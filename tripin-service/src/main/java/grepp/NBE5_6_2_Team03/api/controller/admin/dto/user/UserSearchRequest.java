package grepp.NBE5_6_2_Team03.api.controller.admin.dto.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
public class UserSearchRequest {
    private String keyword;
    private Boolean isLocked;
    private int page;
    private int size;

    public UserSearchRequest() {}

    @Builder
    private UserSearchRequest(String keyword, Boolean isLocked, int page, int size) {
        this.keyword = keyword;
        this.isLocked = isLocked;
        this.page = page;
        this.size = size;
    }

    public Pageable getPageable() {
        return PageRequest.of(this.page, this.size);
    }

}