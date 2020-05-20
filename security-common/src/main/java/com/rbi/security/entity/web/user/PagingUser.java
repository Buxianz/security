package com.rbi.security.entity.web.user;

import com.rbi.security.entity.web.entity.SysUserRole;
import lombok.Data;

import java.util.List;

/**
 * 用户信息分页查看
 */
@Data
public class PagingUser {
    private int id;//用户id
    private String username;//用户账号
    private int companyPersonnelId;//公司人员id
    private Integer enabled;
    /**
     * 组织id
     */
    private Integer organizationId;
    private Integer companyId; //单位ID
    private String companyName;//单位名称
    private Integer factoryId;//工厂ID
    private String factoryName;//厂名称
    private Integer workshopId;//车间ID
    private String workshopName;//车间名称
    private Integer teamId;//班组ID
    private String teamName;//班组名称
    private String name;//姓名
    private String idCardNo;//身份证号码
    /**
     * 角色信息
     */
    private List<SysUserRole> sysUserRoleList;

    private String roleName;
}
