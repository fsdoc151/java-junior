package com.acme.edu.ooad.controller;

import com.acme.edu.ooad.exception.*;
import com.acme.edu.ooad.message.Message;
import com.acme.edu.ooad.saver.ValidatingSaver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LoggerControllerTest {
    private ValidatingSaver saverMock = mock(ValidatingSaver.class);
    private LoggerController controllerSut = new LoggerController(saverMock);

    @Test
    public void shouldSaveLastLoggedMessageWhenFlushAndLastLoggedMessageIsNotNull() throws SaveException, ValidateException {
        Message messageStub = mock(Message.class);
        controllerSut.lastLoggedMessage = messageStub;
        try {
            controllerSut.flush();
        } catch (FlushException e) {
            e.printStackTrace();
        }

        verify(saverMock, times(1)).save(messageStub);
    }

    @Test
    public void shouldGetFlushErrorWhenSaverGetError() throws SaveException, ValidateException {
        Message emptyMessageStub = mock(Message.class);
        controllerSut.lastLoggedMessage = emptyMessageStub;
        doThrow(SaveException.class).when(saverMock).save(emptyMessageStub);

        assertThrows(
                FlushException.class,
                () -> controllerSut.flush()
        );
    }

    @Test
    public void shouldGetLogErrorWhenSaverGetError() throws SaveException, ValidateException {
        Message messageStub = mock(Message.class);

        Message lastLoggedMessageStub = mock(Message.class);
        when(lastLoggedMessageStub.getInstanceToPrint(any())).thenReturn(messageStub);
        controllerSut.lastLoggedMessage = lastLoggedMessageStub;

        doThrow(SaveException.class).when(saverMock).save(messageStub);

        assertThrows(
                LogException.class,
                () -> controllerSut.log(messageStub)
        );
    }

    @Test
    public void shouldSaveInstanceToPrintWhenLogAndLastLoggedMessageIsNotNull() throws LogException, SaveException, ValidateException {
        Message logMessageStub = mock(Message.class);
        controllerSut.lastLoggedMessage = mock(Message.class);
        Message saveMessageStub = mock(Message.class);
        when(controllerSut.lastLoggedMessage.getInstanceToPrint(logMessageStub))
                .thenReturn(saveMessageStub);

        controllerSut.log(logMessageStub);
        verify(saverMock, times(1)).save(saveMessageStub);
    }

    @Test
    public void shouldUpdateLastLoggedMessageWhenLogAndLastLoggedMessageIsNotNull() throws LogException {
        Message logMessageStub = mock(Message.class);
        Message newInstanceMessageStub = mock(Message.class);
        controllerSut.lastLoggedMessage = mock(Message.class);
        when(controllerSut.lastLoggedMessage.getNewInstance(logMessageStub))
                .thenReturn(newInstanceMessageStub);

        controllerSut.log(logMessageStub);
        assertEquals(newInstanceMessageStub, controllerSut.lastLoggedMessage);
    }

    @Test
    public void shouldCloseSaverWhenClose() throws CloseException, SaveException {
        controllerSut.close();
        verify(saverMock, times(1)).close();
    }

    @Test
    public void shouldGetCloseErrorWhenSaverThrowsError() throws SaveException {
        doThrow(SaveException.class).when(saverMock).close();
        assertThrows(CloseException.class, () -> controllerSut.close());
    }

}
