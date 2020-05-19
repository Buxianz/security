package com.rbi.security.entity.config;

import com.rbi.security.entity.web.user.CompanyPersonnelBox;
import com.rbi.security.tool.AbTreeInfo;
import lombok.Data;

import java.util.List;

@Data
public class OrganizationTree extends AbTreeInfo<OrganizationTree> {
    private String organizationName;
    private List<CompanyPersonnelBox> companyPersonnelBoxList;
}
