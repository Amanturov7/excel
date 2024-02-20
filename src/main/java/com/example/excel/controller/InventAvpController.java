package com.example.excel.controller;

import com.example.excel.model.InvertariaztionAssociationData;
import com.example.excel.model.TotalDataAssociation;
import com.example.excel.repository.InvertariaztionAssociationDataRepository;
import com.example.excel.repository.TotalDataAssociationRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class InventAvpController {

    @Autowired
    private InvertariaztionAssociationDataRepository repository;

    @Autowired
    private TotalDataAssociationRepository totalDataAssociationRepository;

    @PostMapping("/rest/parse/avp")
    public ResponseEntity<List<InvertariaztionAssociationData>> readColumns(@RequestParam("file") MultipartFile file) {
        try {
            List<InvertariaztionAssociationData> columnData = readInvertariaztionAssociationDataFromExcel(file);
            repository.saveAll(columnData); // Save all data to the database

            List<TotalDataAssociation> totalData = readTotalDataAssociationFromExcel(file);
            totalDataAssociationRepository.saveAll(totalData); // Save total data to the database

            return ResponseEntity.ok(columnData);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private List<InvertariaztionAssociationData> readInvertariaztionAssociationDataFromExcel(MultipartFile file) throws IOException {
        List<InvertariaztionAssociationData> columnData = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0); // Assume first sheet is the one you want to read
        int lastRowNum = sheet.getLastRowNum();
        List<String> currentRecord = new ArrayList<>();
        boolean isMergedTable = false;

        for (int i = 0; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            Cell cellA = row.getCell(0); // Column A is index 0
            Cell cellB = row.getCell(1); // Column B is index 1
            Cell cellC = row.getCell(2); // Column C is index 2

            if (cellA != null && cellB != null && cellC != null) {
                String cellValueA = getStringValue(cellA);
                String cellValueB = getStringValue(cellB);
                String cellValueC = getStringValue(cellC);

                boolean isMerged = isMergedCell(sheet, i, 0);

                if (isMerged) {
                    if (!currentRecord.isEmpty() && isMergedTable) {
                        columnData.add(mapDataToEntity(currentRecord));
                        currentRecord.clear();
                    }
                    isMergedTable = true;
                    currentRecord.add(cellValueA);
                } else {
                    if (isMergedTable) {
                        currentRecord.add(cellValueB + ": " + cellValueC); // Add B as key and C as value
                    }
                }
            }
        }

        // Add the last record if it's not empty
        if (!currentRecord.isEmpty()) {
            columnData.add(mapDataToEntity(currentRecord));
        }

        workbook.close();
        return columnData;
    }

    private boolean isMergedCell(Sheet sheet, int rowNum, int colNum) {
        for (CellRangeAddress range : sheet.getMergedRegions()) {
            if (range.isInRange(rowNum, colNum)) {
                return true;
            }
        }
        return false;
    }


    private List<TotalDataAssociation> readTotalDataAssociationFromExcel(MultipartFile file) throws IOException {
        List<TotalDataAssociation> totalDataAssociationList = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(1); // Assume second sheet is the one you want to read
        totalDataAssociationList = readTotalDataAssociation(sheet);
        workbook.close();
        return totalDataAssociationList;
    }

    private List<TotalDataAssociation> readTotalDataAssociation(Sheet sheet) {
        List<TotalDataAssociation> totalDataAssociationList = new ArrayList<>();

        int startRow = 2; // Начинаем считывать с третьей строки
        int endRow = sheet.getLastRowNum();

        for (int i = startRow; i <= endRow; i++) {
            Row row = sheet.getRow(i);

            if (isEmptyRow(row)) {
                break; // Прекращаем чтение, если строка пустая
            }

            TotalDataAssociation totalDataAssociation = new TotalDataAssociation();

            // Чтение данных из столбцов B-W
            totalDataAssociation.setName(getStringValueFromSecondSheet(row.getCell(1)));
            totalDataAssociation.setOrganizationEnum(getStringValueFromSecondSheet(row.getCell(2)));
            totalDataAssociation.setDateRegistration(getStringValueFromSecondSheet(row.getCell(3)));
            totalDataAssociation.setRegNumber(getStringValueFromSecondSheet(row.getCell(4)));
            totalDataAssociation.setOwnershipForm(getStringValueFromSecondSheet(row.getCell(5)));
            totalDataAssociation.setEconomicActivity(getStringValueFromSecondSheet(row.getCell(6)));
            totalDataAssociation.setInn(getStringValueFromSecondSheet(row.getCell(7)));
            totalDataAssociation.setOkpo(getStringValueFromSecondSheet(row.getCell(8)));
            totalDataAssociation.setRegion(getStringValueFromSecondSheet(row.getCell(9)));
            totalDataAssociation.setDistrict(getStringValueFromSecondSheet(row.getCell(10)));
            totalDataAssociation.setOkmot(getStringValueFromSecondSheet(row.getCell(11)));
            totalDataAssociation.setWaterCouncil(getStringValueFromSecondSheet(row.getCell(12)));
            totalDataAssociation.setAddress(getStringValueFromSecondSheet(row.getCell(13)));
            totalDataAssociation.setBankName(getStringValueFromSecondSheet(row.getCell(14)));
            totalDataAssociation.setNameFilialBank(getStringValueFromSecondSheet(row.getCell(15)));
            totalDataAssociation.setRegionBank(getStringValueFromSecondSheet(row.getCell(16)));
            totalDataAssociation.setDistrictBank(getStringValueFromSecondSheet(row.getCell(17)));
            totalDataAssociation.setCity(getStringValueFromSecondSheet(row.getCell(18)));
            totalDataAssociation.setAddressBank(getStringValueFromSecondSheet(row.getCell(19)));
            totalDataAssociation.setBik(getStringValueFromSecondSheet(row.getCell(20)));
            totalDataAssociation.setNumberKorespondent(getStringValueFromSecondSheet(row.getCell(21)));
            totalDataAssociation.setCurrentAccountOrganization(getStringValueFromSecondSheet(row.getCell(22)));

            totalDataAssociationList.add(totalDataAssociation);
        }

        return totalDataAssociationList;
    }

    private boolean isEmptyRow(Row row) {
        int lastCellNum = row.getLastCellNum();
        for (int i = 1; i < lastCellNum; i++) {
            Cell cell = row.getCell(i);
            if (cell != null && !getStringValue(cell).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private String getStringValue(Cell cell) {
        if (cell == null) {
            return ""; // Return empty string if cell is null
        }

        cell.setCellType(CellType.STRING); // Convert cell type to string
        return cell.getStringCellValue().trim(); // Get cell value and trim extra spaces
    }

    private String getStringValueFromSecondSheet(Cell cell) {
        if (cell == null) {
            return ""; // Return empty string if cell is null
        }

        cell.setCellType(CellType.STRING); // Convert cell type to string
        return cell.getStringCellValue().trim(); // Get cell value and trim extra spaces
    }

    private InvertariaztionAssociationData mapDataToEntity(List<String> data) {
        InvertariaztionAssociationData entity = new InvertariaztionAssociationData();
        for (String entry : data) {
            String[] keyValue = entry.split(": ");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "Должность":
                        entity.setPosition(value);
                        break;
                    case "Фамилия":
                        entity.setLastName(value);
                        break;
                    case "Имя":
                        entity.setFirstName(value);
                        break;
                    case "Отчество":
                        entity.setSurname(value);
                        break;
                    case "Пол":
                        entity.setGender(value);
                        break;
                    case "ИНН/ПИН":
                        entity.setInn(value);
                        break;
                    case "Номер телефона":
                        entity.setPhoneNumber(value);
                        break;
                    case "Email":
                        entity.setEmail(value);
                        break;
                    case "Образование":
                        entity.setEducation(value);
                        break;
                    case "Специальность":
                        entity.setSpeciality(value);
                        break;
                    case "Гос. Сотрудник":
                        entity.setGovEmployee(value);
                        break;
                    default:
                        // Handle unknown key or ignore
                        break;
                }
            }
        }
        return entity;
    }
}
