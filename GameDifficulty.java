package KN07.M319_KN07;

/**
 * Enumeration representing the available difficulty levels for the game.
 * 
 * <p>Each difficulty level determines the number of guesses allowed per round.
 * Higher difficulties provide fewer guesses, making the game progressively
 * more challenging. The difficulty affects the total number of attempts
 * available across all 30 song snippets.</p>
 * 
 * <p>The enum values are ordered from easiest to hardest, and their ordinal
 * values (0-4) correspond to menu options 1-5 in the game interface.</p>
 * 
 * @author Joel Bonini
 * @version 1.0
 * @since 1.0
 * @see Game#setGuesses(GameDifficulty, int)
 * @see Game#homeLoop()
 */
public enum GameDifficulty {
    /**
     * Easy difficulty with 10 guesses per round.
     * 
     * <p>Recommended for beginners or casual players. Provides the most
     * forgiving experience with plenty of attempts to identify each song.</p>
     */
    EASY,
    
    /**
     * Normal difficulty with 7 guesses per round.
     * 
     * <p>The standard difficulty level offering a balanced challenge.
     * Suitable for players familiar with Twenty One Pilots songs.</p>
     */
    NORMAL,
    
    /**
     * Hard difficulty with 5 guesses per round.
     * 
     * <p>A challenging mode for experienced players. Requires good
     * knowledge of the band's discography and careful consideration
     * of each guess.</p>
     */
    HARD,
    
    /**
     * Extreme difficulty with 3 guesses per round.
     * 
     * <p>Very challenging mode for expert players. Demands excellent
     * knowledge of Twenty One Pilots songs and efficient guessing
     * strategies.</p>
     */
    EXTREME,
    
    /**
     * Impossible difficulty with only 1 guess per round.
     * 
     * <p>The ultimate challenge requiring perfect knowledge. Players
     * must correctly identify each song on the first attempt. One
     * wrong guess ends the game immediately.</p>
     */
    IMPOSSIBLE
}