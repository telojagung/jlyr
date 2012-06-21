package com.jlyr.util;

import com.jlyr.providers.LyricsProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

public class Lyrics {
	Track mTrack = null;
	LyricReader mReader = null;
	String mLyrics = null;
	String[] mSources = null;
	Handler mLyrHandler = null;
	boolean mAutoSave = false;
	
	Context mContext;
	
	LyricsProvider[] mProviders = null;
	int mProviderIndex = 0;
	
	public static final String TAG = "JLyrLyrics";
	
	public static final int DID_LOAD = 0;
	public static final int DID_TRY = 1;
	public static final int DID_ERROR = 2;
	public static final int DID_FAIL = 3;
	public static final int IS_TRYING = 4;
	
	public Lyrics(Context context, Track track) {
		init(context, track, null, false);
	}
	
	public Lyrics(Context context, Track track, boolean autoSave) {
		init(context, track, null, null);
	}
	
	public Lyrics(Context context, Track track, String source) {
		init(context, track, new String[] { source }, null);
	}
	
	public Lyrics(Context context, Track track, String source, Boolean autoSave) {
		init(context, track, new String[] { source }, autoSave);
	}
	
	public Lyrics(Context context, Track track, String[] sources) {
		init(context, track, sources, null);
	}
	
	public Lyrics(Context context, Track track, String[] sources, Boolean autoSave) {
		init(context, track, sources, autoSave);
	}
	
	private void init(Context context, Track track, String[] sources, Boolean autoSave) {
		mContext = context;
		mTrack = track;
		mSources = sources;
		mAutoSave = (autoSave == null)? useAutoSave() : autoSave;
		
		mReader = new LyricReader(mTrack);
		loadProviders(mSources);
		mLyrics = null;
	}
	
	private void loadProviders(String[] sources) {
		ProvidersCollection coll = new ProvidersCollection(mContext, sources);
		mProviders = coll.toArray(mTrack);
	}
	
	public void loadLyrics() {
		loadLyrics(new Handler());
	}
	
	public void loadLyrics(Handler lyr_handler) {
		String[] content = mReader.getContent();
		mLyrics = content[1];
		mLyrHandler = lyr_handler;
		
		if (mLyrics == null) {
	    	Log.i(TAG, "Lyrics not found on disk. Fetching...");
	        fetchLyrics();
		} else {
			Message message = Message.obtain(mLyrHandler, Lyrics.DID_LOAD);
    		mLyrHandler.sendMessage(message);
		}
	}
	
	public void fetchLyrics() {
		mProviderIndex = -1;
		useNextProvider();
	}
	
	public void saveLyrics() {
		mReader.save(mLyrics);
	}
	
	private void useNextProvider() {
		mProviderIndex++;
		if (mProviderIndex >= mProviders.length) {
			Message message = Message.obtain(mLyrHandler, Lyrics.DID_FAIL);
    		mLyrHandler.sendMessage(message);
			return;
		}
		
		LyricsProvider provider = getCurrentProvider();
		doTry();
		
		Handler handler = new Handler() {
			public void handleMessage(Message message) {
				switch (message.what) {
				case Lyrics.DID_LOAD:
					doSucceed();
					break;
				case Lyrics.DID_FAIL:
					doFail();
					break;
				case Lyrics.DID_ERROR:
					doError();
					break;
				}
			}
		};
		provider.loadLyrics(handler);
	}
	
	private void doError() {
		LyricsProvider provider = getCurrentProvider();
		Log.i(TAG, "Provider " + provider.getSource() + " had an error.");
		
		Message message = Message.obtain(mLyrHandler, Lyrics.DID_ERROR, provider.getSource());
		mLyrHandler.sendMessage(message);
		
		mLyrics = null;
		useNextProvider();
	}
	
	private void doFail() {
		LyricsProvider provider = getCurrentProvider();
		Log.i(TAG, "Provider " + provider.getSource() + " failed.");
		
		Message message = Message.obtain(mLyrHandler, Lyrics.DID_TRY, provider.getSource());
		mLyrHandler.sendMessage(message);
		
		mLyrics = null;
		useNextProvider();
	}
	
	private void doSucceed() {
		LyricsProvider provider = getCurrentProvider();
		Log.i(TAG, "Provider " + provider.getSource() + " succeeded.");
		
		mLyrics = provider.getLyrics();
		if (mLyrics != null) {
			if (mAutoSave) {
				saveLyrics();
			}
			
			Message message = Message.obtain(mLyrHandler, Lyrics.DID_LOAD, provider.getSource());
    		mLyrHandler.sendMessage(message);
		}
	}
	
	private void doTry() {
		LyricsProvider provider = getCurrentProvider();
		Log.d(TAG, "Trying provider " + provider.getSource());
		
		Message message = Message.obtain(mLyrHandler, Lyrics.IS_TRYING, provider.getSource());
		mLyrHandler.sendMessage(message);
	}
	
	private boolean useAutoSave() {
		if (mContext == null) {
			Log.e(TAG, "Context is null.");
			return false;
		}
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
		 
		boolean auto_save = SP.getBoolean("auto_save_lyrics", false);
		return auto_save;
	}
	
	public LyricsProvider getCurrentProvider() {
		return mProviders[mProviderIndex];
	}
	
	public String[] getSources() {
		return mSources;
	}
	
	public Track getTrack() {
		return mTrack;
	}
	
	public String getLyrics() {
		return mLyrics;
	}
}
