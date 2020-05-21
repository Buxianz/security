package com.rbi.security.web.service;

import com.rbi.security.entity.config.OrganizationTree;
import com.rbi.security.entity.config.SystemMenuPermisson;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.system.SystemCategory;

import java.util.List;

public interface ConfigService {
    List<OrganizationTree> getOrganizationTree() throws RuntimeException;
    List<SysRole> getRole() throws RuntimeException;
    List<OrganizationTree> getCompanyPersonnelTree() throws RuntimeException;
    List<SystemMenuPermisson> getSystemMenuPermissonTree() throws RuntimeException;
    List<SystemCategory> getSystemCategoryBox() throws RuntimeException;
}
