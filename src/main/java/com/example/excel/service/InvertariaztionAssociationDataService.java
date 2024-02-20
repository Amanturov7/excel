package com.example.excel.service;

import com.example.excel.model.InvertariaztionAssociationData;
import com.example.excel.repository.InvertariaztionAssociationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvertariaztionAssociationDataService {

    private final InvertariaztionAssociationDataRepository repository;

    @Autowired
    public InvertariaztionAssociationDataService(InvertariaztionAssociationDataRepository repository) {
        this.repository = repository;
    }

    public void saveAll(List<InvertariaztionAssociationData> data) {
        repository.saveAll(data);
    }
}
