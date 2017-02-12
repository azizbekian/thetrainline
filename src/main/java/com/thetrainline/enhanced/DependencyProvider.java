package com.thetrainline.enhanced;

import java.io.*;

class DependencyProvider {

    BufferedReader getReader(String fileName) throws FileNotFoundException {
        if (null == fileName || fileName.equals("")) return null;

        FileReader fileReader = new FileReader(fileName);
        return new BufferedReader(fileReader);
    }

    Writer getWriter(String fileName) throws IOException {
        if (null == fileName || fileName.equals("")) return null;

        FileWriter fileWriter = new FileWriter(fileName);
        return new BufferedWriter(fileWriter);
    }

    CsvReader getCsvReader() {
        return new CsvReaderImpl();
    }

    CsvWriter getCsvWriter() {
        return new CsvWriterImpl();
    }

}
