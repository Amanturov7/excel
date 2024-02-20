
package com.example.excel.repository;
import com.example.excel.model.Outlets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface OutletsRepository extends JpaRepository<Outlets, Long> {
    }


