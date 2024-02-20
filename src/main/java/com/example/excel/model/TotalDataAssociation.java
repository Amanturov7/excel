package com.example.excel.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "total_data_associations")
@Data
public class TotalDataAssociation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String organizationEnum;
    private String dateRegistration;
    private String regNumber;
    private String ownershipForm;
    private String economicActivity;
    private String inn;
    private String okpo;
    private String region;
    private String district;
    private String okmot;
    private String waterCouncil;
    private String address;
    private String bankName;
    private String nameFilialBank;
    private String regionBank;
    private String districtBank;
    private String city;
    private String addressBank;
    private String bik;
    private String numberKorespondent;
    private String currentAccountOrganization;
}
