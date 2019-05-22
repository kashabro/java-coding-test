package com.connectgroup;

import com.connectgroup.dto.LogExtract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DataFilterer {
    public static Collection<?> filterByCountry(Reader source, String country) {

        return new BufferedReader(source).lines().skip(1).map(s -> s.split(","))
                .map(v -> new LogExtract(Long.valueOf(v[0]), v[1], Long.valueOf(v[2])))
                .filter(logExtract -> logExtract.getCountryCode().equals(country))
                .collect(Collectors.toList());

    }

    public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
        return new BufferedReader(source).lines().skip(1).map(s -> s.split(","))
                .map(v -> new LogExtract(Long.valueOf(v[0]), v[1], Long.valueOf(v[2])))
                .filter(logExtract -> logExtract.getCountryCode().equals(country))
                .filter(logExtract -> logExtract.getResponseTime() > limit)
                .collect(Collectors.toList());
    }

    public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {

        BufferedReader br = new BufferedReader(source);

       List<LogExtract> logExtractList= br.lines().skip(1).map(s -> s.split(","))
                .map(v -> new LogExtract(Long.valueOf(v[0]), v[1], Long.valueOf(v[2])))
                .collect(Collectors.toList());

        List<Long> responseTimes = logExtractList.stream().map(i -> i.getResponseTime()).collect(Collectors.toList());

        OptionalDouble average = responseTimes.stream().mapToDouble(i->i).average();


        return logExtractList.stream().filter(logExtract -> logExtract.getResponseTime() > average.getAsDouble())
                .collect(Collectors.toList());
    }
}