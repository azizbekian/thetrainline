package com.thetrainline.enhanced;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * A class for reading csv data.
 * <p>
 * <strong>Note:</strong>
 * <p>
 * This class is not threadsafe.
 */
class CsvReaderImpl implements CsvReader {

    @Override
    public String[] readWith(BufferedReader reader) throws IOException {
        try {
            String tmp;
            StringBuilder builder = new StringBuilder();
            while ((tmp = reader.readLine()) != null) {
                builder.append(tmp);
            }
            String line = builder.toString();
            return line.split(CsvUtils.DELIMITER);
        } finally {
            reader.close();
        }
    }

}
