package com.example.excel.service;

import com.example.excel.model.ExcelData;

import java.util.List;

public interface ExcelDataService {
    List<ExcelData> saveAll(List<ExcelData> excelDataList);
}
