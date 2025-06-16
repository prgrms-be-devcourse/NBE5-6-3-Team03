package grepp.NBE5_6_2_Team03.domain.user;

import grepp.NBE5_6_2_Team03.domain.BaseEntity;
import grepp.NBE5_6_2_Team03.domain.user.file.UploadFile;
import grepp.NBE5_6_2_Team03.global.exception.CannotUpdateException;
import grepp.NBE5_6_2_Team03.global.message.ExceptionMessage;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private boolean isLocked;

    @Embedded
    private UploadFile uploadFile;

    @Enumerated(EnumType.STRING)
    private Role role;

    protected User() {
    }

    @Builder
    private User(Long id, String email, String password,
                 String name, String phoneNumber, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public void updateProfile(String email, String name, String phoneNumber, UploadFile uploadFile) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;

        if(uploadFile != null) {
            this.uploadFile = uploadFile;
        }
    }

    public void modifyPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updateIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public void unlock() {
        if (!this.isLocked) {
            throw new CannotUpdateException(ExceptionMessage.ALREADY_UNLOCKED.getDescription());
        }
        this.isLocked = false;
    }

    public void lock() {
        if (this.isLocked) {
            throw new CannotUpdateException(ExceptionMessage.ALREADY_LOCKED.getDescription());
        }
        this.isLocked = true;
    }

    public boolean isAdmin() {
        return this.role == Role.ROLE_ADMIN;
    }

    public void modifyName(String name) {
        this.name = name;
    }

    public void modifyPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
