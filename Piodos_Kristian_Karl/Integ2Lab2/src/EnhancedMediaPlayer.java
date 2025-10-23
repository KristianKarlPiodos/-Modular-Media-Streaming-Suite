// Improved Code: Refactored with Structural Design Patterns
// Each pattern is labeled in comments where applied.

import java.util.*; 

// Adapter Pattern:
// Used to handle multiple media sources (local files, HLS, remote API) by adapting them to a common MediaSource interface.
interface MediaSource {
    String fetchData();
}

class LocalFileSource implements MediaSource {
    private String filePath;

    public LocalFileSource(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String fetchData() {
        return "Data from local file: " + filePath;
    }
}

class HLSSource implements MediaSource {
    private String url;

    public HLSSource(String url) {
        this.url = url;
    }

    @Override
    public String fetchData() {
        return "Streamed data from HLS: " + url;
    }
}

class RemoteAPISource implements MediaSource {
    private String apiEndpoint;

    public RemoteAPISource(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    @Override
    public String fetchData() {
        return "Data from remote API: " + apiEndpoint;
    }
}

// Decorator Pattern:
// Used for on-the-fly feature plugins (subtitle, equalizer, watermarking) by wrapping MediaProcessor.
interface MediaProcessor {
    void process();
}

class BasicMediaProcessor implements MediaProcessor {
    private MediaSource source;

    public BasicMediaProcessor(MediaSource source) {
        this.source = source;
    }

    @Override
    public void process() {
        System.out.println("Processing: " + source.fetchData());
    }
}

abstract class MediaDecorator implements MediaProcessor {
    protected MediaProcessor decoratedProcessor;

    public MediaDecorator(MediaProcessor decoratedProcessor) {
        this.decoratedProcessor = decoratedProcessor;
    }

    @Override
    public void process() {
        decoratedProcessor.process();
    }
}

class SubtitleDecorator extends MediaDecorator {
    public SubtitleDecorator(MediaProcessor decoratedProcessor) {
        super(decoratedProcessor);
    }

    @Override
    public void process() {
        super.process();
        System.out.println("Rendering subtitles.");
    }
}

class EqualizerDecorator extends MediaDecorator {
    public EqualizerDecorator(MediaProcessor decoratedProcessor) {
        super(decoratedProcessor);
    }

    @Override
    public void process() {
        super.process();
        System.out.println("Applying audio equalizer.");
    }
}

class WatermarkDecorator extends MediaDecorator {
    public WatermarkDecorator(MediaProcessor decoratedProcessor) {
        super(decoratedProcessor);
    }

    @Override
    public void process() {
        super.process();
        System.out.println("Adding watermark.");
    }
}

// Composite Pattern:
// Used for composite maanages playlists (single files and sub-playlists).
interface MediaItem {
    void play();
}

class FileItem implements MediaItem {
    private MediaProcessor processor;

    public FileItem(MediaProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void play() {
        processor.process();
    }
}

class Playlist implements MediaItem {
    private List<MediaItem> items = new ArrayList<>();

    public void add(MediaItem item) {
        items.add(item);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public void play() {
        for (MediaItem item : items) {
            item.play();
        }
    }
}

// Bridge Pattern:
// Used for runtime switching of rendering strategy (hardware vs software) by separating abstraction from implementation.
interface Renderer {
    void render(String data);
}

class HardwareRenderer implements Renderer {
    @Override
    public void render(String data) {
        System.out.println("Hardware rendering: " + data);
    }
}

class SoftwareRenderer implements Renderer {
    @Override
    public void render(String data) {
        System.out.println("Software rendering: " + data);
    }
}

abstract class MediaPlayerAbstraction {
    protected Renderer renderer;

    public MediaPlayerAbstraction(Renderer renderer) {
        this.renderer = renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public abstract void play(MediaItem item);
}

class StandardMediaPlayer extends MediaPlayerAbstraction {
    public StandardMediaPlayer(Renderer renderer) {
        super(renderer);
    }

