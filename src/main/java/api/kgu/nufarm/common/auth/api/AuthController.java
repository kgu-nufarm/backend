package api.kgu.nufarm.common.auth.api;

import api.kgu.nufarm.application.user.dto.request.UserRequestDto;
import api.kgu.nufarm.application.user.service.UserService;
import api.kgu.nufarm.common.auth.dto.JwtTokenDto;
import api.kgu.nufarm.common.auth.service.JwtTokenService;
import api.kgu.nufarm.common.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증/인가")
public class AuthController {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ApiResponse<Long> signup(
            @RequestBody UserRequestDto userRequestDto
    ) {
        Long id = userService.save(userRequestDto);
        return ApiResponse.success(id);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ApiResponse<JwtTokenDto> login(
            @RequestParam String email,
            @RequestParam String password
    ) {
        JwtTokenDto tokenDto = jwtTokenService.login(email, password);

        if(tokenDto == null) {
            return ApiResponse.fail("인증 정보가 틀립니다.");
        }
        return ApiResponse.success(tokenDto);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    public ApiResponse<String> logout(@RequestParam Long userId) {
        jwtTokenService.logout(userId);
        return ApiResponse.success("로그아웃에 성공했습니다.");
    }

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "Refresh-Token 헤더에 리프레시 토큰을 넣어 요청이 가능합니다.")
    public ApiResponse<JwtTokenDto> reissue(
            @RequestHeader("Refresh-Token") String refreshToken
    ) {
        JwtTokenDto newTokenDto = jwtTokenService.reissueToken(refreshToken);

        if (newTokenDto == null) {
            return ApiResponse.fail("틀리거나 만료된 리프레시 토큰입니다.");
        }

        return ApiResponse.success(newTokenDto);
    }
}
