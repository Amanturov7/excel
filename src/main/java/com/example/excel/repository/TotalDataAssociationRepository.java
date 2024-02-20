package com.example.excel.repository;

import com.example.excel.model.TotalDataAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalDataAssociationRepository extends JpaRepository<TotalDataAssociation, Long> {
}
