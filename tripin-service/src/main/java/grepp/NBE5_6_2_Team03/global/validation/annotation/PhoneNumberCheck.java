package grepp.NBE5_6_2_Team03.global.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@NotBlank(message = "휴대폰 번호는 빈 값을 허용 하지 않습니다.")
@Pattern(
        regexp = "^01[016789]-\\d{3,4}-\\d{4}$",
        message = "휴대폰 번호는 010-1234-5678 형식으로 입력해주세요."
)
public @interface PhoneNumberCheck {
    String message() default "잘못된 휴대폰 번호 형식 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
