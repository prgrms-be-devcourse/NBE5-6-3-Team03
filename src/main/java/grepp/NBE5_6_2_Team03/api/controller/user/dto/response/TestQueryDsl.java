package grepp.NBE5_6_2_Team03.api.controller.user.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class TestQueryDsl {

    private String email;
    private String name;
    private String phoneNumber;

    @QueryProjection
    public TestQueryDsl(String email, String name, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
