package grepp.NBE5_6_2_Team03.domain.user.mail;

public interface CodeProvidable {

    boolean isSupport(CodeType codeType);
    String provide(CodeType codeType);
}
