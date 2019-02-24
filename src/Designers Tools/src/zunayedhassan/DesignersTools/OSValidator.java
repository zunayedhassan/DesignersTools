package zunayedhassan.DesignersTools;

/**
 *
 * @author Zunayed Hassan
 */
public class OSValidator {
    private final static String _OS = System.getProperty("os.name").toLowerCase();

    public static boolean IS_WINDOWS() {
        return (_OS.contains("win"));
    }

    public static boolean IS_MAC() {
        return (_OS.contains("mac"));
    }

    public static boolean IS_UNIX() {
        return (_OS.contains("nix") || _OS.contains("nux") || _OS.contains("aix"));
    }

    public static boolean IS_SOLARIS() {
        return (_OS.contains("sunos"));
    }
}
