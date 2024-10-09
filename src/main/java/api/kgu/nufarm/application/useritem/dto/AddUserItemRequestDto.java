package api.kgu.nufarm.application.useritem.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AddUserItemRequestDto {

    private Long id;
    private String name;
    private LocalDateTime addedDate;
}
