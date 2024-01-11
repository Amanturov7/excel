package com.example.excel.controller;

import com.example.excel.model.ExcelData;
import com.example.excel.service.ExcelDataService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@RestController
public class ExcelReaderController {

    private final ExcelDataService excelDataService;

    @Autowired
    public ExcelReaderController(ExcelDataService excelDataService) {
        this.excelDataService = excelDataService;
    }

    @PostMapping("/rest/read-excel")
    public ResponseEntity<String> readExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<List<List<String>>> allExcelData = readAllExcelData(file);
            List<ExcelData> entities = convertAllToEntities(allExcelData);

            excelDataService.saveAll(entities);

            return ResponseEntity.status(HttpStatus.CREATED).body("Data successfully saved to the database.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading Excel file.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving data to the database.");
        }
    }

    private List<List<List<String>>> readAllExcelData(MultipartFile file) throws IOException {
        List<List<List<String>>> allExcelData = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            List<List<String>> excelDataList = new ArrayList<>();
            readSheetData(sheet, excelDataList);
            allExcelData.add(excelDataList);
        }

        workbook.close();
        return allExcelData;
    }

    private void readSheetData(Sheet sheet, List<List<String>> result) {
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            List<String> rowData = readRowData(row);
            result.add(rowData);
        }
    }

    private List<String> readRowData(Row row) {
        List<String> rowData = new ArrayList<>();
        Iterator<Cell> cellIterator = row.cellIterator();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();

            switch (cell.getCellType()) {
                case STRING:
                    rowData.add(cell.getStringCellValue().trim());
                    break;
                case NUMERIC:
                    rowData.add(String.valueOf(cell.getNumericCellValue()));
                    break;
                default:
                    rowData.add("");
            }
        }

        return rowData;
    }

    private List<ExcelData> convertAllToEntities(List<List<List<String>>> allExcelData) {
        List<ExcelData> entities = new ArrayList<>();
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        int currentYear = 1997;

        for (List<List<String>> excelDataList : allExcelData) {
            if (excelDataList.size() <= 6 || excelDataList.get(6).isEmpty()) {
                continue;
            }

            int columnsPerMonth = 6;
            int currentMonthIndex = 0;

            for (int i = 0; i < excelDataList.get(0).size(); i += columnsPerMonth) {
                int currentDay = 1;
                for (int j = 6; j < excelDataList.size(); j++) {
                    List<String> rowData = excelDataList.get(j);

                    if (rowData.size() <= i || rowData.get(i) == null || rowData.get(i).isEmpty()) {
                        continue;
                    }

                    try {
                        String month = months[currentMonthIndex];

                        ExcelData entity = new ExcelData();
                        entity.setInflow(getBigDecimalValue(rowData, i + 5));
                        entity.setToKyrgyzstan(getBigDecimalValue(rowData, i + 2));
                        entity.setToKazakhstan(getBigDecimalValue(rowData, i + 3));
                        entity.setTotal(getBigDecimalValue(rowData, i + 4));
                        entity.setVolume(getBigDecimalValue(rowData, i + 1));

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, currentYear);
                        calendar.set(Calendar.MONTH, Arrays.asList(months).indexOf(month));
                        calendar.set(Calendar.DAY_OF_MONTH, currentDay++);
                        entity.setDate(new Timestamp(calendar.getTimeInMillis()));
                        entity.setMonth(month);

                        entities.add(entity);

                    } catch (NumberFormatException e) {
                        System.err.println("Error converting string to BigDecimal: " + e.getMessage());
                    }
                }
                currentMonthIndex = (currentMonthIndex + 1) % 12;
            }

            currentYear++;
        }
        return entities;
    }





    private BigDecimal getBigDecimalValue(List<String> rowData, int index) {
        try {
            return rowData.size() > index && !rowData.get(index).isEmpty() ? new BigDecimal(rowData.get(index).replace(",", "")) : null;
        } catch (NumberFormatException e) {
            System.err.println("Error converting string to BigDecimal: " + e.getMessage());
            return null;
        }
    }


}

