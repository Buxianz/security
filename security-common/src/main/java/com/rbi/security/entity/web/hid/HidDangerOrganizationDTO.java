package com.rbi.security.entity.web.hid;

import lombok.Data;

@Data
public class HidDangerOrganizationDTO {
    private Integer id;
    private String hidDangerCode;
    private Integer organizationId;
    private String organizationName;
    private Integer level;

}
