package com.rbi.security.entity.web.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * (SysUser)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:49
 */
@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = 360253428883446542L;
    /**
    * 用户id
    */
    private Integer id;
    /**
    * 账号
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
     * 加密密码的盐
     */
    private String salf;
    /**
    * 公司人员id（外键）
    */
    private Integer companyPersonnelId;
    /**
     * 角色信息
     */
    private List<SysUserRole> sysUserRoleList;
    /**
     * 操作人员（公司人员信息id）
     */
    private Integer operatingStaff;
    /**
     * 是否启用
     */
    private Integer enabled;
    /**
     * 创建时间
     */
    private String idt;

}
