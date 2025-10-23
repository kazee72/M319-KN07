package KN07.M319_KN07;

/**
 * Represents a song snippet used in the guessing game.
 * 
 * <p>This class encapsulates a text snippet (typically song lyrics or description)
 * and its corresponding song name. Each snippet serves as a clue that players
 * must use to identify the correct Twenty One Pilots song.</p>
 * 
 * <p>Snippet objects are immutable once created - the text and song name
 * cannot be modified after construction. This ensures data integrity
 * throughout the game.</p>
 * 
 * @author Joel Bonini
 * @version 1.0
 * @since 1.0
 * @see Game#seedSnippets()
 */
public class Snippet {
    
    /**
     * The text content of the snippet shown to players as a clue.
     */
    private String text;
    
    /**
     * The name of the song this snippet represents.
     */
    private String songName;

    /**
     * Creates a new Snippet with the specified text and song name.
     * 
     * <p>Constructs an immutable snippet object containing a clue text
     * and its corresponding song answer. Both parameters should be
     * non-null and non-empty for proper game functionality.</p>
     * 
     * @param text the snippet text that serves as a clue for the player
     * @param songName the correct song name that matches this snippet
     */
    public Snippet(String text, String songName) {
        this.text = text;
        this.songName = songName;
    }

    /**
     * Gets the text content of this snippet.
     * 
     * <p>Returns the clue text that is displayed to players during
     * the guessing phase of the game.</p>
     * 
     * @return the snippet text used as a clue
     */
    public String getText() { return this.text; }
    
    /**
     * Gets the song name associated with this snippet.
     * 
     * <p>Returns the correct answer for this snippet, which is compared
     * against the player's guess to determine if they answered correctly.</p>
     * 
     * @return the song name that corresponds to this snippet
     */
    public String getSongName() { return this.songName; }

    /**
     * Returns a string representation of this snippet.
     * 
     * <p>Creates a formatted string showing both the song name and its
     * associated text snippet. Useful for debugging and logging purposes.</p>
     * 
     * <p>Format: {@code "SongName: Snippet text"}</p>
     * 
     * @return a string containing the song name and snippet text
     */
    @Override
    public String toString() {
        return String.format("%s: %s", this.songName, this.text);
    }
}