package com.rbi.security.entity.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (SysOrganization)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:47
 */
@Data
public class SysOrganization implements Serializable {
    private static final long serialVersionUID = 464531677728519551L;
    /**
    * 组织id
    */
    private Integer id;
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
    /**
     * 不在表中，父级组织级别
     */
    private Integer parentLevel;
    /**
     * 操作人
     */
    private Integer operatingStaff;

    private Integer security;


}