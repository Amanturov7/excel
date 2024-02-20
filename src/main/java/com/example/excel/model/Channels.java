package com.example.excel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "channels")
public class Channels {

    @Id
    private Long id;
    private String name;
    private String invertNumber;
    private String code;
    private String buildProject;
    private LocalDate dateStart;
    private Double totalArea;
    private Integer numberStructures;
    private String typeCategory;
    private String typeObject;
    private String appointment;
    private Double projectCost;
    private Double carryingamount;
    private Double sum;

}
