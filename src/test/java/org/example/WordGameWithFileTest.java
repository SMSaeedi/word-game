package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class WordGameWithFileTest {

    @Test
    void testCorrectGuess() {
        WordGameWithFile game = new WordGameWithFile("mahsa", 5);
        List<String> inputs = Arrays.asList("m", "a", "h", "s");
        Supplier<String> inputSupplier = createInputSupplier(inputs);

        game.start(inputSupplier);

        assertTrue(game.isWordGuessed(), "The word should be guessed correctly.");
    }

    @Test
    void testIncorrectGuess() {
        WordGameWithFile game = new WordGameWithFile("mahsa", 5);
        List<String> inputs = Arrays.asList("x", "y", "z");
        Supplier<String> inputSupplier = createInputSupplier(inputs);

        game.start(inputSupplier);

        assertFalse(game.isWordGuessed(), "The word should not be guessed.");
        assertEquals(5, game.getWrongLetters().size(),
                "There should be 5 wrong guesses plus 3 chars and 2 empty chars");
    }

    @Test
    void testRedundantGuess() {
        WordGameWithFile game = new WordGameWithFile("mahsa", 5);
        List<String> inputs = Arrays.asList("m", "m", "a");
        Supplier<String> inputSupplier = createInputSupplier(inputs);

        game.start(inputSupplier);

        assertFalse(game.isWordGuessed(), "The word should be guessed correctly.");
        assertEquals(5, game.getWrongLetters().size(),
                "There should be only 1 wrong guess and 4 spaces.");
    }

    @Test
    void testGameOverWhenMaxAttemptsExceeded() {
        WordGameWithFile game = new WordGameWithFile("mahsa", 5);
        List<String> inputs = Arrays.asList("x", "y", "z", "w", "q");
        Supplier<String> inputSupplier = createInputSupplier(inputs);

        game.start(inputSupplier);

        assertFalse(game.isWordGuessed(), "The word should not be guessed.");
        assertEquals(5, game.getWrongLetters().size(), "All 5 attempts should be used.");
    }

    private Supplier<String> createInputSupplier(List<String> inputs) {
        Iterator<String> iterator = inputs.iterator();
        return () -> iterator.hasNext() ? iterator.next() : "";
    }

}