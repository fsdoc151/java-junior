package com.acme.edu.ooad.saver;

import com.acme.edu.SysoutCaptureAndAssertionAbility;
import com.acme.edu.ooad.exception.SaveException;
import com.acme.edu.ooad.exception.ValidateException;
import com.acme.edu.ooad.message.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConsoleSaverTest implements SysoutCaptureAndAssertionAbility {
    private Saver saver = new ConsoleSaver();

    @Test
    public void shouldGetErrorWhenMessageIsNull() {
        Message nullMessage = null;

        assertThrows(
                ValidateException.class,
                () -> saver.save(nullMessage)
        );
    }

    @Test
    public void shouldGetErrorWhenMessageIsEmpty() {
        Message emptyMessageStub = mock(Message.class);
        when(emptyMessageStub.getBody()).thenReturn("");

        assertThrows(
                ValidateException.class,
                () -> saver.save(emptyMessageStub)
        );
    }

    @Test
    public void shouldPrintWhenSaveMessage() throws SaveException, ValidateException {
        resetOut();
        captureSysout();

        Message messageStub = mock(Message.class);
        when(messageStub.getBody()).thenReturn("message to print");
        when(messageStub.toString()).thenReturn("message to print");

        saver.save(messageStub);
        assertSysoutEquals("message to print" + System.lineSeparator());
    }
}
