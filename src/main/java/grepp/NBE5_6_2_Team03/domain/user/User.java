package grepp.NBE5_6_2_Team03.domain.user;

import grepp.NBE5_6_2_Team03.domain.user.command.UserCreateCommand;
import grepp.NBE5_6_2_Team03.domain.user.command.UserEditCommand;
import grepp.NBE5_6_2_Team03.domain.user.file.UploadFile;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    @Embedded
    private UploadFile uploadFile;

    @Enumerated(EnumType.STRING)
    private Role role;

    protected User() {
    }

    @Builder
    private User(String email, String password, String name, String phoneNumber, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static User register(UserCreateCommand command, String encodedPassword) {
        return User.builder()
                .email(command.getEmail())
                .password(encodedPassword)
                .name(command.getName())
                .phoneNumber(command.getPhoneNumber())
                .role(Role.ROLE_USER)
                .build();
    }

    public void edit(UserEditCommand editCommand) {
        this.email = editCommand.getEmail();
        this.name = editCommand.getName();
        this.phoneNumber = editCommand.getPhoneNumber();
        this.uploadFile = editCommand.getUploadFile();
    }
}
