package com.amazon.service;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.amazon.constant.CsvHeader.BRAND;
import static com.amazon.constant.CsvHeader.DESCRIPTION;
import static com.amazon.constant.CsvHeader.ID;
import static com.amazon.constant.CsvHeader.IN_STOCK_STATUS;
import static com.amazon.constant.CsvHeader.NAME;
import static com.amazon.constant.CsvHeader.PRICE;
import static com.amazon.constant.CsvHeader.QUANTITY;
import static com.amazon.constant.CsvHeader.RATE;
import static com.amazon.constant.CsvHeader.THUMBNAIL;

@Service
public class AdminCsvService {

    public static final int BREAK_LIST_SIZE = 500;

    public boolean headersValid(CSVParser csvParser) {
        Map<String, Integer> headerMap = csvParser.getHeaderMap();
        if (Objects.isNull(headerMap.get(ID.toString()))
            || Objects.isNull(headerMap.get(NAME.toString()))
            || Objects.isNull(headerMap.get(PRICE.toString()))
            || Objects.isNull(headerMap.get(BRAND.toString()))
            || Objects.isNull(headerMap.get(DESCRIPTION.toString()))
            || Objects.isNull(headerMap.get(RATE.toString()))
            || Objects.isNull(headerMap.get(IN_STOCK_STATUS.toString()))
            || Objects.isNull(headerMap.get(THUMBNAIL.toString()))
            || Objects.isNull(headerMap.get(QUANTITY.toString()))) {
            return false;
        }
        return true;
    }

    public List<List<CSVRecord>> breakIntoSmallLists(List<CSVRecord> largeList) {
        final int BREAK_SIZE = BREAK_LIST_SIZE;
        int largeListSize = largeList.size();
        int indexStart = 0; //inclusive
        int indexEnd = BREAK_SIZE; //exclusive
        List<List<CSVRecord>> smallLists = new ArrayList<>();
        if (BREAK_SIZE > largeListSize) {
            smallLists.add(largeList);
        } else {
            smallLists.add(largeList.subList(indexStart, indexEnd));
        }
        while (indexEnd <= largeListSize) {
            indexStart += BREAK_SIZE;
            indexEnd += BREAK_SIZE;
            if (indexEnd > largeListSize) indexEnd = largeListSize;
            smallLists.add(largeList.subList(indexStart, indexEnd));
            if (indexEnd == largeListSize) break;
        }
        return smallLists;
    }
}
