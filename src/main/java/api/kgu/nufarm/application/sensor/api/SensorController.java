package api.kgu.nufarm.application.sensor.api;

import api.kgu.nufarm.application.sensor.dto.SensorRequestDto;
import api.kgu.nufarm.application.sensor.service.SensorService;
import api.kgu.nufarm.common.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensor")
@Tag(name = "Sensor", description = "센서")
public class SensorController {

    private final SensorService sensorService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "센서값 저장")
    @PostMapping("/addSensorResult/{id}")
    public ApiResponse<Long> addSensorResult (
            @PathVariable Long id,
            @RequestBody SensorRequestDto dto
    ) {
        Long userItemId = sensorService.addSensorResult(id, dto);
        if(userItemId == null) {
            return ApiResponse.fail("해당 id의 작물이 존재하지 않습니다.");
        }
        return ApiResponse.success(userItemId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "온도 조회", description = "하루 동안, 한 시간 간격으로 온도를 조회합니다.")
    @GetMapping("/temperature/{id}")
    public ApiResponse<List<Float>> getTemperature (
            @PathVariable Long id,
            @RequestParam LocalDate date
    ) {
        List<Float> temperatures = sensorService.getTemperature(id, date);
        return ApiResponse.success(temperatures);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "습도 조회", description = "하루 동안, 한 시간 간격으로 습도를 조회합니다.")
    @GetMapping("/humidity/{id}")
    public ApiResponse<List<Float>> getHumidity (
            @PathVariable Long id,
            @RequestParam LocalDate date
    ) {
        List<Float> humidities = sensorService.getHumidity(id, date);
        return ApiResponse.success(humidities);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "조도 조회", description = "하루 동안, 한 시간 간격으로 조도를 조회합니다.")
    @GetMapping("/lightIntensity/{id}")
    public ApiResponse<List<Float>> getLightIntensity (
            @PathVariable Long id,
            @RequestParam LocalDate date
    ) {
        List<Float> lightIntensities = sensorService.getLightIntensity(id, date);
        return ApiResponse.success(lightIntensities);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "가스 조회", description = "하루 동안, 한 시간 간격으로 가스를 조회합니다.")
    @GetMapping("/gasLevel/{id}")
    public ApiResponse<List<Float>> getGasLevel (
            @PathVariable Long id,
            @RequestParam LocalDate date
    ) {
        List<Float> gasLevels = sensorService.getGasLevel(id, date);
        return ApiResponse.success(gasLevels);
    }
}