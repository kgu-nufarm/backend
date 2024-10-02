package api.kgu.nufarm.application.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    private String email;
    private String password;
    private String username;
}
