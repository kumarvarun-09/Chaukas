package com.chaukas.monitor;

import com.chaukas.monitor.model.MonitorConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MonitorConfigRepository extends JpaRepository<MonitorConfig, Long> {
    @Override
    Optional<MonitorConfig> findById(Long id);

    Optional<MonitorConfig> findByMonitorOrderByVersionDesc(Long monitorId);

    Optional<?> findAllByMonitor(Long monitorId);
}
