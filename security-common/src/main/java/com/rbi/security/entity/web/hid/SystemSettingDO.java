package com.rbi.security.entity.web.hid;

import lombok.Data;

@Data
public class SystemSettingDO{
    private Integer id;
    private String organizationId;
    private String settingCode;
    private String settingName;
    private String settingType;
    private Integer status;
    private String idt;
    private String udt;
}
