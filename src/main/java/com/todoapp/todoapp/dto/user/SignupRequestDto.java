package com.todoapp.todoapp.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class SignupRequestDto {

    @Pattern(regexp ="^[a-z]+[0-9]*$",message ="아이디 허용문자에 맞게 적어주세요")
    @Size(min=4, max=10,message ="4자 이상 10자 이하이어야 합니다")
    @NotBlank
    private String username;

    // 현재 시간이 모잘라서 꼭 대문자 or 숫자 포함등은 해결 못 했습니다.
    @Pattern(regexp ="[a-zA-Z0-9]*$",message ="비밀번호 허용 문자에 맞게 해주세요")
    @Size(min=8, max=15,message ="8자 이상 15자 이하이어야 합니다")
    @NotBlank
    private String password;
}
