package org.example.improvedcode.list;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

class FileWordLoaderTest {

    @Test
    void testLoadWordsFromFile() throws Exception {
        BufferedReader bufferedReaderMock = mock(BufferedReader.class);

        FileReader fileReaderMock = mock(FileReader.class);
        /**
         * While your test passing without mocks may indicate that it works correctly under the given conditions,
         * introducing mocks can enhance the reliability, speed,
         * and maintainability of your tests by isolating dependencies and ensuring
         * that you're only testing the unit in question.
         */
        whenNew(FileReader.class).withArguments("dictionary.txt").thenReturn(fileReaderMock);
        whenNew(BufferedReader.class).withArguments(fileReaderMock).thenReturn(bufferedReaderMock);

        FileWordLoader wordLoader = new FileWordLoader();
        List<String> words = wordLoader.loadWords();

        assertEquals(17, words.size());
        assertTrue(words.contains("abandon"));
        assertTrue(words.contains("aah"));
        assertFalse(words.contains("banana's"));
        assertFalse(words.contains("Orange"));
    }
}
