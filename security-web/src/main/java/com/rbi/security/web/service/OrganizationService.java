package com.rbi.security.web.service;

import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.organization.PagingOrganization;
import com.rbi.security.tool.PageData;

public interface OrganizationService {
    void insertOrganization(SysOrganization sysOrganization) throws RuntimeException;
    void updateOrganization(SysOrganization sysOrganization) throws RuntimeException;
    void deleteOrganization(int id) throws RuntimeException;
    PageData<PagingOrganization> pagingOrganization(int pageNo,int pageSize ,int startIndex) throws RuntimeException;
}
