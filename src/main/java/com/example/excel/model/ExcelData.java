package com.example.excel.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.Data;

@Data
@Entity
@Table(name = "excel_data")
public class ExcelData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp date;
    private BigDecimal volume;
    private BigDecimal total;
    private BigDecimal toKazakhstan;
    private BigDecimal toKyrgyzstan;
    private BigDecimal inflow;
    private String month;
}
