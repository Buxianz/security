package com.rbi.security.entity.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (SysUserRole)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:49
 */
@Data
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 597830476070006180L;
    /**
    * 角色用户关联id
    */
    private Integer id;
    /**
    * 角色id
    */
    private Integer roleId;
    /**
    * 用户id
    */
    private Integer userId;
    /**
     * 操作人员（公司人员信息id）
     */
    private Integer operatingStaff;
    /**
     * 创建时间
     */
    private String idt;
}