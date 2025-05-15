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
@NotBlank(message = "이름은 빈 값을 허용 하지 않습니다.")
@Pattern(
        regexp = "^[A-Za-z가-힣0-9]{2,10}$",
        message = "회원의 이름은 한글과 영어 문자만 가능하며 최대 10자리 입니다."
)
public @interface NameCheck {
    String message() default "잘못된 이름 형식 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
