package KN07.M319_KN07;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;

/**
 * Represents a Twenty One Pilots song guessing game.
 * 
 * <p>This class manages the entire game lifecycle including difficulty selection,
 * snippet presentation, score tracking, and game state transitions. Players must
 * guess song titles based on provided text snippets within a limited number of attempts.</p>
 * 
 * <p>The game consists of 30 rounds, with each round presenting a song snippet.
 * The number of allowed guesses per round depends on the selected difficulty level.</p>
 * 
 * @author Joel Bonini
 * @version 1.0
 */
public class Game {
    
    private GameState state;
    private GameDifficulty difficulty;
    private int score;
    private int guesses;
    private boolean won;
    private ArrayList<Snippet> snippets = new ArrayList<>();
    private Scanner in = new Scanner(System.in);

    /**
     * Creates a new Game instance and initializes it to the home state.
     * 
     * <p>Initializes the game with a score of 0, sets the state to HOME,
     * and loads all song snippets from the snippets file. The game is ready
     * to start after construction.</p>
     * 
     * @author Joel Bonini
     */
    public Game() {
        this.score = 0;
        this.state = GameState.HOME;
        this.seedSnippets();
    }

    /**
     * Main game loop that controls the flow between different game states.
     * 
     * <p>This method runs indefinitely, transitioning between HOME, GUESSING, and OVER states
     * based on game progression and user input. The loop only terminates when the user
     * chooses to exit the game, which calls {@code Helper.ExitGame()} to terminate
     * the program.</p>
     * 
     * <p>State transitions:</p>
     * <ul>
     *   <li>HOME → GUESSING: After difficulty selection</li>
     *   <li>GUESSING → OVER: When all snippets are completed or guesses run out</li>
     *   <li>OVER → HOME: If player chooses to play again</li>
     *   <li>OVER → Program termination: If player chooses to quit</li>
     * </ul>
     * 
     * @author Joel Bonini
     */
    public void gameLoop() {
        while (true) {
            switch (this.state) {
                case GameState.HOME:
                    this.setDifficulty(homeLoop());
                    this.setGameState(GameState.GUESSING);
                    break;
                case GameState.GUESSING:
                    this.setGameState(guessingLoop());
                    break;
                case GameState.OVER:
                    this.setGameState(overLoop());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Displays the home screen and prompts the user to select a difficulty level.
     * 
     * <p>This method presents a menu with five difficulty options and validates user input.
     * It continues to prompt until a valid selection (1-5) is made. Invalid inputs are
     * handled gracefully with error messages and a brief delay before re-prompting.</p>
     * 
     * <p>Difficulty options:</p>
     * <ol>
     *   <li>Easy - 10 guesses per round</li>
     *   <li>Normal - 7 guesses per round</li>
     *   <li>Hard - 5 guesses per round</li>
     *   <li>Extreme - 3 guesses per round</li>
     *   <li>Impossible - 1 guess per round</li>
     * </ol>
     * 
     * @return The selected {@link GameDifficulty} based on user input
     * @see GameDifficulty
     */
    public GameDifficulty homeLoop() {
        int userChoice = -1;
        Helper.ClearConsole();
        // Get the users difficulty choice
        do {
            Helper.Delay(100);
            Helper.ClearConsole();
            System.out.println("""
                    Welcome!
                    Choose a difficulty:
                    [1] Easy
                    [2] Normal
                    [3] Hard
                    [4] Extreme
                    [5] Impossible""");
            try {
                System.out.print("> ");
                userChoice = in.nextInt();
            } catch(InputMismatchException e) {
                in.nextLine();
                System.out.println("Please choose a valid input! (1, 2, 3, 4, 5)");
                Helper.Delay(1000);
                continue;
            }
        } while (userChoice < 1 || userChoice > 5);

        return GameDifficulty.values()[userChoice - 1];
    }

    /**
     * Manages the main guessing gameplay loop where players identify songs from snippets.
     * 
     * <p>This method shuffles all snippets and presents them one by one to the player.
     * For each snippet, the player has a limited number of guesses (based on difficulty)
     * to correctly identify the song. The game continues through all 30 snippets unless
     * the player runs out of guesses.</p>
     * 
     * <p>Game progression:</p>
     * <ul>
     *   <li>Snippets are randomized at the start of each game</li>
     *   <li>Correct guesses increment the score by 1</li>
     *   <li>Incorrect guesses reduce remaining attempts</li>
     *   <li>Running out of guesses ends the game immediately</li>
     *   <li>Completing all 30 snippets with a perfect score results in a win</li>
     * </ul>
     * 
     * @return {@link GameState#OVER} when the game ends (either by completion or failure)
     * @see GameState
     */
    public GameState guessingLoop() {
        Collections.shuffle(snippets);
        if (in.hasNextLine()) in.nextLine();
        // Keep track of guesses already used
        int guessesUsed = 0;
        // Flag to check if user guessed correctly
        boolean guessed = false;
        for (Snippet s : snippets) {
            this.setGuesses(this.difficulty, guessesUsed);
            guessed = false;
            String guess;
            while (!guessed && this.guesses > 0) {
                Helper.ClearConsole();
                System.out.println(String.format("""
                    --- Guess the Twenty One Pilots Song ---
                    Score: %d / 30
                    Guesses: %d
                    Snippet: %s""", this.score, this.guesses, s.getText()));
                System.out.print("> ");
                guess = in.nextLine();
                if (isCorrect(guess, s.getSongName())) {
                    guessed = true;
                    System.out.println(String.format("Correct! The song was '%s'.", s.getSongName()));
                    this.score += 1;
                } else {
                    this.guesses -= 1;
                    guessesUsed += 1;
                    System.out.println("Not quite...");
                }
                // Wait 2 seconds so user sees message
                Helper.Delay(2000);
            }
            if (this.guesses <= 0 && !guessed) {
                this.won = false;
                return GameState.OVER;
            }
        }
        // User wins game if they reach a score of 30
        this.won = (this.score == 30);
        return GameState.OVER;
    }

    /**
     * Displays the game over screen and prompts the user to play again or quit.
     * 
     * <p>Shows different messages based on whether the player won or lost.
     * A win requires achieving a perfect score of 30/30. The method displays
     * the final score and total guesses used, then asks if the player wants
     * to continue.</p>
     * 
     * <p>User options:</p>
     * <ul>
     *   <li>'y' - Resets the game and returns to the home screen</li>
     *   <li>'n' - Displays a goodbye message and terminates the program</li>
     * </ul>
     * 
     * @return {@link GameState#HOME} if playing again, or calls {@code Helper.ExitGame()} to terminate
     */
    public GameState overLoop() {
        Helper.ClearConsole();
        String answer;
        do {
            if (this.won) {
                System.out.println(String.format("""
                        You won!
                        Score: 30 / 30
                        Guesses used: %d
                        """, this.getGuesses()));
            } else {
            System.out.println(String.format("""
                        Game Over:
                        Out of guesses
                        Score: %d / 30
                        """, this.score));
            }
            // Ask User to play again
            System.out.println("Would you like to play again? [y/n]");
            System.out.print("> ");
            answer = in.nextLine().toLowerCase();
        } while (!answer.equals("y") && !answer.equals("n"));
        switch (answer) {
            case "y":
                this.score = 0;
                return GameState.HOME;
            case "n":
                Helper.ClearConsole();
                System.out.println("Thank you for playing! Goodbye :)");
                Helper.Delay(2000);
                // Cleanup
                Helper.ClearConsole();
                // Terminate program
                Helper.ExitGame();    
            default:
                break;
        }
        return GameState.OVER;
    }

    /**
     * Checks if the player's guess matches the correct song name.
     * 
     * <p>Performs a case-insensitive comparison between the guess and solution.
     * This allows players to enter answers without worrying about exact capitalization.</p>
     * 
     * @param guess the player's guessed song name
     * @param solution the correct song name
     * @return {@code true} if the guess matches the solution (case-insensitive), {@code false} otherwise
     */
    public boolean isCorrect(String guess, String solution) {
        return guess.toLowerCase().equals(solution.toLowerCase());
    }

    /**
     * Loads song snippets from the snippets.txt file into memory.
     * 
     * <p>Reads the snippets file line by line, expecting alternating lines of
     * song names and snippet text. Empty lines are ignored. The method creates
     * {@link Snippet} objects for each song-snippet pair and adds them to the
     * snippets collection.</p>
     * 
     * <p>File format expected:</p>
     * <pre>
     * Song Name 1
     * Snippet text for song 1
     * Song Name 2
     * Snippet text for song 2
     * ...
     * </pre>
     * 
     * <p>If the file is not found, an error message is printed and the stack trace
     * is displayed for debugging purposes.</p>
     */
    public void seedSnippets() {
        String path = "snippets.txt";
        File snippetFile = new File(path);

        try (Scanner fileScanner = new Scanner(snippetFile)) {
            String name = null;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                
                if (line.isEmpty()) { continue; }

                if (name == null) {
                    name = line;
                } else {
                    this.snippets.add(new Snippet(line, name));
                    name = null;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(String.format("File %s was not found", path));
            e.printStackTrace();
        }
    }

    /**
     * Sets the number of remaining guesses based on difficulty and guesses already used.
     * 
     * <p>Calculates the remaining guesses by subtracting used guesses from the
     * difficulty's base guess allowance. This method is called at the start of
     * each round to reset or update the guess counter.</p>
     * 
     * <p>Base guess allowances by difficulty:</p>
     * <ul>
     *   <li>EASY: 10 guesses</li>
     *   <li>NORMAL: 7 guesses</li>
     *   <li>HARD: 5 guesses</li>
     *   <li>EXTREME: 3 guesses</li>
     *   <li>IMPOSSIBLE: 1 guess</li>
     * </ul>
     * 
     * @param difficulty the current game difficulty level
     * @param guessesUsed the number of guesses already consumed in previous rounds
     */
    public void setGuesses(GameDifficulty difficulty, int guessesUsed) { 
        switch (difficulty) {
            case GameDifficulty.EASY:
                this.guesses = 10 - guessesUsed;
                break;
            case GameDifficulty.NORMAL:
                this.guesses = 7 - guessesUsed;
                break;
            case GameDifficulty.HARD:
                this.guesses = 5 - guessesUsed;
                break;
            case GameDifficulty.EXTREME:
                this.guesses = 3 - guessesUsed;
                break;
            case GameDifficulty.IMPOSSIBLE:
                this.guesses = 1 - guessesUsed;
                break;
        
            default:
                break;
        }
    }

    /**
     * Gets the current game state.
     * 
     * @return the current {@link GameState}
     */
    public GameState getGameState() { return this.state; }
    
    /**
     * Gets the current game difficulty level.
     * 
     * @return the current {@link GameDifficulty}
     */
    public GameDifficulty getGameDifficulty() { return this.difficulty; }
    
    /**
     * Gets the player's current score.
     * 
     * @return the score as an integer (0-30)
     */
    public int getScore() { return this.score; }
    
    /**
     * Gets the number of remaining guesses for the current round.
     * 
     * @return the remaining guesses
     */
    public int getGuesses() { return this.guesses; }
    
    /**
     * Gets the collection of all song snippets.
     * 
     * @return an {@link ArrayList} containing all {@link Snippet} objects
     */
    public ArrayList<Snippet> getSnippets() { return this.snippets; }

    /**
     * Sets the game state to a new value.
     * 
     * @param state the new {@link GameState} to transition to
     */
    public void setGameState(GameState state) { this.state = state; }
    
    /**
     * Sets the game difficulty level.
     * 
     * @param difficulty the new {@link GameDifficulty} to apply
     */
    public void setDifficulty(GameDifficulty difficulty) { this.difficulty = difficulty; }
    
    /**
     * Sets the player's score.
     * 
     * @param score the new score value (should be between 0 and 30)
     */
    public void setScore(int score) { this.score = score; }
    
    /**
     * Sets the collection of song snippets.
     * 
     * @param snippets the new {@link ArrayList} of {@link Snippet} objects to use
     */
    public void setSnippets(ArrayList<Snippet> snippets) { this.snippets = snippets; }
}