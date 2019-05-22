package com.connectgroup;

import com.connectgroup.dto.LogExtract;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DataFiltererTest {
    @Test
    public void shouldReturnEmptyCollection_WhenLogFileIsEmpty() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "GB").isEmpty());
    }

    @Test
    public void shouldReturnEmptyCollection_WhenCountryCodeisNotFound_SingleLine() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/single-line"), "US").isEmpty());
    }

    @Test
    public void shouldReturnCollection_FilteredByCountryCode_SingleLine() throws FileNotFoundException {

        List<LogExtract> logExtractList = DataFilterer.filterByCountry(openFile("src/test/resources/single-line"),"GB")
                .stream().map(m -> (LogExtract)m).collect(Collectors.toList());

        assertEquals("GB", logExtractList.get(0).getCountryCode());
        assertEquals(200, logExtractList.get(0).getResponseTime());
        assertEquals(1431592497, logExtractList.get(0).getTimeStamp());
    }

    @Test
    public void shouldReturnEmptyCollection_WhenCountryCodeisNotFound_MultiLines() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "DK").isEmpty());
    }

    @Test
    public void shouldReturnCollection_WhenMultipleCountryCodePresent_MultiLines() throws FileNotFoundException {
        List<LogExtract> logExtractList = DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"),"US")
                .stream().map(m -> (LogExtract)m).collect(Collectors.toList());

        assertEquals(3,logExtractList.size());
    }

    @Test
    public void shouldSingleCollection_WhenMultipleCountryCodePresent_MultiLines() throws FileNotFoundException {
        List<LogExtract> logExtractList = DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"),"DE")
                .stream().map(m -> (LogExtract)m).collect(Collectors.toList());

        assertEquals(1,logExtractList.size());
    }



    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }
}