    @Override
    public void play(MediaItem item) {
        String data = "Media data";
        renderer.render(data);
        item.play();
    }
}

// Proxy Pattern: Used for remote-proxying to cache streams.
class RemoteStreamProxy implements MediaSource {
    private MediaSource realSource;
    private String cachedData;

    public RemoteStreamProxy(MediaSource realSource) {
        this.realSource = realSource;
    }

    @Override
    public String fetchData() {
        if (cachedData == null) {
            cachedData = realSource.fetchData();
            System.out.println("Caching remote data.");
        } else {
            System.out.println("Using cached data.");
        }
        return cachedData;
    }
}

// Main program
public class EnhancedMediaPlayer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Playlist mainPlaylist = new Playlist();
        Renderer currentRenderer = new HardwareRenderer();
        MediaPlayerAbstraction player = new StandardMediaPlayer(currentRenderer);

        boolean running = true;
        while (running) {
            System.out.println("\n === Modular Media Player Menu === ");
            System.out.println("1. Add a new media item to playlist");
            System.out.println("2. Play current playlist");
            System.out.println("3. Switch renderer (Current: " +
                (currentRenderer instanceof HardwareRenderer ? "Hardware" : "Software") + ")");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addMediaItem(scanner, mainPlaylist);
                    break;
                case 2:
                    if (mainPlaylist.isEmpty()) {
                        System.out.println("Playlist is empty. Please add media first.");
                    } else {
                        player.play(mainPlaylist);
                    }
                    break;
                case 3:
                    currentRenderer = (currentRenderer instanceof HardwareRenderer)
                        ? new SoftwareRenderer()
                        : new HardwareRenderer();
                    player.setRenderer(currentRenderer);
                    System.out.println("Switched to " +
                        (currentRenderer instanceof HardwareRenderer ? "Hardware" : "Software") + " renderer.");
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting Modular Media Player. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose between 1 and 4.");
            }
        }
        scanner.close();
    }

    private static void addMediaItem(Scanner scanner, Playlist playlist) {
        System.out.print("Enter media source type (local/hls/api): ");
        String type = scanner.nextLine().toLowerCase();

        if (type.equals("local")) {
            System.out.print("Enter file name: ");
        } else if (type.equals("hls")) {
            System.out.print("Enter stream URL: ");
        } else if (type.equals("api")) {
            System.out.print("Enter API endpoint: ");
        } else {
            System.out.println("Invalid source type.");
            return;
        }

        String path = scanner.nextLine();

        MediaSource source;
        switch (type) {
            case "local":
                source = new LocalFileSource(path);
                break;
            case "hls":
                source = new HLSSource(path);
                break;
            case "api":
                source = new RemoteAPISource(path);
                break;
            default:
                System.out.println("Invalid source type.");
                return;
        }

        // Optional proxy caching (only for remote sources)
        if (!type.equals("local")) {
            System.out.print("Enable proxy caching for this source? (y/n): ");
            if (scanner.nextLine().toLowerCase().startsWith("y")) {
                source = new RemoteStreamProxy(source);
            }
        }

        MediaProcessor processor = new BasicMediaProcessor(source);

        // Optional decorators
        System.out.print("Add subtitles feature? (y/n): ");
        if (scanner.nextLine().toLowerCase().startsWith("y")) {
            processor = new SubtitleDecorator(processor);
        }

        System.out.print("Add audio equalizer feature? (y/n): ");
        if (scanner.nextLine().toLowerCase().startsWith("y")) {
            processor = new EqualizerDecorator(processor);
        }

        System.out.print("Add watermark feature? (y/n): ");
        if (scanner.nextLine().toLowerCase().startsWith("y")) {
            processor = new WatermarkDecorator(processor);
        }

        playlist.add(new FileItem(processor));
        System.out.println("Media item added successfully!");
    }
}
