package ar.edu.unq.reviewitbackend.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Pagination {

    private static final int DEFAULT_SIZE = 10;

    private Integer page;
    private Integer size;
    private String sort;
    private String order;
    
    public Pagination () {}
    
    public Pagination(Integer page, Integer size, String sort,String order) {
    	this.setOrder(order);
    	this.setPage(page);
    	this.setSize(size);
    	this.setSort(sort);
    }

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
    
    public Integer getPage() {
    	return this.page;
    }
    
    public void setPage(Integer page) {
    	this.page = page;
    }
    
    public Integer getSize() {
    	return this.page;
    }
    
    public void setSize(Integer size) {
    	this.size = size;
    }
    
    public String getSort() {
    	return this.sort;
    }
    
    public void setSort(String sort) {
    	this.sort = sort;
    }

    public String getOrder() {
    	return this.order;
    }
    
    public void setOrder(String order) {
    	this.order = order;
    }

}
