package com.acme.edu.ooad.saver;

import com.acme.edu.ooad.exception.SaveException;
import com.acme.edu.ooad.exception.ValidateException;
import com.acme.edu.ooad.message.Message;

import java.io.IOException;

public interface Saver {
    void save(Message message) throws ValidateException, SaveException;
    default void close() throws IOException {}
}
