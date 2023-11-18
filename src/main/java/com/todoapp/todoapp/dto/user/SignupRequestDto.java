package com.todoapp.todoapp.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {

//    @Pattern(regexp ="^[a-z]+[0-9]*$",message ="아이디 허용문자에 맞게 적어주세요")
//    @Size(min=4, max=10,message ="4자 이상 10자 이하이어야 합니다")
    @NotBlank
    private String username;

    // 정규식안에 { } 이용하면 해당 길이 설정 가능이라 @Size 어노테이션 필요없을듯. 테스트 해보고 안되면 변경
//    @Pattern(regexp ="^[a-zA-Z]+[0-9]{8,15}$",message ="8자 이상 15자 이하이거나 비밀번호 허용 문자에 맞게 해주세요")
//    @Size(min=8, max=15)
    @NotBlank
    private String password;
}
