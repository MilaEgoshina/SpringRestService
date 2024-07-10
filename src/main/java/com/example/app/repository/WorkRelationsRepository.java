package com.example.app.repository;

import com.example.app.entity.WorkRelations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkRelationsRepository extends JpaRepository<WorkRelations,Long> {

    boolean exitsById(Long id);
}
