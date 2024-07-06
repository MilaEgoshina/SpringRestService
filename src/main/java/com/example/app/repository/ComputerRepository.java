package com.example.app.repository;

import com.example.app.entity.Computer;
import com.example.app.entity.WorkRelations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {

    @Query("SELECT c FROM Computer c WHERE c.worker.id = :worker_id")
    List<Computer> findAllByWorkerId(Long workerId);

    @Query("DELETE FROM Computer c WHERE c.worker.id = :worker_id")
    boolean deleteByWorkerId(Long workerId);

    @Query("SELECT с FROM Computer с WHERE с.serialNumber = :serialNumber")
    Optional<Computer> findBySerialNumber(String number);

    Optional<Computer> findById(Long id);

    boolean existsById(Long id);
}
