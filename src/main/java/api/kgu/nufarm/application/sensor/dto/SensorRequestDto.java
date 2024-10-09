package api.kgu.nufarm.application.sensor.dto;

import api.kgu.nufarm.application.sensor.entity.Sensor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SensorRequestDto {

    private float temperature;         // 온도
    private float humidity;            // 습도
    private float lightIntensity;      // 조도
    private float gasLevel;            // 가스

    public static SensorRequestDto toDto(Sensor sensor) {
        return SensorRequestDto.builder()
                .temperature(sensor.getTemperature())
                .humidity(sensor.getHumidity())
                .lightIntensity(sensor.getLightIntensity())
                .gasLevel(sensor.getGasLevel())
                .build();
    }
}
