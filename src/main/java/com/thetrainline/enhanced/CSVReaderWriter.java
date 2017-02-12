package com.thetrainline.enhanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

/**
 * Helper class for reading and writing CSV data.
 *
 * Concerning this comments in the FEEDBACK file:
 * •   Doesn't handle different symbols for column split and new lines.
 * •   Only one type of characters handled for columns separators and EOL 
 * •   No generics 
 * Can't understand these comments, because of lack of concrete specification of the problem.
 * There is not mentioned anything about different symbols in the requirement.
 */
public class CSVReaderWriter {

    private DependencyProvider provider;
    private CsvReader cvsReader;
    private CsvWriter cvsWriter;
    private String fileName;

    public CSVReaderWriter() {
        provider = new DependencyProvider();
        cvsReader = provider.getCsvReader();
        cvsWriter = provider.getCsvWriter();
    }

    /**
     * <strong>NOTE:</strong>
     * Parameter {@code mode} is disregarded.
     */
    public synchronized void open(String fileName, Mode mode) throws Exception {
        this.fileName = fileName;
    }

    public synchronized void write(String... columns) throws IOException {
        if (null == fileName) return;
        Writer bufferedWriter = provider.getWriter(fileName);
        if(null == bufferedWriter) return;
        cvsWriter.writeWith(bufferedWriter, columns);
    }

    public synchronized boolean read(String[] columns) throws IOException {
        if (null == fileName) return false;
        BufferedReader reader = provider.getReader(fileName);
        if(null == reader) return false;
        String[] result = cvsReader.readWith(reader);
        if (null == result || result.length < 2) return false;
        columns[0] = result[0];
        columns[1] = result[1];
        return true;
    }

    /**
     * @return {@code False} regardless on input parameters.
     * @deprecated This method is <strong>broken</strong>. Use {@link CSVReaderWriter#read(String[])} instead.
     */
    @Deprecated
    public boolean read(String column1, String column2) throws IOException {
        return false;
    }

    /**
     * @deprecated Closing is no longer necessary.
     */
    @Deprecated
    public void close() throws IOException {
        fileName = null;
    }

    /**
     * @deprecated Usage of this enum is no longer required.
     */
    @Deprecated
    public enum Mode {
        Read(1), Write(2);

        private int mode;

        Mode(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return mode;
        }
    }

}
