package KN07.M319_KN07;

/**
 * Enumeration representing the possible states of the game.
 * 
 * <p>This enum defines the three distinct states that the game can be in
 * at any given time. The game controller uses these states to determine
 * which game loop to execute and how to transition between different
 * phases of gameplay.</p>
 * 
 * <p>State transitions:</p>
 * <ul>
 *   <li>{@code HOME} → {@code GUESSING}: After difficulty selection</li>
 *   <li>{@code GUESSING} → {@code OVER}: When game ends (win or loss)</li>
 *   <li>{@code OVER} → {@code HOME}: When player chooses to play again</li>
 * </ul>
 * 
 * @author Joel Bonini
 * @version 1.0
 * @since 1.0
 * @see Game#gameLoop()
 */
public enum GameState {
    /**
     * The initial state where the main menu is displayed.
     * 
     * <p>In this state, the game displays the welcome screen and
     * prompts the user to select a difficulty level. This is the
     * entry point for each new game session.</p>
     */
    HOME,
    
    /**
     * The active gameplay state where song guessing occurs.
     * 
     * <p>During this state, the game presents song snippets to the
     * player and processes their guesses. The game remains in this
     * state until all snippets are completed or the player runs
     * out of guesses.</p>
     */
    GUESSING,
    
    /**
     * The end state displaying game results and replay options.
     * 
     * <p>This state shows the final score, whether the player won
     * or lost, and offers the choice to play again or quit the game.
     * From here, the game either returns to {@code HOME} or terminates.</p>
     */
    OVER
}