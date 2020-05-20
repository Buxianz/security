package com.rbi.security.entity.web.organization;

import lombok.Data;

/**
 * 组织信息分页查看类
 */
@Data
public class PagingOrganization {
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
     * 不在表中，父级组织名称
     */
    private String parentName;
}
