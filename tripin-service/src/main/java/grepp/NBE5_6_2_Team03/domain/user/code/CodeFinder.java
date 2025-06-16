package grepp.NBE5_6_2_Team03.domain.user.code;

import grepp.NBE5_6_2_Team03.global.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CodeFinder {

    private final List<CodeProvidable> CODE_PROVIDERS = List.of(
            new PasswordCodeProvider()
    );

    public String findCodeFrom(CodeType codeType){
        return CODE_PROVIDERS.stream()
                .filter(provider -> provider.isSupport(codeType))
                .findFirst()
                .map(provider -> provider.provide(codeType))
                .orElseThrow(() -> new BusinessException("CODE_FINDER 예외"));
    }
}
