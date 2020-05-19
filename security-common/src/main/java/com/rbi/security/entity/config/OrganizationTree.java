package com.rbi.security.entity.config;

import com.rbi.security.tool.AbTreeInfo;
import lombok.Data;

@Data
public class OrganizationTree extends AbTreeInfo<OrganizationTree> {
    private String organizationName;
}
