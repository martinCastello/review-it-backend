package ar.edu.unq.reviewitbackend.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable{

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    protected Date createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    protected Date lastModifiedDate;
}
