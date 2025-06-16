package grepp.NBE5_6_2_Team03.api.controller.admin.dto.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class UserSearchPageResponse {

    private List<UserInfoResponse> responses;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;

    @Builder
    private UserSearchPageResponse(List<UserInfoResponse> responses, int pageNumber,
                                   int pageSize, long totalElements, int totalPages,
                                   boolean hasNext) {
        this.responses = responses;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.hasNext = hasNext;
    }

    public static UserSearchPageResponse from(Page<UserInfoResponse> page) {
        return UserSearchPageResponse.builder()
                .responses(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNext(page.hasNext())
                .build();
    }
}
