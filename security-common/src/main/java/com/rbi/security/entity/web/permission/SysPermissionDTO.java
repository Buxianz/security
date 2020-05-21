package com.rbi.security.entity.web.permission;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.permission
 * @NAME: a
 * @USER: "林新元"
 * @DATE: 2020/5/21
 * @TIME: 17:44
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 五月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 21
 * @DAY_NAME_SHORT: 星期四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 17
 * @MINUTE: 44
 * @PROJECT_NAME: security
 **/
@Data
public class SysPermissionDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    private Integer id;
    /**
     * 权限名称
     */
    private String permissionName;
    /**
     * 操作标识码
     */
    private String operateCode;
    /**
     * 父级权限id
     */
    private Integer parentId;
    /**
     * 权限描述
     */
    private String description;
    /**
     * 所属系统id
     */
    private Integer systemId;
    /**
     * 是否启用
     */
    private Integer enabled;
    /**
     * 创建时间
     */
    private String idt;

    private String udt;

    List<?> sysPermissionDTO;
}
