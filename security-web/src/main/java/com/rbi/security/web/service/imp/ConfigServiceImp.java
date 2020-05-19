package com.rbi.security.web.service.imp;

import com.rbi.security.entity.config.OrganizationTree;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.tool.TreeDTO;
import com.rbi.security.web.DAO.OrganizationDAO;
import com.rbi.security.web.service.ConfigService;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ConfigServiceImp implements ConfigService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImp.class);
@Autowired
    OrganizationDAO organizationDAO;
    @Override
    public List<OrganizationTree> getOrganizationTree() throws RuntimeException {
        List<OrganizationTree> organizationTreeList=null;
        try {
            organizationTreeList= organizationDAO.getAllOrganization();
            organizationTreeList= TreeDTO.listToTree(organizationTreeList);
            return organizationTreeList;
        }catch (Exception e){
            logger.error("获取组织树信息失败，异常为{}",e);
            throw new RuntimeException("获取组织树信息失败");
        }
    }

    @Override
    public List<SysRole> getRole() throws RuntimeException {
        return null;
    }
}
