package com.example.excel.model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "invent_avp")
@Data
public class InvertariaztionAssociationData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String avpName;
    private String position;
    private String lastName;
    private String firstName;
    private String surname;
    private String gender;
    private String inn;
    private String phoneNumber;
    private String email;
    private String education;
    private String speciality;
    private String govEmployee;

}
