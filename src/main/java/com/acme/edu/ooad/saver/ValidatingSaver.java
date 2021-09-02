package com.acme.edu.ooad.saver;

import com.acme.edu.ooad.exception.ValidateException;
import com.acme.edu.ooad.message.Message;

public abstract class ValidatingSaver implements Saver {

    public void validate(Message message) throws ValidateException {
        if (message == null)
            throw new ValidateException(new IllegalArgumentException(ValidateException.NULL_INPUT_ERROR));
        if (message.getBody().isEmpty())
            throw new ValidateException(new IllegalArgumentException(ValidateException.EMPTY_INPUT_ERROR));
    }
}
