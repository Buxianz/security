package com.rbi.security.web.service;

import com.rbi.security.entity.config.OrganizationTree;
import com.rbi.security.entity.web.entity.SysRole;

import java.util.List;

public interface ConfigService {
    List<OrganizationTree> getOrganizationTree() throws RuntimeException;
    List<SysRole> getRole() throws RuntimeException;
}