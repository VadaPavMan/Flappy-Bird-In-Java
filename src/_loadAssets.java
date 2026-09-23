import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class _loadAssets {
    private static final Path ASSETS_DIR = codeSourceDirectory()
            .getParent()
            .resolve("assets");

    private static Path codeSourceDirectory() {
        try {
            return Paths.get(_loadAssets.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI())
                    .toAbsolutePath();
        } catch (URISyntaxException exception) {
            throw new IllegalStateException("Unable to locate the application files", exception);
        }
    }

    private static String asset(String... parts) {
        Path result = ASSETS_DIR;
        for (String part : parts) {
            result = result.resolve(part);
        }
        return result.toString();
    }

    // Return Icons Path
    public String getIcon(String... path) {
        return asset(path);
    }

    // App Icons
    public static final String APPICON = asset("appicon", "favicon.ico");

    // Sprites

    // Numbers
    public static final String ZERO = asset("sprites", "0.png");
    public static final String ONE = asset("sprites", "1.png");
    public static final String TWO = asset("sprites", "2.png");
    public static final String THREE = asset("sprites", "3.png");
    public static final String FOUR = asset("sprites", "4.png");
    public static final String FIVE = asset("sprites", "5.png");
    public static final String SIX = asset("sprites", "6.png");
    public static final String SEVEN = asset("sprites", "7.png");
    public static final String EIGHT = asset("sprites", "8.png");
    public static final String NINE = asset("sprites", "9.png");
    
    // Backgrounds
    public static final String BACKGROUND_DAY = asset("sprites", "background-day.png");
    public static final String BACKGROUND_NIGHT = asset("sprites", "background-night.png");
    
    // Base
    public static final String BASE = asset("sprites", "base.png");

    // Birds
    public static final String BLUEBIRD_DOWNFLAP = asset("sprites", "bluebird-downflap.png");
    public static final String BLUEBIRD_MIDFLAP = asset("sprites", "bluebird-midflap.png");
    public static final String BLUEBIRD_UPFLAP = asset("sprites", "bluebird-upflap.png");
    
    public static final String REDBIRD_DOWNFLAP = asset("sprites", "redbird-downflap.png");
    public static final String REDBIRD_MIDFLAP = asset("sprites", "redbird-midflap.png");
    public static final String REDBIRD_UPFLAP = asset("sprites", "redbird-upflap.png");
    
    public static final String YELLOWBIRD_DOWNFLAP = asset("sprites", "yellowbird-downflap.png");
    public static final String YELLOWBIRD_MIDFLAP = asset("sprites", "yellowbird-midflap.png");
    public static final String YELLOWBIRD_UPFLAP = asset("sprites", "yellowbird-upflap.png");
    
    // Game Over
    public static final String GAMEOVER = asset("sprites", "gameover.png");
    
    // Message
    public static final String MESSAGE = asset("sprites", "message.png");
    
    // Pipe
    public static final String PIPE_GREEN = asset("sprites", "pipe-green.png");
    public static final String PIPE_RED = asset("sprites", "pipe-red.png");


}
