package com.example.excel.service;

import com.example.excel.model.ExcelData;
import com.example.excel.repository.ExcelDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExcelDataServiceImpl  implements ExcelDataService{
    private final ExcelDataRepository excelDataRepository;

    public ExcelDataServiceImpl(ExcelDataRepository excelDataRepository) {
        this.excelDataRepository = excelDataRepository;
    }

    @Override
    public List<ExcelData> saveAll(List<ExcelData> excelDataList) {
        return excelDataRepository.saveAll(excelDataList);
    }
}
