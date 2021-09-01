package com.acme.edu.ooad.saver;

import com.acme.edu.ooad.exception.ReadException;
import com.acme.edu.ooad.exception.SaveException;
import com.acme.edu.ooad.exception.ValidateException;
import com.acme.edu.ooad.message.Message;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileSaverTest {
    private static String filePath = "outputTest.log";

    @AfterAll
    public static void tearsDown() throws IOException {
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
}
