package com.amazon.controller;

import com.amazon.entity.Item;
import com.amazon.service.AdminCsvService;
import com.amazon.service.CustomerItemService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.amazon.constant.CsvHeader.BRAND;
import static com.amazon.constant.CsvHeader.DESCRIPTION;
import static com.amazon.constant.CsvHeader.ID;
import static com.amazon.constant.CsvHeader.FOR_SALE;
import static com.amazon.constant.CsvHeader.NAME;
import static com.amazon.constant.CsvHeader.PRICE;
import static com.amazon.constant.CsvHeader.QUANTITY;
import static com.amazon.constant.CsvHeader.RATE;
import static com.amazon.constant.CsvHeader.THUMBNAIL;

@RestController
@RequestMapping("/admin/csv")
public class AdminCsvController {

    private static final String TEMPLATE_FILE = "template.csv";

    private final AdminCsvService adminCsvService;
    private final CustomerItemService itemService;

    public AdminCsvController(AdminCsvService adminCsvService,
                              CustomerItemService itemService) {
        this.adminCsvService = adminCsvService;
        this.itemService = itemService;
    }

    @PostMapping("/import")
    public String importItemsByCsv(@RequestParam("csv") MultipartFile csv, HttpServletResponse response) throws IOException {

        if(Objects.isNull(csv)){
            response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Missing csv file.");
            return null;
        }

        try (InputStream csvInputStream = csv.getInputStream(); InputStreamReader reader = new InputStreamReader(csvInputStream, StandardCharsets.UTF_8)) {

            CSVParser csvParser = CSVFormat.EXCEL.withHeader().parse(reader);
            List<CSVRecord> csvRecordList = csvParser.getRecords();

            if (!adminCsvService.headersValid(csvParser)) {
                response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Headers are not valid.");
                return null;
            }

            List<List<CSVRecord>> largeCsvLists = adminCsvService.breakIntoSmallLists(csvRecordList);

            int countRecords = 0;
            List<Item> items;
            for (List<CSVRecord> smallCsvLists : largeCsvLists) {
                items = new ArrayList<>(smallCsvLists.size());
                for (CSVRecord record : smallCsvLists) {
                    items.add(new Item(Integer.parseInt(record.get(ID)),
                            record.get(NAME),
                            Double.parseDouble(record.get(PRICE)),
                            record.get(BRAND), record.get(DESCRIPTION),
                            Double.parseDouble(record.get(RATE)),
                            Boolean.parseBoolean(record.get(FOR_SALE)),
                            record.get(THUMBNAIL),
                            Integer.parseInt(record.get(QUANTITY))));
                }
                try {
                    itemService.saveAll(items);
                    countRecords += smallCsvLists.size();
                } catch(Exception e) {
                    response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage());
                    return null;
                }
            }
            return "Imported " + countRecords + " records";
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(TEMPLATE_FILE);
        InputStreamResource file = new InputStreamResource(classPathResource.getInputStream());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + TEMPLATE_FILE)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
