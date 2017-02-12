package com.thetrainline.enhanced;

import java.io.IOException;
import java.io.Writer;

/**
 * A class for writing csv data.
 * <p>
 * <strong>Note:</strong>
 * <p>
 * This class is not threadsafe.
 */
class CsvWriterImpl implements CsvWriter {

    @Override
    public void writeWith(Writer writer, String... columns) throws IOException {
        if (null == columns || columns.length == 0) return;

        try {
            String joinedColumns = String.join(CsvUtils.DELIMITER, columns);
            writer.write(joinedColumns);
        } finally {
            writer.close();
        }
    }

}
