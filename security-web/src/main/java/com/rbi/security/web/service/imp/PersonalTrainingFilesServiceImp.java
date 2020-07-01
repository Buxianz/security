package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.safe.PersonalTrainingFiles;
import com.rbi.security.entity.web.safe.demand.SafaTrainingType;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.OrganizationDAO;
import com.rbi.security.web.DAO.safe.*;
import com.rbi.security.web.service.PersonalTrainingFilesService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: PersonalTrainingFilesServiceImp
 * @USER: "吴松达"
 * @DATE: 2020/7/1
 * @TIME: 14:43
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 01
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 14
 * @MINUTE: 43
 * @PROJECT_NAME: security
 **/
@Service
public class PersonalTrainingFilesServiceImp implements PersonalTrainingFilesService {
    private static final Logger logger = LoggerFactory.getLogger(PersonalTrainingFilesServiceImp.class);
    @Autowired
    SafeTrainingTasksDAO safeTrainingTasksDAO;
    @Autowired
    SafeTrainingPlanDAO safeTrainingPlanDAO;
    @Autowired
    SafeTraningNeedsDAO safeTraningNeedsDAO;
    @Autowired
    SafeTestQaperDAO safeTestQaperDAO;
    @Autowired
    OrganizationDAO organizationDAO;
    @Autowired
    SafaTrainingTypeDAO safaTrainingTypeDAO;
    @Override
    public PageData getPersonalTrainingFiles(int pageNo, int pageSize , int startIndex) throws RuntimeException {
       try{
           Subject subject = SecurityUtils.getSubject();
           int companyPersonnelId=((AuthenticationUserDTO)subject.getPrincipal()).getCompanyPersonnelId();
           List<PersonalTrainingFiles> personalTrainingFiles=safeTrainingTasksDAO.pagePersonalTrainingFiles(companyPersonnelId,startIndex,pageSize);
           int count =safeTrainingTasksDAO.getPersonalTrainingFilesCount(companyPersonnelId);
           for(int i=0;i<personalTrainingFiles.size();i++){
               //设置组织名称
               SysOrganization sysOrganization=organizationDAO.getOrganizationById(personalTrainingFiles.get(i).getOrganizationTrainingDepartmentId());
               personalTrainingFiles.get(i).setOrganizationTrainingDepartmentName(sysOrganization.getOrganizationName());
               //设置类型名称
               SafaTrainingType safaTrainingType= safaTrainingTypeDAO.getSafaTrainingTypeById(personalTrainingFiles.get(i).getTrainingTypeId());
               personalTrainingFiles.get(i).setTrainingTypeName(safaTrainingType.getTrainingTypeName());
               //设置考试时间
               personalTrainingFiles.get(i).setExaminationTime(safeTestQaperDAO.getStartAndEndTime(personalTrainingFiles.get(i).getTrainingPlanId()));
               //设置培训时间
               personalTrainingFiles.get(i).setTrainingime(safeTraningNeedsDAO.getStartAndEndTime(personalTrainingFiles.get(i).getId()));
           }
           int totalPage;
           if (count%pageSize==0){
               totalPage = count/pageSize;
           }else {
               totalPage = count/pageSize+1;
           }
           return new PageData(pageNo, pageSize, totalPage, count, personalTrainingFiles);
       }catch (Exception e){
           logger.error("分页获取自身培训档案信息失败，异常为{}",  e);
           throw new RuntimeException("分页获取自身培训档案信息失败");
       }
    }
}
