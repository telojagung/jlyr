# Introduction #

JLyr fetches lyrics from multiple sources, either via an API provided by the website, or by parsing the webpage you would usually see on the web.


# Supported Providers List #


## AZLyrics ##

To get the lyrics from [AZLyrics](http://www.azlyrics.com/):

  * Search for the lyrics on their site via [DuckDuckGo](http://www.duckduckgo.com/).
  * Parse the web page to find the lyrics.

This relies on [DuckDuckGo](http://www.duckduckgo.com/) for searching for the song on AZLyrics. Failure to find lyrics _might_ be due to DuckDuckGo not finding them (although this is most likely not the case). I am working on an alternative. Meanwhile, if you find the lyrics on the website but not via the app, please report it.

## MetroLyrics ##

To get the lyrics from [MetroLyrics](http://www.metrolyrics.com/):

  * Generate the URL dynamically.
  * Parse the web page to find the lyrics.

This method involves a lot of parsing and might not be fool-proof. If there are any problems displaying the lyrics, or not finding them even  though they are on the website, please report.

## LyrDB ##

To get the lyrics from [LyrDB](http://www.lyrdb.com/):

  * Search for the song via their API.
  * Get the lyrics directly.

This relies on their API for searching and fetching, so it might "find" a song's lyrics, even if it's not a 100% percent match. (A sanity check will be included in future releases).

Also, I've been noticing that the website is often down. If fetching lyrics is slow, try disabling this source, as it may be slowing down the providers which come after it.

## ChartLyrics ##

To get the lyrics from [ChartLyrics](http://www.chartlyrics.com/):

  * Search and get the lyrics directly from ChartLyrics.

This relies on their API for searching and fetching, so it might "find" a song's lyrics, even if it's not a 100% percent match. (A sanity check will be included in future releases). This is actually happening more frequently than LyrDB: it finds a song that is not near at all.

## Dummy ##

This one is here for testing. You don't need to use it. It will always fail.


# Latest Additions #

## SongLyrics ##

To get the lyrics from [SongLyrics](http://www.songlyrics.com/):
  * Search via [DuckDuckGo](http://www.duckduckgo.com) to find the URL, or try to generate it.
  * Parse the page to find the lyrics.


## DarkLyrics ##

The problem with [DarkLyrics](http://www.darklyrics.com/) is that the pages are not one per song, but one per album. So, to get the lyrics, the whole album lyrics need to be downloaded, for each song... There might be a better solution now, but it's not implemented.


# List of to-be-added Providers #

## LyricsMode ##

There are many options I am considering to get lyrics from [LyricsMode](http://www.lyricsmode.com/):

  * Search via [DuckDuckGo](http://www.duckduckgo.com) to find the URL (it is always in the first few results there), then parse the page (easily parsed, `div#songlyrics` or `div#songlyrics_h`).
  * Search via [DuckDuckGo](http://www.duckduckgo.com) to find the URL, then parse the page to find the internal song id, and use their widget system to get the lyrics (no parsing HTML needed).
  * Search via the website's search system, but the outcome is not guaranteed to be the lyrics page (might be the search results page).


## LyricsFreak ##

To get lyrics from [LyricsFreak](http://www.lyricsfreak.com/):

  * Search via [DuckDuckGo](http://www.duckduckgo.com) to find the URL.
  * Parse the page (easily parsed too, `div#content_h`).


## LyricsBay ##

To get the lyrics from [LyricsBay](http://www.lyricsbay.com/):

  * Search via [DuckDuckGo](http://www.duckduckgo.com) to find the URL, or try to generate it.
  * Parse the page (might not be easy, only classes used).


## WikiaLyrics ##

[WikiaLyrics](http://lyrics.wikia.com/Lyrics_Wiki) has an API, but only gives partial lyrics, so I would need to parse the page as usual... unless I find how their app is doing it, and see if it can be done too...

# Providers Suggestions #

If you know of a provider which has an API, or provides good lyrics, or genre-specific (like Dark Lyrics), or region/language-specific, leave a comment about it. If you know of some other application/plugin that gets lyrics from there, name it too.

If you can know how to parse it (HTML structure, regular expression, or whatever), it would be even better. And if you can do it yourself, let me know if you want to contribute.

# Thanks #

I want to thank lots of lyrics plugin, for players like songbird, exaile, banshee, foobar, and much more which I can't remember, for finding ways to parse and/or generate lyrics url and using the APIs AND [DuckDuckGo](http://www.duckduckgo.com/), of course. I won't thank those providers which don't have an API for the public to use.