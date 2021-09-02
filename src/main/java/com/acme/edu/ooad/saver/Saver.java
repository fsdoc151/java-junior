package com.acme.edu.ooad.saver;

import com.acme.edu.ooad.exception.SaveException;
import com.acme.edu.ooad.exception.ValidateException;
import com.acme.edu.ooad.message.Message;

public interface Saver {
    void save(Message message) throws ValidateException, SaveException;

    void close() throws SaveException;
}
