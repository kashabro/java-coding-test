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

public class DataFilterTimeResponseAboveLimitTest {

    @Test
    public void shouldReturnEmptyCollection_WhenLogFileIsEmpty() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/empty"), "GB",100).isEmpty());
    }

    @Test
    public void shouldReturnEmptyCollection_WhenCountryCodeisNotFound_SingleLine() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/single-line"), "US",100).isEmpty());
    }

    @Test
    public void shouldReturnEmptyCollection_WhenResponseTimeUnderLimit_SingleLine() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/single-line"), "GB",300).isEmpty());
    }

    @Test
    public void shouldReturnEmptyCollection_WhenResponseTimeUnderLimit_BySingleDigit_SingleLine() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/single-line"), "GB",201).isEmpty());
    }

    @Test
    public void shouldReturnCollection_WhenResponseTimeAboveLimit_SingleLine() throws FileNotFoundException {
        List<LogExtract> logExtractList = DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/single-line"),"GB",100)
                .stream().map(m -> (LogExtract)m).collect(Collectors.toList());
        assertEquals("GB", logExtractList.get(0).getCountryCode());
        assertEquals(200, logExtractList.get(0).getResponseTime());
        assertEquals(1431592497, logExtractList.get(0).getTimeStamp());
    }

    @Test
    public void shouldReturnCollection_WhenResponseTimeAboveLimit_BySingleDigit_SingleLine() throws FileNotFoundException {
        List<LogExtract> logExtractList = DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/single-line"),"GB",199)
                .stream().map(m -> (LogExtract)m).collect(Collectors.toList());
        assertEquals("GB", logExtractList.get(0).getCountryCode());
        assertEquals(200, logExtractList.get(0).getResponseTime());
        assertEquals(1431592497, logExtractList.get(0).getTimeStamp());
    }

    @Test
    public void shouldReturnEmptyCollection_WhenCountryCodeisNotFound_MultiLines() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"), "DK",100).isEmpty());
    }

    @Test
    public void shouldReturnEmptyCollection_WhenResponseTimeUnderLimit_MultiLine() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"), "US",900).isEmpty());
    }

    @Test
    public void shouldReturnCollection_WhenResponseTimeAboveLimit_MutliLine() throws FileNotFoundException {
        List<LogExtract> logExtractList = DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"),"US",600)
                .stream().map(m -> (LogExtract)m).collect(Collectors.toList());
        assertEquals(2, logExtractList.size());

    }

    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }
}
