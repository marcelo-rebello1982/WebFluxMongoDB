package com.api.web.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Version;
import java.util.Date;

public abstract class BaseEntity {

    @CreatedBy private String createdBy;
    @CreatedDate private String createdDate;
    @LastModifiedBy private String updatedBy;
    @LastModifiedDate private Date updatedDate;
    @Version private Long version;
    private Boolean delete = Boolean.FALSE;

}