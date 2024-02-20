package com.example.excel.service;

import com.example.excel.model.TotalDataAssociation;
import com.example.excel.repository.TotalDataAssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TotalDataAssociationService {

    private final TotalDataAssociationRepository repository;

    @Autowired
    public TotalDataAssociationService(TotalDataAssociationRepository repository) {
        this.repository = repository;
    }

    public void saveTotalDataAssociations(List<TotalDataAssociation> totalDataAssociations) {
        repository.saveAll(totalDataAssociations);
    }
}