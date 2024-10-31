package com.nhnacademy.twojopingfront.user.member.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhnacademy.twojopingfront.user.member.dto.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import jakarta.validation.constraints.*;
import lombok.Setter;

/**
 * 회원 생성 요청 DTO 클래스.
 * <p>
 * 이 클래스는 회원 생성 시 필요한 데이터를 담는 데이터 전송 객체입니다.
 * 주로 회원 가입을 위해 사용되며, 각 필드는 유효성 검사를 거칩니다.
 * </p>
 * <p>
 * 주요 필드는 아이디, 비밀번호, 이름, 전화번호, 이메일, 닉네임, 성별, 생년월일이며,
 * 각 필드는 필수 입력 사항이며 특정 형식 및 길이 제한이 존재합니다.
 * </p>
 * @author Luha
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateRequestDto {

    @NotBlank(message = "로그인 아이디는 필수 입력 사항입니다.")
    @Size(min = 4, max = 20, message = "로그인 아이디는 4자 이상, 20자 이하로 입력 가능합니다.")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 소문자와 숫자로만 구성되며 4자 이상, 20자 이하여야 합니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    @Size(min = 8, max = 255, message = "비밀번호는 8자 이상, 최대 255자까지 입력 가능합니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "비밀번호는 대문자, 소문자, 숫자, 특수 문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력 사항입니다.")
    @JsonIgnore // 직렬화 시 이 필드를 제외하여 전송하지 않음
    private String confirmPassword;

    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 입력 사항입니다.")
    @Pattern(regexp = "010-\\d{3,4}-\\d{4}", message = "전화번호는 '010-0000-0000' 형식이어야 합니다.")
    private String phone;

    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    @Email(message = "유효한 이메일 주소를 입력해 주세요.")
    private String email;

    @NotBlank(message = "닉네임은 필수 입력 사항입니다.")
    @Size(min = 2, max = 20, message = "닉네임은 최소 2자 이상, 최대 20자까지 입력 가능합니다.")
    private String nickName;

    @NotNull(message = "성별은 필수 입력 사항입니다.")
    private Gender gender;

    @Past(message = "생년월일은 과거 날짜만 가능합니다.")
    private LocalDate birthday;


    @AssertTrue(message = "비밀번호와 비밀번호 확인이 일치하지 않습니다.")
    @JsonIgnore // 이 메서드는 직렬화되지 않도록 설정
    public boolean isPasswordMatching() {
        return this.password != null && this.password.equals(this.confirmPassword);
    }

}
