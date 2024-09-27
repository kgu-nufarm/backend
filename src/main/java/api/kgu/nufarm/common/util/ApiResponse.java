package api.kgu.nufarm.common.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String status;
    private T data;
    private String message;

    private ApiResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    // 데이터와 함께 성공 여부 반환
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS_STATUS, data, null);
    }

    // 성공 여부만 반환
    public static ApiResponse<?> successWithNoContent() {
        return new ApiResponse<>(SUCCESS_STATUS, null, null);
    }

    // validation 실패
    public static ApiResponse<?> fail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put( error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ApiResponse<>(FAIL_STATUS, errors, null);
    }

    // 에러 반환 시
    public static ApiResponse<?> error(String message) {
        return new ApiResponse<>(ERROR_STATUS, null, message);
    }
}
