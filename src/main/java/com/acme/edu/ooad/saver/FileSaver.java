package com.acme.edu.ooad.saver;

import com.acme.edu.ooad.exception.SaveException;
import com.acme.edu.ooad.exception.ValidateException;
import com.acme.edu.ooad.message.Message;

import java.io.*;

public class FileSaver extends ValidatingSaver {
    private final String encoding;
    private final int bufferSize;
    private final String filePath;
    private final boolean isAppended;
    private BufferedWriter writer;

    public FileSaver(String encoding, int bufferSize, String filePath, boolean isAppended)
            throws SaveException {
        this.encoding = encoding;
        this.bufferSize = bufferSize;
        this.filePath = filePath;
        this.isAppended = isAppended;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filePath, isAppended);
            OutputStreamWriter intermediaryWriter = new OutputStreamWriter(
                    new BufferedOutputStream(fileOutputStream, bufferSize), encoding);
            this.writer = new BufferedWriter(intermediaryWriter);
        } catch (FileNotFoundException e) {
            throw new SaveException(SaveException.FILE_NOT_FOUND, e);
        } catch (UnsupportedEncodingException e) {
            try {
                fileOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            throw new SaveException(SaveException.UNSUPPORTED_ENCODING, e);
        }
    }

    @Override
    public void save(Message message) throws SaveException, ValidateException {
        try {
            super.validate(message);
            writer.write(message.toString() + System.lineSeparator());
        } catch (IOException e) {
            throw new SaveException(SaveException.ON_WRITE_FILE_ERROR, e);
        }
    }

    @Override
    public void close() throws SaveException {
        try {
            writer.close();
        } catch (IOException e) {
            throw new SaveException(SaveException.ON_CLOSE_FILE_ERROR, e);
        }
    }

}
