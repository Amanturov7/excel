package com.example.excel.repository;
import com.example.excel.model.Hydropost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HydropostRepository extends JpaRepository<Hydropost, Long> {
}
