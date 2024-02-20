
package com.example.excel.service;
import com.example.excel.model.Outlets;
import com.example.excel.repository.OutletsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OutletService {
    private final OutletsRepository repository;

    @Autowired
    public OutletService(OutletsRepository repository) {
        this.repository = repository;
    }

    public void saveOutlets(List<Outlets> outlets) {
        repository.saveAll(outlets);
    }
}
