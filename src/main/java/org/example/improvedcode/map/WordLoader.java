package org.example.improvedcode.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

interface WordLoader {
    Map<String, LinkedHashSet<String>> loadWords();
}

class FileWordLoader implements WordLoader {

    @Override
    public Map<String, LinkedHashSet<String>> loadWords() {
        Map<String, LinkedHashSet<String>> wordMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dictionary.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.endsWith("'s") && line.equals(line.toLowerCase())) {
                    String firstLetter = String.valueOf(line.charAt(0));
                    wordMap.putIfAbsent(firstLetter, new LinkedHashSet<>());
                    wordMap.get(firstLetter).add(line);
                }
            }
            for (String key : wordMap.keySet()) {
                List<String> tempList = new ArrayList<>(wordMap.get(key));
                Collections.shuffle(tempList);
                wordMap.put(key, new LinkedHashSet<>(tempList));
            }
        } catch (IOException e) {
            throw new RuntimeException("loadWords failed", e);
        }
        return wordMap;
    }
}

class Dictionary {
    private final Map<String, LinkedHashSet<String>> wordMap;

    public Dictionary(WordLoader wordLoader) {
        this.wordMap = new HashMap<>(wordLoader.loadWords());
    }

    public boolean contains(String word) {
        String firstLetter = String.valueOf(word.charAt(0));
        return wordMap.containsKey(firstLetter) && wordMap.get(firstLetter).contains(word);
    }

    public String pop(String lastWord) {
        String firstLetter = (lastWord == null) ? null : String.valueOf(lastWord.charAt(lastWord.length() - 1));

        if (firstLetter != null && wordMap.containsKey(firstLetter) && !wordMap.get(firstLetter).isEmpty()) {
            Iterator<String> iterator = wordMap.get(firstLetter).iterator();
            if (iterator.hasNext()) {
                String word = iterator.next();
                iterator.remove();
                return word;
            }
        }

        for (Map.Entry<String, LinkedHashSet<String>> entry : wordMap.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                Iterator<String> iterator = entry.getValue().iterator();
                String word = iterator.next();
                iterator.remove();
                return word;
            }
        }
        return null;
    }

    public void remove(String word) {
        String firstLetter = String.valueOf(word.charAt(0));
        if (wordMap.containsKey(firstLetter)) {
            wordMap.get(firstLetter).remove(word);
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
