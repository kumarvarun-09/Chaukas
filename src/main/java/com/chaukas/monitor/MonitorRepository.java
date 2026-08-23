package com.chaukas.monitor;

import com.chaukas.monitor.model.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonitorRepository extends JpaRepository<Monitor, Long> {
    @Override
    Optional<Monitor> findById(Long aLong);

    Optional<Monitor> findByUser(Long userId);
    Optional<?> findAllByUser(Long userId);
}
