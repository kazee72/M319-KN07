package KN07.M319_KN07;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;

public class Game {
    
    private GameState state;
    private GameDifficulty difficulty;
    private int score;
    private int guesses;
    private ArrayList<Snippet> snippets = new ArrayList<>();
    private Scanner in = new Scanner(System.in);

    public Game() {
        this.score = 0;
        this.state = GameState.HOME;
        this.seedSnippets();
    }

    public void gameLoop() {
        while (this.state != GameState.OVER) {
            switch (this.state) {
                case GameState.HOME:
                    this.setDifficulty(homeLoop());
                    this.setGameState(GameState.GUESSING);
                    break;
                case GameState.GUESSING:
                    guessingLoop();
                    break;
                default:
                    break;
            }
        }
    }

    public GameDifficulty homeLoop() {
        int userChoice = -1;

        // Get the users difficulty choice
        do {
            //Helper.ClearConsole();
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
                continue;
            }
        } while (userChoice < 1 || userChoice > 5);

        return GameDifficulty.values()[userChoice - 1];
    }

    public GameState guessingLoop() {
        Collections.shuffle(snippets);
        if (in.hasNextLine()) in.nextLine();
        for (Snippet s : snippets) {
            // Reset guesses after every snippet
            this.setGuesses(this.difficulty);
            // Flag if user has guessed correctly
            boolean guessed = false;
            String guess;
            while (!guessed && this.guesses > 0) {
                Helper.ClearConsole();
                System.out.println(String.format("""
                    --- Guess the Song ---
                    Score: %d
                    Guesses: %d
                    Snippet: %s""", this.score, this.guesses, s.getText()));
                System.out.print("> ");
                guess = in.nextLine();
                if (isCorrect(guess, s.getSongName())) {
                    guessed = true;
                    System.out.println(String.format("Correct the song was '%s'.", s.getSongName()));
                    this.score += 1;
                } else {
                    this.guesses -= 1;
                    System.out.println("Not quite...");
                }
                // Wait 2 seconds so user sees message
                Helper.Delay(2000);
            }
            if (!guessed) {
                System.out.println(String.format("""
                        Game Over:
                        Out of guesses
                        Score: %d
                        """, this.score));
                Helper.Delay(2000);
            } else {
                System.out.println(String.format("""
                        Nice you guessed all song
                        """));
                Helper.Delay(2000);
            }
        }
        return GameState.HOME;
    }

    public boolean isCorrect(String guess, String solution) {
        return guess.toLowerCase().equals(solution.toLowerCase());
    }


    public void seedSnippets() {
        String path = "snippets.txt";
        File snippetFile = new File(path);

        try (Scanner fileScanner = new Scanner(snippetFile)) {
            String name = null;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                // Skip line if its empty
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

    public void setGuesses(GameDifficulty difficulty) { 
        switch (difficulty) {
            case GameDifficulty.EASY:
                this.guesses = 10;
                break;
            case GameDifficulty.NORMAL:
                this.guesses = 7;
                break;
            case GameDifficulty.HARD:
                this.guesses = 5;
                break;
            case GameDifficulty.EXTREME:
                this.guesses = 3;
                break;
            case GameDifficulty.IMPOSSIBLE:
                this.guesses = 1;
                break;
        
            default:
                break;
        }
     }

    public GameState getGameState() { return this.state; }
    public GameDifficulty getGameDifficulty() { return this.difficulty; }
    public int getScore() { return this.score; }
    public int getGuesses() { return this.guesses; }
    public ArrayList<Snippet> getSnippets() { return this.snippets; }

    public void setGameState(GameState state) { this.state = state; }
    public void setDifficulty(GameDifficulty difficulty) { this.difficulty = difficulty; }
    public void setScore(int score) { this.score = score; }
    public void setSnippets(ArrayList<Snippet> snippets) { this.snippets = snippets; }
}
