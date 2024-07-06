package com.example.app.repository;

import com.example.app.entity.Computer;
import com.example.app.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker,Long> {

    Optional<Worker> findById(Long id);

    boolean exitsById(Long id);
}
