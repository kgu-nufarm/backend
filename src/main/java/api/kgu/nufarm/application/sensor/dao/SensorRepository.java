package api.kgu.nufarm.application.sensor.dao;

import api.kgu.nufarm.application.sensor.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    List<Sensor> findByUserItemIdAndRecordedAtBetween(Long userItemId, LocalDateTime start, LocalDateTime end);
}
