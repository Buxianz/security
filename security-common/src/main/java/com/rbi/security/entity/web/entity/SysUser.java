package com.rbi.security.entity.web.entity;

import java.io.Serializable;
import java.util.List;

/**
 * (SysUser)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:49
 */
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
    private int operatingStaff;
    /**
     * 是否启用
     */
    private int enabled;
    /**
     * 创建时间
     */
    private String idt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalf() {
        return salf;
    }

    public void setSalf(String salf) {
        this.salf = salf;
    }

    public Integer getCompanyPersonnelId() {
        return companyPersonnelId;
    }

    public void setCompanyPersonnelId(Integer companyPersonnelId) {
        this.companyPersonnelId = companyPersonnelId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<SysUserRole> getSysUserRoleList() {
        return sysUserRoleList;
    }

    public void setSysUserRoleList(List<SysUserRole> sysUserRoleList) {
        this.sysUserRoleList = sysUserRoleList;
    }

    public int getOperatingStaff() {
        return operatingStaff;
    }

    public void setOperatingStaff(int operatingStaff) {
        this.operatingStaff = operatingStaff;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getIdt() {
        return idt;
    }

    public void setIdt(String idt) {
        this.idt = idt;
    }
}
