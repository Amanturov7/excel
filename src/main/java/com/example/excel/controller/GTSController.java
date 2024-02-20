package com.example.excel.controller;

import com.example.excel.model.Channels;
import com.example.excel.model.Hydropost;
import com.example.excel.model.Outlets;
import com.example.excel.service.ChannelsService;
import com.example.excel.service.HydropostService;
import com.example.excel.service.OutletService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class GTSController {

    private final HydropostService hydropostService;
    private final OutletService outletService;
    private final ChannelsService channelsService;


    @Autowired
    public GTSController(HydropostService hydropostService, OutletService outletService, ChannelsService channelsService) {
        this.hydropostService = hydropostService;
        this.outletService = outletService;
        this.channelsService = channelsService;
    }

    @PostMapping("/rest/gts/hydroposts")
    public String parseHydropostsExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<Hydropost> hydroposts = parseHydropostsFromExcel(file);

            hydropostService.saveHydroposts(hydroposts);

            return "Hydroposts parsed and saved successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to parse Hydroposts from Excel.";
        }
    }

    @PostMapping("/rest/gts/outlets")
    public String parseOutletsExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<Outlets> outlets = parseOutletsFromExcel(file);
            outletService.saveOutlets(outlets);

            return "outlets parsed and saved successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to parse outlets from Excel.";
        }
    }


    @PostMapping("/rest/gts/channels")
    public String parseChannelsFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<Channels> channels = parseChannelsExcel(file);
            channelsService.saveChannels(channels);

            return "channels parsed and saved successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to parse channels from Excel.";
        }
    }

    private List<Hydropost> parseHydropostsFromExcel(MultipartFile file) throws IOException {
        List<Hydropost> hydroposts = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheet("Гидропосты");

        Iterator<Row> iterator = sheet.iterator();

        // Skip the first row (header)
        if (iterator.hasNext()) {
            iterator.next();
        }

        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            Hydropost hydropost = new Hydropost();

            hydropost.setName(getStringValueFromCell(currentRow.getCell(1)));
            hydropost.setHydro_facility(getStringValueFromCell(currentRow.getCell(2)));
            hydropost.setCode(getStringValueFromCell(currentRow.getCell(3)));
            hydropost.setCoordinates(getStringValueFromCell(currentRow.getCell(4)));
            hydropost.setType_category(getStringValueFromCell(currentRow.getCell(5)));
            hydropost.setType_measurement(getStringValueFromCell(currentRow.getCell(6)));

            Cell dateCell = currentRow.getCell(7);
            if (dateCell != null && dateCell.getCellType() == CellType.NUMERIC) {
                hydropost.setDate_start(dateCell.getLocalDateTimeCellValue().toLocalDate());
            }
            hydropost.setCarryingamount(getStringValueFromCell(currentRow.getCell(8)));
            hydropost.setSum(getStringValueFromCell(currentRow.getCell(9)));
            hydropost.setTechnicalstatusenum(getStringValueFromCell(currentRow.getCell(10)));
            hydropost.setOperationalstatusenum(getStringValueFromCell(currentRow.getCell(11)));

            if (hydropost.getName().isEmpty()) {
                break;
            }

            hydroposts.add(hydropost);
        }

        workbook.close();
        return hydroposts;
    }




    private List<Outlets> parseOutletsFromExcel(MultipartFile file) throws IOException {
        List<Outlets> outlets = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheet("Водовыпуски");

        Iterator<Row> iterator = sheet.iterator();

        if (iterator.hasNext()) {
            iterator.next();
        }

        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            Outlets outlets1 = new Outlets();

            outlets1.setSystemName(getStringValueFromCell(currentRow.getCell(1)));
            outlets1.setHydro_facility(getStringValueFromCell(currentRow.getCell(2)));
            outlets1.setName(getStringValueFromCell(currentRow.getCell(3)));
            outlets1.setCode(getStringValueFromCell(currentRow.getCell(4)));
            outlets1.setCoordinates(getStringValueFromCell(currentRow.getCell(5)));
            outlets1.setPermeability(getStringValueFromCell(currentRow.getCell(6)));
            Cell dateCell = currentRow.getCell(7);
            if (dateCell != null && dateCell.getCellType() == CellType.NUMERIC) {
                outlets1.setDate_start(dateCell.getLocalDateTimeCellValue().toLocalDate());
            }
            outlets1.setCarryingamount(getStringValueFromCell(currentRow.getCell(8)));
            outlets1.setSum(getStringValueFromCell(currentRow.getCell(9)));
            outlets1.setTechnicalstatusenum(getStringValueFromCell(currentRow.getCell(10)));
            outlets1.setOperationalstatusenum(getStringValueFromCell(currentRow.getCell(11)));


            if (outlets1.getName().isEmpty()) {
                break;
            }

            outlets.add(outlets1);
        }
        workbook.close();
        return outlets;
    }




    private List<Channels> parseChannelsExcel(MultipartFile file) throws IOException {
        List<Channels> channels = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheet("Каналы");

        Iterator<Row> iterator = sheet.iterator();

        if (iterator.hasNext()) {
            iterator.next();
        }

        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            Channels channels1 = new Channels();

            channels1.setName(getStringValueFromCell(currentRow.getCell(1)));
            channels1.setInvertNumber(getStringValueFromCell(currentRow.getCell(2)));
            channels1.setCode(getStringValueFromCell(currentRow.getCell(3)));
            channels1.setBuildProject(getStringValueFromCell(currentRow.getCell(4)));
            Cell dateCell = currentRow.getCell(5);
            if (dateCell != null && dateCell.getCellType() == CellType.NUMERIC) {
                channels1.setDateStart(dateCell.getLocalDateTimeCellValue().toLocalDate());
            }
            channels1.setTotalArea(getDoubleValueFromCell(currentRow.getCell(6)));
            channels1.setNumberStructures(getIntValueFromCell(currentRow.getCell(7)));
            channels1.setTypeCategory(getStringValueFromCell(currentRow.getCell(8)));
            channels1.setTypeObject(getStringValueFromCell(currentRow.getCell(8)));
            channels1.setAppointment(getStringValueFromCell(currentRow.getCell(8)));
            channels1.setProjectCost(getDoubleValueFromCell(currentRow.getCell(8)));
            channels1.setCarryingamount(getDoubleValueFromCell(currentRow.getCell(8)));
            channels1.setSum(getDoubleValueFromCell(currentRow.getCell(9)));
            if (channels1.getName().isEmpty()) {
                break;
            }
            channels.add(channels1);
        }
        workbook.close();
        return channels;
    }




    private String getStringValueFromCell(Cell cell) {
        if (cell == null) {
            return "";
        } else {
            cell.setCellType(CellType.STRING);
            return cell.getStringCellValue().trim();
        }
    }

    private Double getDoubleValueFromCell(Cell cell) {
        if (cell == null) {
            return 0.0;
        } else {
            cell.setCellType(CellType.NUMERIC);
            return cell.getNumericCellValue();
        }
    }

    private int getIntValueFromCell(Cell cell) {
        if (cell == null) {
            return 0;
        } else {
            cell.setCellType(CellType.NUMERIC);
            return (int) cell.getNumericCellValue();
        }
    }

}
