package KN07.M319_KN07;

/**
 * Entry point for the Twenty One Pilots song guessing game application.
 * 
 * <p>This class contains the main method that initializes and starts the game.
 * It creates a new {@link Game} instance and begins the game loop, which
 * continues until the user chooses to exit the application.</p>
 * 
 * @author Joel Bonini
 * @version 1.0
 * @since 1.0
 * @see Game
 */
public class Main {
    
    /**
     * The main method that launches the game application.
     * 
     * <p>Creates a new instance of the {@link Game} class and starts
     * the main game loop. The game loop runs indefinitely until the
     * user explicitly chooses to quit, at which point the program
     * terminates via {@link Helper#ExitGame()}.</p>
     * 
     * <p>Program flow:</p>
     * <ol>
     *   <li>Game instance is created (loads snippets, initializes state)</li>
     *   <li>Game loop starts in HOME state</li>
     *   <li>User selects difficulty</li>
     *   <li>Guessing rounds begin</li>
     *   <li>Game ends with win/loss</li>
     *   <li>User can replay or exit</li>
     * </ol>
     * 
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        Game game1 = new Game();

        game1.gameLoop();
    }
}