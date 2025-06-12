package grepp.NBE5_6_2_Team03.api.controller.admin.dto.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
public class UserSearchRequest {
    private boolean locked;
    private int page;
    private int size;

    public UserSearchRequest(boolean locked, int page, int size) {
        this.locked = locked;
        this.page = page;
        this.size = size;
    }

    public Pageable getPageable() {
        return PageRequest.of(this.page, this.size);
    }

}
