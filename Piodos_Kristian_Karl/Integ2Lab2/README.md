Modular Media Streaming Suite

EnhancedMediaPlayer – A Java Implementation of Structural Design Patterns

--- Overview -----

This project demonstrates how structural design patterns can be applied in a modular media player system.
It was designed to show clean architecture, extensibility, and runtime flexibility using five key structural patterns:

* Adapter Pattern – Unifies different media sources (local, HLS, API)

* Decorator Pattern – Dynamically adds playback features (subtitles, equalizer, watermark)

* Composite Pattern – Manages playlists containing both single files and sub-playlists

* Bridge Pattern – Enables runtime switching between hardware and software rendering

* Proxy Pattern – Implements caching for remote streams to reduce redundant fetches


---- Design Pattern Summary ----

1. Adapter Pattern

Allows the media player to work with different data sources through a common interface MediaSource.
Classes:

* MediaSource (interface)

* LocalFileSource, HLSSource, RemoteAPISource (adapters)

2. Decorator Pattern

Adds optional media features at runtime without altering core processing logic.
Classes:

* MediaProcessor (interface)

* BasicMediaProcessor (base processor)

* SubtitleDecorator, EqualizerDecorator, WatermarkDecorator (decorators)

3. Composite Pattern

Allows nested playlists to be treated like single media files.
Classes:

* MediaItem (interface)

* FileItem (leaf component)

* Playlist (composite)

4. Bridge Pattern

Separates media control logic from rendering strategy, making it easy to switch renderers dynamically.
Classes:

* Renderer (interface)

* HardwareRenderer, SoftwareRenderer (implementations)

* MediaPlayerAbstraction, StandardMediaPlayer (abstractions)

5. Proxy Pattern

Improves performance by caching remote media data instead of fetching it repeatedly.
Classes:

* RemoteStreamProxy (proxy class)

* Wraps around any MediaSource that supports remote access



----How to Run the Project----

1. Compile

Make sure you have Java JDK 8 or higher installed.
Then open a terminal or command prompt inside your project directory and run:

javac EnhancedMediaPlayer.java

2. Run

After successful compilation, execute:

java EnhancedMediaPlayer

---- Demo Instructions -----

Once the program runs, an interactive menu will appear:

=== Modular Media Player Menu ===
1. Add a new media item to playlist
2. Play current playlist
3. Switch renderer (Current: Hardware)
4. Exit

- Example Demo Flow:

Add a media item

Choose option 1

Select local, hls, or api

Enter a file name or URL (e.g., movie.mp4, http://stream.hls)

Choose optional features (subtitles, equalizer, watermark)

Enable proxy caching for remote streams (optional)

Play playlist

Choose option 2


Watch console output as decorators apply features in order and rendering occurs.

Switch Renderer

Choose option 3


Toggles between Hardware and Software rendering (Bridge Pattern in action)

Exit

Choose option 4 to quit.


---- Author ----

Developed by: Kristian Karl R. Piodos
Subject: Integrative Programming & Technolog ies 1
Topic: Structural Design Patterns (Adapter, Decorator, Composite, Bridge, Proxy)


