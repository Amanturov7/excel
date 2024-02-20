package com.example.excel.service;

import com.example.excel.model.Hydropost;
import com.example.excel.repository.HydropostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HydropostService {

    private final HydropostRepository repository;

    @Autowired
    public HydropostService(HydropostRepository repository) {
        this.repository = repository;
    }

    public void saveHydroposts(List<Hydropost> нydroposts) {
        repository.saveAll(нydroposts);
    }
}
