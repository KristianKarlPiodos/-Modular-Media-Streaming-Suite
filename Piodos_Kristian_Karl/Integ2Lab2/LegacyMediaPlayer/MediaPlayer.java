// Legacy Code: Monolithic MediaPlayer.java


public class MediaPlayer {
    private String currentSource;
    private boolean useHardwareRendering = false;
    private boolean addSubtitle = false;
    private boolean addEqualizer = false;
    private boolean addWatermark = false;
    private String cache; 

    public void setSource(String source) {
        this.currentSource = source;
    }

    public void toggleHardwareRendering(boolean useHardware) {
        this.useHardwareRendering = useHardware;
    }

    public void toggleSubtitle(boolean enable) {
        this.addSubtitle = enable;
    }

    public void toggleEqualizer(boolean enable) {
        this.addEqualizer = enable;
    }

    public void toggleWatermark(boolean enable) {
        this.addWatermark = enable;
    }

    public void play() {
        if (currentSource == null) {
            System.out.println("No source set.");
            return;
        }


        if (currentSource.endsWith(".mp4") || currentSource.endsWith(".avi")) {
          
            System.out.println("Playing local file: " + currentSource);
        } else if (currentSource.startsWith("hls://")) {
          
            System.out.println("Streaming HLS: " + currentSource);
          
            cache = "Cached HLS data for " + currentSource; 
        } else if (currentSource.startsWith("api://")) {
         
            System.out.println("Fetching from remote API: " + currentSource);
           
            cache = "Cached API data for " + currentSource;
        } else {
            System.out.println("Unsupported source: " + currentSource);
            return;
        }

     
        if (useHardwareRendering) {
            System.out.println("Using hardware rendering.");
        } else {
            System.out.println("Using software rendering.");
        }

        if (addSubtitle) {
            System.out.println("Rendering subtitles.");
        }
        if (addEqualizer) {
            System.out.println("Applying audio equalizer.");
        }
        if (addWatermark) {
            System.out.println("Adding watermark.");
        }

     
        System.out.println("Playback complete.");
    }

    public void playPlaylist(String[] sources) {
     
        for (String source : sources) {
            setSource(source);
            play();
        }
    }

    public static void main(String[] args) {
        MediaPlayer player = new MediaPlayer();
        player.setSource("video.mp4");
        player.toggleSubtitle(true);
        player.play();

        player.setSource("hls://stream.example.com");
        player.toggleWatermark(true);
        player.play();
    }
}

