package com.dbarenholz.asvc.io;

import com.dbarenholz.asvc.Settings;

import java.io.*;

/**
 * Very simple CSV reader for use with {@link com.dbarenholz.asvc.App}.
 * Reads {@link com.dbarenholz.asvc.vocabitem.VocabItem}s from a file.
 */
public class CSVReader implements Closeable {
    private BufferedReader reader;

    private CSVReader(File file) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(file));
    }

    /**
     * Creates a reader reading a file
     *
     * @param fileName file to read
     * @throws FileNotFoundException if file is not found.
     */
    public CSVReader(String fileName) throws FileNotFoundException {
        this(new File(fileName));
    }

    private String[] readLine() throws IOException {
        String line = reader.readLine();

        if (line == null) {
            return null;
        } else {
            return line.split(Settings.delim);
        }
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     *
     * <p> As noted in {@link AutoCloseable#close()}, cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * <em>mark</em> the {@code Closeable} as closed, prior to throwing
     * the {@code IOException}.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        reader.close();
    }
}
