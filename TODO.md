Essential features
==================

  * add more providers
  * add more receivers
  * allow to choose receivers (like in Simple Last.fm Scrobbler)
  * maybe use database to save names of stored lyrics, to use search and/or load better in browser
  * save in local file track info and provider/source. it should be displayed in the viewer
  * add edit functionality to the saved lyrics.
  * add canRead() and canSave() methods to providers, and provide the user with a choice to save there and there and read there and there. LyricReader will be a provider.
  * use @color instead of @integer for colors?
  * add search history
  * add support for https only or not
  * add support for proxies (why it fails when proxies are set)

Probably in another major version
=================================

  * add history of errors/success of providers
    * group these by provider to try and guess which providers are most likely to fail by artist
    * add auto-select providers feature
  * option to send statistics to a server to keep track of success rates of providers by artists
  * get and save whole album lyrics
  * get to choose which players to listen for and which not to listen for
  * run on boot or not preference
  * error log (why each provider failed)
  * choose where to save the lyrics (default should be [external-app-data]/Android/.data/com.jlyr I guess, check docs)

Some misc ideas
===============

  * scroll along with the lyrics
  * get lyrics from lrc (??) files
  * use fragments to display list of lyrics next to lyrics (android 3.x+ I guess)
  * better icon
  * better UI (tabs, action bar, etc.)
  *
