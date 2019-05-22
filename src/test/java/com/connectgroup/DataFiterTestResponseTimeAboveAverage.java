package com.connectgroup;

import com.connectgroup.dto.LogExtract;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataFiterTestResponseTimeAboveAverage {

    @Test
    public void shouldReturnEmptyCollection_WhenLogFileIsEmpty() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/empty")).isEmpty());
    }

    @Test
    public void shouldReturnEmptyCollection_WhenSingleLine() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/single-line")).isEmpty());
    }

    @Test
    public void shouldReturnCollectionAboveAverage_MultiLine() throws FileNotFoundException {

        List<LogExtract> logExtractList = DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/multi-lines"))
                .stream().map(m -> (LogExtract)m).collect(Collectors.toList());

        assertEquals(3,logExtractList.size());
        assertTrue(logExtractList.get(0).getResponseTime() > 526.0);
        assertTrue(logExtractList.get(1).getResponseTime() > 526.0);
        assertTrue(logExtractList.get(2).getResponseTime() > 526.0);
    }

    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }



}
