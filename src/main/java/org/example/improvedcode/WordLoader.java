package org.example.improvedcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

interface WordLoader {
    /**
     * Allowing:  different implementations, Flexibility and Extensibility (OCP), Dependency Inversion Principle (DIP),
     * Testing and Mocking, Separation of Concerns
     */
    List<String> loadWords();
}

class FileWordLoader implements WordLoader {

    @Override
    public List<String> loadWords() {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dictionary.txt"))) {
            String line;
            while ((line = reader.readLine()) != null)
                if (!line.endsWith("'s") && line.equals(line.toLowerCase()))
                    words.add(line);

            Collections.shuffle(words);
        } catch (IOException e) {
            System.err.println("loadWords failed " + e.getMessage());
        }
        return words;
    }
}

class Dictionary {
    private final Set<String> words;

    public Dictionary(WordLoader wordLoader) {
        /**
         * Replacing the List and Set with a single LinkedHashSet.
         * To achieve: order preservation and efficient lookups: Like a Set, it offers O(1) time complexity for lookups.
        */
        this.words = new LinkedHashSet<>(wordLoader.loadWords());
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public String getNextWord(String lastWord) {
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            if (lastWord == null || word.charAt(0) == lastWord.charAt(lastWord.length() - 1)) {
                iterator.remove();
                return word;
            }
        }
        return null;
    }

    public void remove(String word) {
        words.remove(word);
    }
}

class WordGame {
    private final Dictionary dictionary;

    public WordGame(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void startGame() {
        int score = 0;
        String lastWord = null;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            lastWord = dictionary.getNextWord(lastWord);
            if (lastWord == null) {
                System.err.println("No more words available.");
                break;
            }

            System.out.printf("%s\n", lastWord);
            String word = scanner.next();

            if (!dictionary.contains(word)) {
                System.err.println("Wrong!\nScore: " + score);
                break;
            } else {
                score += 1;
                dictionary.remove(word);
                lastWord = word;
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        WordLoader wordLoader = new FileWordLoader();
        Dictionary dictionary = new Dictionary(wordLoader);
        WordGame game = new WordGame(dictionary);

        game.startGame();
    }
}
