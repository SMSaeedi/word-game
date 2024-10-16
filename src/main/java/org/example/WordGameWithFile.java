package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

public class WordGameWithFile {
    private final String wordToGuess;
    private final Set<Character> guessedLetters;
    private final List<Character> wrongLetters;
    private final char[] currentGuess;
    private final int maxAttempts;

    public WordGameWithFile(String word, int maxAttempts) {
        this.wordToGuess = word.toLowerCase();
        this.maxAttempts = maxAttempts;
        this.guessedLetters = new HashSet<>();
        this.wrongLetters = new ArrayList<>();
        this.currentGuess = new char[word.length()];

        for (int i = 0; i < word.length(); i++)
            currentGuess[i] = '_';
    }

    public void start(Supplier<String> inputSupplier) {
        while (!isWordGuessed() && wrongLetters.size() < maxAttempts) {
            if (wrongLetters.size() >= maxAttempts) {
                System.err.println("Game Over! The word was: " + wordToGuess);
                break;
            }

            System.out.println("Current word: " + new String(currentGuess));
            System.out.println("Guessed wrong letters: " + wrongLetters);
            System.out.println("Attempts remaining: " + getRemainedAttempts());
            System.out.print("Enter a letter: ");

            String input = inputSupplier.get().toLowerCase();

            if (!input.isEmpty()) {
                char guess = input.charAt(0);
                processGuess(guess);
            } else {
                wrongLetters.add('_');
                System.err.println("Please guess a valid letter");
            }
        }

        if (isWordGuessed())
            System.out.println("Congratulations! You've guessed the word: " + wordToGuess);
    }

    private int getRemainedAttempts() {
        return maxAttempts - (guessedLetters.size() + wrongLetters.size());
    }

    private void processGuess(char guess) {
        if (guessedLetters.contains(guess) || wrongLetters.contains(guess)) {
            wrongLetters.add(guess);
            System.err.println("You've already guessed this letter");
            return;
        }

        if (wordToGuess.contains(Character.toString(guess))) {
            guessedLetters.add(guess);
            updateCurrentGuess(guess);
            System.out.println("Good guess!");
        } else {
            wrongLetters.add(guess);
            System.err.println("Wrong guess!");
        }
    }

    private void updateCurrentGuess(char guess) {
        for (int i = 0; i < wordToGuess.length(); i++)
            if (wordToGuess.charAt(i) == guess)
                currentGuess[i] = guess;
    }

    public boolean isWordGuessed() {
        return new String(currentGuess).equals(wordToGuess);
    }

    private static String getRandomWordFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("dictionary.txt"))) {
            Random random = new Random();
            String[] words = br.lines().toArray(String[]::new);
            return words[random.nextInt(words.length)];
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return "default";
        }
    }

    public static void main(String[] args) {
        String word = getRandomWordFromFile();
        WordGameWithFile game = new WordGameWithFile(word, word.length());
        game.start(() -> new Scanner(System.in).nextLine());
    }

    //Getter
    public List<Character> getWrongLetters() {
        return wrongLetters;
    }
}
