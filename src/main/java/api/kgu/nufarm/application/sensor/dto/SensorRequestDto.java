package api.kgu.nufarm.application.sensor.dto;

import api.kgu.nufarm.application.sensor.entity.Sensor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SensorRequestDto {

    private float temperature;         // 온도
    private float humidity;            // 습도
    private float illuminance;         // 조도
    private float soil_moisture;       // 토양습도
    private float co2;                 // CO2

    public static SensorRequestDto toDto(Sensor sensor) {
        return SensorRequestDto.builder()
                .temperature(sensor.getTemperature())
                .humidity(sensor.getHumidity())
                .illuminance(sensor.getIlluminance())
                .soil_moisture(sensor.getSoilMoisture())
                .co2(sensor.getCo2())
                .build();
    }
}
