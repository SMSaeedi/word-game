package org.example.improvedcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DictionaryTest {
    private Dictionary dictionary;

    @BeforeEach
    void setUp() {
        WordLoader wordLoaderMock = mock(WordLoader.class);
        List<String> fakeWords = Arrays.asList("apple", "elephant", "tree");
        when(wordLoaderMock.loadWords()).thenReturn(fakeWords);

        dictionary = new Dictionary(wordLoaderMock);
    }

    @Test
    void testContains() {
        assertTrue(dictionary.contains("apple"));
        assertFalse(dictionary.contains("banana"));
    }

    @Test
    void testGetNextWordWord() {
        String firstWord = dictionary.getNextWord(null);
        assertEquals("apple", firstWord);

        String secondWord = dictionary.getNextWord("apple");
        assertEquals("elephant", secondWord);
    }

    @Test
    void testRemoveWord() {
        dictionary.remove("apple");
        assertFalse(dictionary.contains("apple"));
    }

    @Test
    void testGetNextWordAndRemove() {
        String word = dictionary.getNextWord(null);
        assertNotNull(word);
        dictionary.remove(word);
        assertFalse(dictionary.contains(word));
    }

}
