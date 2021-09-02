package com.acme.edu.ooad.saver;

import com.acme.edu.ooad.exception.SaveException;
import com.acme.edu.ooad.exception.ValidateException;
import com.acme.edu.ooad.message.Message;

public class ConsoleSaver extends ValidatingSaver {

    @Override
    public void save(Message message) throws ValidateException, SaveException {
        super.validate(message);
        System.out.println(message);
    }

    @Override
    public void close() {
    }
}
