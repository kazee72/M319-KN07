package KN07.M319_KN07;

/**
 * Utility class providing helper methods for console operations and game control.
 * 
 * <p>This class contains static utility methods for clearing the console,
 * adding delays, and controlling program termination. All methods are static
 * and the class is not meant to be instantiated.</p>
 * 
 * @author Joel Bonini
 * @version 1.0
 * @since 1.0
 */
public class Helper {
    
    /**
     * Clears the console screen using ANSI escape codes.
     * 
     * <p>This method uses ANSI escape sequences to clear the terminal screen
     * and position the cursor at the top-left corner. It works on Unix-based
     * systems (Linux, macOS) and terminals that support ANSI codes.</p>
     * 
     * <p>The escape codes used:</p>
     * <ul>
     *   <li>\033[H - Moves cursor to home position (0,0)</li>
     *   <li>\033[2J - Clears the entire screen</li>
     * </ul>
     * 
     * <p>Note: This may not work properly on Windows Command Prompt without
     * ANSI support enabled.</p>
     */
    public static void ClearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Pauses program execution for a specified duration.
     * 
     * <p>Creates a delay in the program flow, useful for giving users time
     * to read messages or creating smooth transitions between game states.
     * If the thread is interrupted during sleep, it sets the interrupt flag
     * on the current thread.</p>
     * 
     * @param ms the delay duration in milliseconds (1000ms = 1 second)
     */
    public static void Delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Terminates the game application immediately.
     * 
     * <p>Calls {@code System.exit(0)} to terminate the JVM with a success
     * status code. This method is typically called when the user chooses
     * to quit the game from the game over screen.</p>
     * 
     * <p>Exit code 0 indicates normal termination without errors.</p>
     */
    public static void ExitGame() {
        System.exit(0); 
    }
}