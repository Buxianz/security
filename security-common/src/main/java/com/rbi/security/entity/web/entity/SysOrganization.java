package com.rbi.security.entity.web.entity;

import java.io.Serializable;

/**
 * (SysOrganization)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:47
 */
public class SysOrganization implements Serializable {
    private static final long serialVersionUID = 464531677728519551L;
    /**
    * 组织id
    */
    private Long id;
    /**
    * 组织名称
    */
    private String organizationName;
    /**
     * 父级组织id
     */
    private Integer parentId;
    /**
     * 组织级别
     */
    private Integer level;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

}