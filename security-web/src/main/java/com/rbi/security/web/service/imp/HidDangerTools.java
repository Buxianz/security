package com.rbi.security.web.service.imp;

import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.web.DAO.hid.HidDangerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HidDangerTools {
    @Autowired
    HidDangerDAO hidDangerDAO;

    public  SysCompanyPersonnel findPersonnelByUserId(Integer userId){
        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelByUserId(userId);
        return sysCompanyPersonnel;
    }

    public SysRole findRoleByUserId(Integer userId){
        SysRole sysRole = hidDangerDAO.findRoleByUserId(userId);
        return sysRole;
    }

}
