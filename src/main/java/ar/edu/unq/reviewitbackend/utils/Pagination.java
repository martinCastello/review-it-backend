package ar.edu.unq.reviewitbackend.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class Pagination {

    private static final int DEFAULT_SIZE = 10;

    private Integer page;
    private Integer size;
    private String sort;
    private String order;

    public static PageRequest buildPageRequest(Pagination pagination) {
        pagination.page = pagination.page == null || pagination.page < 0 ? 0 : pagination.page;
        pagination.size = pagination.size == null ? DEFAULT_SIZE : pagination.size;
        if (pagination.sort != null && !pagination.sort.isEmpty()) {
            if ((pagination.order != null && !pagination.order.isEmpty()))
                if (pagination.order.equals("desc"))
                    return PageRequest.of(pagination.page, pagination.size, Sort.by(pagination.sort).descending());
            return PageRequest.of(pagination.page, pagination.size, Sort.by(pagination.sort).ascending());
        }
        return PageRequest.of(pagination.page, pagination.size);
    }

}
