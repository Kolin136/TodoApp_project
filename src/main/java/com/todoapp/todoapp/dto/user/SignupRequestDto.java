package com.todoapp.todoapp.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {

    @Pattern(regexp ="^[a-z0-9]$")
    @Size(min=4, max=10)
    @NotBlank
    private String username;

    // 정규식안에 { } 이용하면 해당 길이 설정 가능이라 @Size 어노테이션 필요없을듯. 테스트 해보고 안되면 변경
    @Pattern(regexp ="^[a-zA-Z0-9]{8,15}$")
//    @Size(min=8, max=15)
    @NotBlank
    private String password;
}
