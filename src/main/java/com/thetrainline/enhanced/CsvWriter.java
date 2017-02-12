package com.thetrainline.enhanced;

import java.io.IOException;
import java.io.Writer;

public interface CsvWriter {

    /**
     * Writes items of {@code columns} into a file with name {@code fileName} after concatenating them with
     * {@link CsvUtils#DELIMITER}.
     *
     * @param writer  {@link Writer} to writeWith with.
     * @param columns Columns to be written into destination file.
     */
    void writeWith(Writer writer, String... columns) throws IOException;

}
