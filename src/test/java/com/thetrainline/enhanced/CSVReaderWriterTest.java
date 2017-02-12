package com.thetrainline.enhanced;

import com.thetrainline.enhanced.CSVReaderWriter.Mode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CSVReaderWriterTest {

    private static final String FILE_NAME = "test.txt";

    @Mock
    private CsvReader csvReader;
    @Mock
    private CsvWriter csvWriter;
    @Mock
    private Writer writer;
    @Mock
    private BufferedReader reader;
    @Mock
    private DependencyProvider provider;

    @InjectMocks
    private CSVReaderWriter csvReaderWriter;

    @Test
    public void openingDoesNothing() throws Exception {
        csvReaderWriter.open(FILE_NAME, null);
        verifyZeroInteractions(provider);
        verifyZeroInteractions(csvReader);
        verifyZeroInteractions(csvWriter);
    }

    @Test
    public void readingWhenOpenNotCalled() throws Exception {
        boolean actual = csvReaderWriter.read(new String[] {});
        assertEquals(false, actual);
        verifyZeroInteractions(provider);
        verifyZeroInteractions(csvReader);
    }

    @Test
    public void writingWhenOpenNotCalled() throws Exception {
        csvReaderWriter.write("aaa", "bbb");
        verifyZeroInteractions(provider);
        verifyZeroInteractions(csvWriter);
    }

    @Test
    public void closingDoesNothing() throws IOException {
        csvReaderWriter.close();
        Mockito.verifyZeroInteractions(provider);
        Mockito.verifyZeroInteractions(csvReader);
        Mockito.verifyZeroInteractions(csvWriter);
    }

    @Test
    public void writingColumns() throws Exception {
        when(provider.getWriter(FILE_NAME)).thenReturn(writer);

        csvReaderWriter.open(FILE_NAME, CSVReaderWriter.Mode.Write);
        csvReaderWriter.write("aaa", "bbb");

        verify(provider).getWriter(FILE_NAME);
        verify(csvWriter).writeWith(writer, "aaa", "bbb");
    }

    @Test
    public void readingColumns() throws Exception {
        String[] mockedResult = new String[]{"aaa", "bbb"};
        String[] input = new String[2];
        when(provider.getReader(FILE_NAME)).thenReturn(reader);
        when(csvReader.readWith(reader)).thenReturn(mockedResult);

        csvReaderWriter.open(FILE_NAME, CSVReaderWriter.Mode.Read);
        boolean actual = csvReaderWriter.read(input);

        assertEquals(true, actual);
        assertArrayEquals(input, mockedResult);
        verify(provider).getReader(FILE_NAME);
        verify(csvReader).readWith(reader);
    }

    @Test
    public void readingColumn() throws Exception {
        String[] mockedResult = new String[]{"aaa"};
        String[] input = new String[2];
        when(provider.getReader(FILE_NAME)).thenReturn(reader);
        when(csvReader.readWith(reader)).thenReturn(mockedResult);

        csvReaderWriter.open(FILE_NAME, CSVReaderWriter.Mode.Read);
        boolean actual = csvReaderWriter.read(input);

        assertEquals(false, actual);
        assertNotEquals(input, mockedResult);
        verify(provider).getReader(FILE_NAME);
        verify(csvReader).readWith(reader);
    }

    @Test
    public void readBrokenMethod() throws IOException {
        boolean actual = csvReaderWriter.read("column1", "column2");
        assertEquals(false, actual);
        verifyZeroInteractions(provider);
        verifyZeroInteractions(csvReader);
    }

    @Test
    public void readEnum() {
        Mode mode = Mode.Read;

        int actual = mode.getMode();

        assertEquals(1, actual);
    }

    @Test
    public void writeEnum() {
        Mode mode = Mode.Write;

        int actual = mode.getMode();

        assertEquals(2, actual);
    }

}