package com.acme.edu.ooad.saver;

import com.acme.edu.ooad.exception.ReadException;
import com.acme.edu.ooad.exception.SaveException;
import com.acme.edu.ooad.exception.ValidateException;
import com.acme.edu.ooad.message.Message;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FileSaverTest {
    private static final String filePath = "outputTest.log";

    @AfterAll
    public static void tearsDown() throws IOException, InterruptedException {
        Thread.sleep(10000);
        Files.deleteIfExists(Path.of(filePath));
    }

    @Test
    public void shouldSaveMessageToFileWhenSaveMessage() throws SaveException, ValidateException, ReadException {
        Saver saver = new FileSaver(Charset.defaultCharset().toString(), 256, filePath, false);
        Message messageStub = mock(Message.class);
        String messageBody = "message to print";
        when(messageStub.getBody()).thenReturn(messageBody);
        when(messageStub.toString()).thenReturn(messageBody);

        saver.save(messageStub);
        saver.close();
        try {
            String fileLines = String.join(System.lineSeparator(),
                    (Files.readAllLines(Path.of(filePath), Charset.defaultCharset())));
            assertEquals(messageBody, fileLines);
        } catch (IOException e) {
            throw new ReadException(ReadException.ON_OPEN_FILE_ERROR, e);
        }
    }

    @Test
    public void shouldGetErrorWhenFileNotFound() {
        SaveException exception = assertThrows(SaveException.class,
                () -> new FileSaver(Charset.defaultCharset().toString(), 256,
                        "unreachableDir/"+filePath, false).close());
        assertTrue(exception.getMessage().contains(SaveException.FILE_NOT_FOUND));
    }

    @Test
    public void shouldGetErrorWhenEncodingUnsupported() {
        SaveException exception = assertThrows(SaveException.class,
                () -> new FileSaver("UNSUPPORTED-CHARSET", 256,
                        filePath, false).close());
        assertTrue(exception.getMessage().contains(SaveException.UNSUPPORTED_ENCODING));
    }

    @Test
    public void shouldGetErrorWhenMessageIsNull() throws SaveException {
        Message nullMessage = null;
        Saver saver = new FileSaver(Charset.defaultCharset().toString(), 256,
                filePath, false);
        assertThrows(
                ValidateException.class,
                () -> saver.save(nullMessage)
        );
        saver.close();
    }

    @Test
    public void shouldGetErrorWhenMessageIsEmpty() throws SaveException {
        Saver saver = new FileSaver(Charset.defaultCharset().toString(), 256,
                filePath, false);
        Message emptyMessageStub = mock(Message.class);
        when(emptyMessageStub.getBody()).thenReturn("");

        assertThrows(
                ValidateException.class,
                () -> saver.save(emptyMessageStub)
        );
        saver.close();
    }

}
