package com.thetrainline.source;

import java.io.*;

// No documentation provided. Describe the responsibility of the class and it's public methods.
// By convention class should have CamelCased naming, regardless whether they consist of abbreviation or no.
// Thus, a better naming would be `CSVReaderWriter`.
// "Csv reader and writer". Obviously, this class is responsible for multiple things, which violates SOLID principles,
// particularly Single Responsibility Principle. What should be a better design is to have CsvReader and CsvWriter and a manager
// who just delegates particular action to either CsvWriter or CsvReader. Then each of the components would have one responsibility.
// This class is not threadsafe. Either declare, that it is not threadsafe, or preferably make it threadsafe.
public class CSVReaderWriter {
    // by convention fields should not be prefixed with _
    private BufferedReader _bufferedReader = null;
    private BufferedWriter _bufferedWriter = null;

    // this enum represents a flag of itself, may be changed to a boolean field
    public enum Mode {
        // by convention this should be UPPERCASED, because enums are static final fields
        Read (1), Write(2);

        private int _mode;
        Mode(int mode) {
            this._mode = mode;
        }
        public int getMode() {
            return _mode;
        }
    }

    // the clients of this class should not be responsible for opening and closing closeables
    // as a client of this class what I need is just to writeWith and readWith, I'm not interested how the opening and closing of
    // closeables happens inside this class. Therefore, do not provide public `open` and `close` methods to clients and
    // do not leave a responsibility on client to correctly handle the state of closeables, i.e. client may misuse your
    // class and forget to close the closeable after reading a data, which would result in a memory leak. Or unintentionally
    // perform writing after calling this method with reading mode, which will result in NPE.
    public void open(String fileName, Mode mode) throws Exception {
        // by convention braces should be opened on the same line of the statement
        if (mode == Mode.Read)
        {
            _bufferedReader = new BufferedReader(new FileReader(fileName));
        }
        else if (mode == Mode.Write)
        {
            // fileWriter is not closed. Should be declared as a field of a class in order to be closed in `close()` method
            FileWriter fileWriter = new FileWriter(fileName);
            _bufferedWriter = new BufferedWriter(fileWriter);
        }
        else // after substituting Mode enum with boolean this block may be removed
        {
            throw new Exception("Unknown file mode for " + fileName);
        }
    }

    public void write(String... columns) throws IOException {
        // perform a check for edge-case, i.e. if `columns` is null or empty immediately return from this method

        // StringBuilder would be desirable for performing string concatenation
        // Note, StringBuilder is not synchronized, which means this method should be synchronized in order to safely use StringBuilder
        // Otherwise StringBuffer maybe used, which is synchronized
        String outPut = "";

        // The performance of this `for` maybe slightly enhanced by performing ++i instead of i++
        for (int i = 0; i < columns.length; i++)
        {
            outPut += columns[i];
            // Performing additional if operation on each loop
            // Maybe changed with a `for` loop that loops until columns.length - 1 and one concatenation operation after the loop.
            if ((columns.length - 1) != i)
            {
                // see comment at line 93: after declaring static final field use that field instead
                outPut += "\t";
            }
        }

        writeLine(outPut);
    }

    // Document what this method does, particularly what it returns
    // The signature of this method should be changed to a more intuitive
    // Now `readWith` accepts String[], fills it with data, and returns boolean showing whether it succeed
    // Changing to `public String[] readWith()` is also not desirable, because client may try to access the 3rd element of the
    // array, but the array is always filled with two elements
    // Change to `public Pair<String, String> readWith()` would make more sense for the client of the class.
    public boolean read(String[] columns) throws IOException {
        // these should be declared as private static final fields of a class
        final int FIRST_COLUMN = 0;
        final int SECOND_COLUMN = 1;

        String line;
        String[] splitLine;

        // this should declared as private static final field of a class
        String separator = "\t";

        line = readLine();
        // readLine() may return null, null-check needed before performing `split()`
        splitLine = line.split(separator);

        if (splitLine.length == 0)
        {
            columns[0] = null;
            columns[1] = null;

            return false;
        }
        else
        {
            columns[0] = splitLine[FIRST_COLUMN];
            columns[1] = splitLine[SECOND_COLUMN];

            return true;
        }
    }

    // this method is broken, because variables column1, column2 are scoped to this method only, thus client won't
    // see changes after performing `readWith(column1, column2)`
    public boolean read(String column1, String column2) throws IOException {
        final int FIRST_COLUMN = 0;
        final int SECOND_COLUMN = 1;

        String line;
        String[] splitLine;

        String separator = "\t";

        line = readLine();

        if (line == null)
        {
            column1 = null;
            column2 = null;

            return false;
        }

        splitLine = line.split(separator);

        if (splitLine.length == 0)
        {
            column1 = null;
            column2 = null;

            return false;
        }
        else
        {
            column1 = splitLine[FIRST_COLUMN];
            column2 = splitLine[SECOND_COLUMN];

            return true;
        }
    }

    private void writeLine(String line) throws IOException {
        _bufferedWriter.write(line);
    }

    private String readLine() throws IOException {
        return _bufferedReader.readLine();
    }

    // The client of the class should not be responsible for keeping track and closing objects. That's responsibility of this
    // class. Thus, make this method private and close this objects manually. A better approach would be to entirely get
    // rid of this method and use try-with-resources approach if it's applicable.
    // Also close fileReader and fileWriter. See comments in `open()`
    public void close() throws IOException {
        if (_bufferedWriter != null)
        {
            _bufferedWriter.close();
        }

        if (_bufferedWriter != null)
        {
            _bufferedWriter.close();
        }
    }
}
