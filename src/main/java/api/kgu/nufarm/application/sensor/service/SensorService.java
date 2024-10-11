package api.kgu.nufarm.application.sensor.service;

import api.kgu.nufarm.application.sensor.dao.SensorRepository;
import api.kgu.nufarm.application.sensor.dto.SensorRequestDto;
import api.kgu.nufarm.application.sensor.entity.Sensor;
import api.kgu.nufarm.application.useritem.entity.UserItem;
import api.kgu.nufarm.application.useritem.service.UserItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;
    private final UserItemService userItemService;

    @Transactional
    public Long addSensorResult(Long id, SensorRequestDto dto) {
        UserItem userItem = userItemService.getUserItem(id);
        if(userItem == null) return null;
        Sensor sensor = Sensor.toEntity(userItem, LocalDateTime.now(), dto);
        sensorRepository.save(sensor);
        return sensor.getId();
    }

    public List<Float> getTemperature(Long id, LocalDate date) {
        List<Sensor> sensors = getSensorsByIdAndDate(id, date);
        if(sensors == null) return null;
        return sensors.stream()
                .map(Sensor::getTemperature)
                .toList();
    }

    public List<Float> getHumidity(Long id, LocalDate date) {
        List<Sensor> sensors = getSensorsByIdAndDate(id, date);
        if(sensors == null) return null;
        return sensors.stream()
                .map(Sensor::getHumidity)
                .toList();
    }

    public List<Float> getIlluminance(Long id, LocalDate date) {
        List<Sensor> sensors = getSensorsByIdAndDate(id, date);
        if(sensors == null) return null;
        return sensors.stream()
                .map(Sensor::getIlluminance)
                .toList();
    }

    public List<Float> getSoilMoisture(Long id, LocalDate date) {
        List<Sensor> sensors = getSensorsByIdAndDate(id, date);
        if(sensors == null) return null;
        return sensors.stream()
                .map(Sensor::getSoilMoisture)
                .toList();
    }

    public List<Float> getCo2(Long id, LocalDate date) {
        List<Sensor> sensors = getSensorsByIdAndDate(id, date);
        if(sensors == null) return null;
        return sensors.stream()
                .map(Sensor::getCo2)
                .toList();
    }

    private List<Sensor> getSensorsByIdAndDate(Long id, LocalDate date) {
        UserItem userItem = userItemService.getUserItem(id);
        if(userItem == null) return null;
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        return sensorRepository.findByUserItemIdAndRecordedAtBetween(userItem.getId(), start, end);
    }
}
