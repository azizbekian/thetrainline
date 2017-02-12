package com.thetrainline.enhanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public interface CsvReader {

    /**
     * Reads from {@code fileName} and splits the result by {@link CsvUtils#DELIMITER}.
     *
     * @param reader A {@link Reader} to read with.
     * @return An array of {@link String} with result of split. Returns {@code null} if source file does not exist.
     */
    String[] readWith(BufferedReader reader) throws IOException;

}
