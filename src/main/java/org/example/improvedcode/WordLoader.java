package org.example.improvedcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

interface WordLoader {
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
    private final List<String> words;
    private final Set<String> wordSet;

    public Dictionary(WordLoader wordLoader) {
        this.words = new ArrayList<>(wordLoader.loadWords());
        this.wordSet = new HashSet<>(words);
    }

    public boolean contains(String word) {
        return wordSet.contains(word);
    }

    public String pop(String lastWord) {
        List<String> modifiableWords = new ArrayList<>(words);
        Iterator<String> iterator = modifiableWords.iterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            if (lastWord == null || word.charAt(0) == lastWord.charAt(lastWord.length() - 1)) {
                iterator.remove();
                wordSet.remove(word);
                return word;
            }
        }
        return null;
    }

    public void remove(String word) {
        if (wordSet.contains(word)) {
            words.remove(word);
            wordSet.remove(word);
        }
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
            lastWord = dictionary.pop(lastWord);
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
