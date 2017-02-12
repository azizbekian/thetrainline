package com.thetrainline;


import com.thetrainline.enhanced.CSVReaderWriter;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    private static final String FILE_NAME = "test.txt";

    public static void main(String[] args) {

        CSVReaderWriter csvReaderWriter = new CSVReaderWriter();

        try {
            csvReaderWriter.open(FILE_NAME, CSVReaderWriter.Mode.Write);
            csvReaderWriter.write("aaa", "bbb", "ccc", "ddd", "eee");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvReaderWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            csvReaderWriter.open(FILE_NAME, CSVReaderWriter.Mode.Read);
            String[] arr = new String[2];
            boolean success = csvReaderWriter.read(arr);
            if (success) System.out.println(Arrays.toString(arr));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvReaderWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            csvReaderWriter.open(FILE_NAME, CSVReaderWriter.Mode.Read);
            String column1 = "column1";
            String column2 = "column2";
            boolean success = csvReaderWriter.read(column1, column2);
            if (success) System.out.println(column1 + " " + column2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvReaderWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
