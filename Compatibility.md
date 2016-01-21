# Introduction #

JLyr uses different techniques to detect the "now playing" song. These techniques work with some music apps, but not all of them. Check below to see if your favorite app is supported or not. If your application is not on the list, let me know (report it as an issue).

**NOTE**: The search feature does not rely on the music player, so it will work anyway.

# Methods #

These are the methods (receivers) currently supported:
  * ScrobbleDroid API
  * Stock Android Player

# Tested Applications #

## Stock Music Player ##

The currently playing song should be detected automatically.

## Vanilla Music Player ##

There are two ways to make it work:
  * via the ScrobbleDroid API: In the preferences screen, check the "Scrobble to Last.fm" or "Use ScrobbleDroid API" option. Pause/Play a song, and you will see it is detected.
  * via the Stock Player: In newer versions (not on the Play Store, at the time of writing), there is an option to "Emulate Stock Broadcasts", which will make it act as if it was the stock android player.

Available on the [Play Store](https://play.google.com/store/apps/details?id=org.kreed.vanilla).

**NOTE**: the latest version, if not on the market, is available on [GitHub](https://github.com/kreed/vanilla/downloads).

## Songbird ##

Enable "Last.fm scrobbling" in the app's settings.

Available on the [Play Store](https://play.google.com/store/apps/details?id=com.songbirdnest.mediaplayer).

## Jukefox ##

Enable scrobbling to Last.fm via the ScrobbleDroid API. Go to Settings > "Scrobble to Last.fm" > "Scrobble to last.fm" > "use scrobbledroid".

Available on the [Play Store](https://play.google.com/store/apps/details?id=ch.ethz.dcg.pancho2)