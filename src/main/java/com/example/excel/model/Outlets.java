package com.example.excel.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@Table(name = "outlets")
public class Outlets {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String hydro_facility;
        private String code;
        private String systemName;
        private String coordinates;
        private String type_category;
        private String permeability;
        private String type_measurement;
        private LocalDate date_start;
        private String carryingamount;
        private String sum;
        private String technicalstatusenum;
        private String operationalstatusenum;
}

