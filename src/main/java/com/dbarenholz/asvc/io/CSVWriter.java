package com.dbarenholz.asvc.io;

import com.dbarenholz.asvc.Settings;
import com.dbarenholz.asvc.vocabitem.VocabItem;

import java.io.*;
import java.util.HashSet;

/**
 * Very simple CSV writer for use with {@link com.dbarenholz.asvc.App}.
 * Writes {@link com.dbarenholz.asvc.vocabitem.VocabItem}s to a file.
 */
public class CSVWriter implements Closeable {
    private PrintWriter writer;

    /**
     * Creates a CSVwriter for a file specified by a name
     *
     * @param fileName name of the file
     * @throws IOException if something goes wrong
     */
    public CSVWriter(String fileName) throws IOException {
        this(new File(fileName));
    }

    private CSVWriter(File file) throws IOException {
        writer = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));
    }

    @Override
    public void close() {
        writer.close();
    }

    /**
     * Writes all words in a set to a CSV file
     *
     * @param setOfAllProcessedWords set of words to write
     */
    public void write(HashSet<VocabItem> setOfAllProcessedWords) {
        for (VocabItem word : setOfAllProcessedWords) {
            write(word);
        }
    }

    /**
     * Writes a single word to a CSV file
     *
     * @param word word to write
     */
    private void write(VocabItem word) {
        String[] toWrite = new String[]{
                // TODO: Fix writing method for anki
//                word.getExpression(),
//                word.getMeaning(),
//                word.getReading(),
//                word.getSentence(),
//                "[sound:" + word.getAudio() + "]"
        };
        write(toWrite);
    }

    /**
     * Writes a string array to a file
     *
     * @param arr array to write
     */
    private void write(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for (String word : arr) {
            sb.append(word).append(Settings.delim);
        }
        sb.deleteCharAt(sb.lastIndexOf(Settings.delim));

        writer.write(sb.toString());
    }
}

