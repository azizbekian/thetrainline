package com.thetrainline.enhanced;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.Writer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CsvWriterImplTest {

    private static final String FILE_NAME = "test.txt";

    @Mock
    private Writer writer;
    @Mock
    private DependencyProvider provider;

    @InjectMocks
    private CsvWriterImpl csvWriter;

    @Test
    public void writeNullColumns() throws IOException {
        when(provider.getWriter(FILE_NAME)).thenReturn(writer);

        csvWriter.writeWith(writer, null);

        verifyZeroInteractions(writer);
    }

    @Test
    public void writeEmptyColumns() throws IOException {
        when(provider.getWriter(FILE_NAME)).thenReturn(writer);

        csvWriter.writeWith(writer, new String[] {});

        verifyZeroInteractions(writer);
    }

    @Test
    public void writeColumns() throws Exception {
        when(provider.getWriter(FILE_NAME)).thenReturn(writer);

        csvWriter.writeWith(writer, "aaa", "bbb");

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(argumentCaptor.capture());
        assertEquals("aaa" + CsvUtils.DELIMITER + "bbb", argumentCaptor.getValue());
        verify(writer).close();
    }

}