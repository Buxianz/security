package com.rbi.security.web.service;

import com.rbi.security.entity.web.health.OccHealthProject;
import com.rbi.security.tool.PageData;

/**
 * @ClassName HealthProjectService
 * @Description TODO
 * @Author muyizg
 * @Date 2020/6/19 11:47
 * @Version 1.0
 **/

public interface HealthProjectService {

    PageData<OccHealthProject> getPageHealthPro(int pageNo,int pageSize,int startIndex) throws RuntimeException;

    void insertHealthPro(OccHealthProject occHealthProject) throws RuntimeException;

    void updateHealthPro(OccHealthProject occHealthProject) throws RuntimeException;

    void deleteHealthPro(int id) throws RuntimeException;

}
