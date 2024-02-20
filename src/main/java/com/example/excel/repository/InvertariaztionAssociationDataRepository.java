package com.example.excel.repository;

import com.example.excel.model.InvertariaztionAssociationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvertariaztionAssociationDataRepository extends JpaRepository<InvertariaztionAssociationData, Long> {
    // Здесь можно добавить кастомные методы для работы с сущностями, если это необходимо
}
