package api.kgu.nufarm.common.auth.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {

    private String email;
    private String password;
}
