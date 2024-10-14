package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class WorkGameFixedInputTest {
    @Test
    void testCorrectGuess() {
        WorkGameFixedInput game = new WorkGameFixedInput("mahsa", 5);
        List<String> inputs = Arrays.asList("m", "a", "h", "s");
        Supplier<String> inputSupplier = createInputSupplier(inputs);

        game.start(inputSupplier);

        assertTrue(game.isWordGuessed(), "The word should be guessed correctly.");
    }

    @Test
    void testIncorrectGuess() {
        WorkGameFixedInput game = new WorkGameFixedInput("mahsa", 5);
        List<String> inputs = Arrays.asList("x", "y", "z");
        Supplier<String> inputSupplier = createInputSupplier(inputs);

        game.start(inputSupplier);

        assertFalse(game.isWordGuessed(), "The word should not be guessed.");
        assertEquals(5, game.getWrongLetters().size(),
                "There should be 5 wrong guesses plus 3 chars and 2 empty chars");
    }

    @Test
    void testRedundantGuess() {
        WorkGameFixedInput game = new WorkGameFixedInput("mahsa", 5);
        List<String> inputs = Arrays.asList("m", "m", "a");
        Supplier<String> inputSupplier = createInputSupplier(inputs);

        game.start(inputSupplier);

        assertFalse(game.isWordGuessed(), "The word should be guessed correctly.");
        assertEquals(5, game.getWrongLetters().size(),
                "There should be only 1 wrong guess and 4 spaces.");
    }

    private Supplier<String> createInputSupplier(List<String> inputs) {
        Iterator<String> iterator = inputs.iterator();
        return () -> iterator.hasNext() ? iterator.next() : "";
    }
}