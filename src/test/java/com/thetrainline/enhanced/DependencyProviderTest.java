package com.thetrainline.enhanced;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DependencyProviderTest {

    @InjectMocks
    private DependencyProvider dependencyProvider;

    @Test
    public void getReaderWithNullFileName() throws FileNotFoundException {
        BufferedReader actual = dependencyProvider.getReader(null);

        assertEquals(null, actual);
    }

    @Test
    public void getReaderWithEmptyFileName() throws FileNotFoundException {
        BufferedReader actual = dependencyProvider.getReader("");

        assertEquals(null, actual);
    }

    @Test
    public void getWriterWithNullFileName() throws IOException {
        Writer actual = dependencyProvider.getWriter(null);

        assertEquals(null, actual);
    }

    @Test
    public void getWriterWithEmptyFileName() throws IOException {
        Writer actual = dependencyProvider.getWriter("");

        assertEquals(null, actual);
    }

}