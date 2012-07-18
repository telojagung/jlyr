/**
 * This file is part of Simple Last.fm Scrobbler.
 * 
 *     http://code.google.com/p/a-simple-lastfm-scrobbler/
 * 
 * Copyright 2011 Simple Last.fm Scrobbler Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jlyr.receiver;

import com.jlyr.util.Track;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

/**
 * A BroadcastReceiver for intents sent by music apps such as Android Music and
 * Hero Music. Specialized classes inherit from this class to deal with the
 * small differences.
 * 
 * @see AndroidMusicReceiver
 * @see HeroMusicReceiver
 * @author tgwizard
 * @since 1.2.7
 */
public abstract class BuiltInMusicAppReceiver extends
	AbstractPlayStatusReceiver {

	private static final String TAG = "SLSBuiltInMusicAppReceiver";

	static final int NO_AUDIO_ID = -1;

	final String stop_action;

	final String app_package;
	final String app_name;

	public BuiltInMusicAppReceiver(String stopAction, String appPackage,
		String appName) {
		super();
		stop_action = stopAction;
		app_package = appPackage;
		app_name = appName;
	}

	@Override
	protected void parseIntent(Context ctx, String action, Bundle bundle)
		throws IllegalArgumentException {

		Track t = parseTrack(ctx, bundle);
		setTrack(t);

		if (action.equals(stop_action)) {
			setState(Track.State.PLAYLIST_FINISHED);
		} else {
			setState(Track.State.RESUME);
		}
	}

	Track parseTrack(Context ctx, Bundle bundle) {
		long audioid = getAudioId(bundle);

		if (shouldFetchFromMediaStore(ctx, audioid)) { // read from MediaStore
			return readTrackFromMediaStore(ctx, audioid);
		} else {
			return readTrackFromBundleData(bundle);
		}
	}

	long getAudioId(Bundle bundle) {
		long id = NO_AUDIO_ID;
		Object idBundle = bundle.get("id");
		if (idBundle != null) {
			if (idBundle instanceof Long)
				id = (Long) idBundle;
			else if (idBundle instanceof Integer)
				id = (Integer) idBundle;
			else if (idBundle instanceof String) {
				id = Long.valueOf((String) idBundle).longValue();
			} else {
				Log.w(TAG,
					"Got unsupported idBundle type: " + idBundle.getClass());
			}
		}
		return id;
	}

	boolean shouldFetchFromMediaStore(Context ctx, long audioid) {
		if (audioid > 0)
			return true;
		return false;
	}

	Track readTrackFromMediaStore(Context ctx, long audioid) {
		Log.d(TAG, "Will read data from mediastore");

		final String[] columns = new String[] {
			MediaStore.Audio.AudioColumns.ARTIST,
			MediaStore.Audio.AudioColumns.TITLE,
			MediaStore.Audio.AudioColumns.DURATION,
			MediaStore.Audio.AudioColumns.ALBUM,
			MediaStore.Audio.AudioColumns.TRACK, };

		Cursor cur = ctx.getContentResolver().query(
			ContentUris.withAppendedId(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, audioid), columns,
			null, null, null);

		if (cur == null) {
			throw new IllegalArgumentException("could not open cursor to media in media store");
		}

		Track track = null;
		try {
			if (!cur.moveToFirst()) {
				throw new IllegalArgumentException("no such media in media store");
			}
			String artist = cur.getString(cur.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));

			String title = cur.getString(cur.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE));
			
			String album = cur.getString(cur.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM));
			
			track = new Track(artist, title, album, null);

		} finally {
			cur.close();
		}
		
		return track;
	}

	Track readTrackFromBundleData(Bundle bundle) {
		Log.d(TAG, "Will read data from intent");

		String artist = bundle.getString("artist");
		String album = bundle.getString("album");
		String title = bundle.getString("track");
		if (artist == null || album == null || title == null) {
			throw new IllegalArgumentException("null track values");
		}

		Track track = new Track(artist, title, album, null);
		return track;
	}
}
