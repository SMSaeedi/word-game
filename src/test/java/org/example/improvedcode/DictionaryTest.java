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
    void testPopWord() {
        String firstWord = dictionary.pop(null);
        assertEquals("apple", firstWord);

        String secondWord = dictionary.pop("apple");
        assertEquals("elephant", secondWord);
    }

    @Test
    void testRemoveWord() {
        dictionary.remove("apple");
        assertFalse(dictionary.contains("apple"));
    }

    @Test
    void testPopAndRemove() {
        String word = dictionary.pop(null);
        assertNotNull(word);
        dictionary.remove(word);
        assertFalse(dictionary.contains(word));
    }

}
