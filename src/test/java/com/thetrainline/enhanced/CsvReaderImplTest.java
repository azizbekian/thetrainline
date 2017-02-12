package com.thetrainline.enhanced;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CsvReaderImplTest {

    private static final String FILE_NAME = "test.txt";

    @Mock
    private BufferedReader reader;
    @Mock
    private DependencyProvider provider;

    @InjectMocks
    private CsvReaderImpl csvReader;

    @Test
    public void readTabulatedLine() throws IOException {
        when(provider.getReader(FILE_NAME)).thenReturn(reader);
        when(reader.readLine())
                .thenReturn("aaa" + CsvUtils.DELIMITER + "bbb" + CsvUtils.DELIMITER + "ccc" + CsvUtils.DELIMITER + "ddd")
                .thenReturn(null);

        csvReader.readWith(reader);

        verify(reader, times(2)).readLine();
        verify(reader).close();
    }

    @Test
    public void readOrdinaryLine() throws Exception {
        when(provider.getReader(FILE_NAME)).thenReturn(reader);
        when(reader.readLine())
                .thenReturn("aaa" + " " + "bbb" + "\n" + "ccc" + "\r" + "ddd")
                .thenReturn(null);

        String[] actual = csvReader.readWith(reader);

        assertArrayEquals(new String[]{"aaa" + " " + "bbb" + "\n" + "ccc" + "\r" + "ddd"}, actual);
        verify(reader, times(2)).readLine();
        verify(reader).close();
    }

}