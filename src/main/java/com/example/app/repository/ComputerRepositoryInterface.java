package com.example.app.repository;

import com.example.app.entity.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComputerRepositoryInterface extends JpaRepository<Computer, Long> {

    List<Computer> findAllByWorkerId(Long workerId);

    boolean deleteByWorkerId(Long workerId);

    Optional<Computer> findBySerialNumber(String number);
}
