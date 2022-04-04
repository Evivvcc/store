package com.cy.store.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 作为实体类的基类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;
}
