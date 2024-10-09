package api.kgu.nufarm.application.sensor.entity;

import api.kgu.nufarm.application.sensor.dto.SensorRequestDto;
import api.kgu.nufarm.application.useritem.entity.UserItem;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_item_id")
    private UserItem userItem;  // 사용자 작물

    private LocalDateTime recordedAt;  // 데이터가 기록된 시간

    private float temperature;         // 온도

    private float humidity;            // 습도

    private float lightIntensity;      // 조도

    private float gasLevel;            // 가스 농도

    public static Sensor toEntity(UserItem userItem, LocalDateTime recordedAt, SensorRequestDto dto) {
        return Sensor.builder()
                .userItem(userItem)
                .recordedAt(recordedAt)
                .temperature(dto.getTemperature())
                .humidity(dto.getHumidity())
                .lightIntensity(dto.getLightIntensity())
                .gasLevel(dto.getGasLevel())
                .build();
    }
}
