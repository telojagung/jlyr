package com.jlyr.receiver;

import com.jlyr.util.Track;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class SEMCMusicReceiver extends BuiltInMusicAppReceiver {

	static final String APP_PACKAGE = "com.sonyericsson.music";
	
	static final String ACTION_SEMC_START_LEGACY = "com.sonyericsson.music.playbackcontrol.ACTION_PLAYBACK_PLAY";
	static final String ACTION_SEMC_STOP_LEGACY = "com.sonyericsson.music.playbackcontrol.ACTION_PLAYBACK_PAUSE";
	static final String ACTION_SEMC_FINISHED = "com.sonyericsson.music.TRACK_COMPLETED";
	static final String ACTION_SEMC_METACHANGED = "com.sonyericsson.music.metachanged";
	static final String ACTION_SEMC_COMPLETE = "com.sonyericsson.music.playbackcomplete";
	static final String ACTION_SEMC_STATECHANGED = "com.sonyericsson.music.playstatechanged";
	
	static final String ACTION_SEMC_START = "com.sonyericsson.music.playbackcontrol.ACTION_TRACK_STARTED";
	static final String ACTION_SEMC_STOP = "com.sonyericsson.music.playbackcontrol.ACTION_PAUSED";
	
	private static final String TAG = "SEMCMusicReceiver";

	public SEMCMusicReceiver() {
		super(ACTION_SEMC_STOP, APP_PACKAGE, "Sony Ericsson Music Player");
	}

	protected boolean isStopAction(String action) {
		return action.equals(ACTION_SEMC_STOP) || action.equals(ACTION_SEMC_STOP_LEGACY);
	}
	
	@Override
	protected void parseIntent(Context ctx, String action, Bundle bundle) throws IllegalArgumentException {
		super.parseIntent(ctx, action, bundle);
	}

	@Override
	Track readTrackFromBundleData(Bundle bundle) {
		Log.d(TAG, "Will read data from SEMC intent");

		String artist = bundle.getString("ARTIST_NAME");
		String album = bundle.getString("ALBUM_NAME");
		String title = bundle.getString("TRACK_NAME");

		if (artist == null || album == null || title == null) {
			throw new IllegalArgumentException("null track values");
		}

		Track track = new Track(artist, title, album, null);
		return track;
	}

}
