package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.role.PagingRole;
import com.rbi.security.entity.web.safe.demand.PagingTraniningNeeds;
import com.rbi.security.entity.web.safe.demand.SafeTrainingNeeds;
import com.rbi.security.exception.NonExistentException;
import com.rbi.security.exception.RepeatException;
import com.rbi.security.tool.LocalDateUtils;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.safe.SafeTraningNeedsDAO;
import com.rbi.security.web.service.SafeDemandReportService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: SafeDemandReportServiceImp（需求提报关联模块）
 * @USER: "吴松达"
 * @DATE: 2020/5/31
 * @TIME: 22:15
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 31
 * @DAY_NAME_SHORT: 周日
 * @DAY_NAME_FULL: 星期日
 * @HOUR: 22
 * @MINUTE: 15
 * @PROJECT_NAME: security
 **/
@Service
public class SafeDemandReportServiceImp implements SafeDemandReportService {
     @Autowired
     SafeTraningNeedsDAO safeTraningNeedsDAO;
     private static final Logger logger = LoggerFactory.getLogger(SafeDemandReportServiceImp.class);
     /*
    添加需求
     */
     @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
     public void insertSafeDemandReport(SafeTrainingNeeds safeTrainingNeeds) throws RuntimeException{
         String idt = LocalDateUtils.localDateTimeFormat(LocalDateTime.now(), LocalDateUtils.FORMAT_PATTERN);
          try{
              Subject subject = SecurityUtils.getSubject();
              safeTrainingNeeds.setIdt(idt);
              safeTrainingNeeds.setProposedTime(idt);
              safeTrainingNeeds.setReportPerson(((AuthenticationUserDTO)subject.getPrincipal()).getCompanyPersonnelId());
               safeTraningNeedsDAO.insertSafeTraningNeeds(safeTrainingNeeds);
          } catch (Exception e) {
               logger.error("添加需求提报失败，用户信息为{}，异常为{}", safeTrainingNeeds.toString(), e);
               throw new RuntimeException("添加需求提报失败");
          }
     }
    /**
     * 分页查看未处理需求
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public PageData<PagingTraniningNeeds> pagingSafeDemandReport(int pageNo, int pageSize, int startIndex,int processingStatus) throws RuntimeException{
        List<PagingTraniningNeeds> pagingTraniningNeedsList=null;
        try{
            int count =0;
            if(processingStatus==1) {
                pagingTraniningNeedsList = safeTraningNeedsDAO.pagingUnprocessedSafeTraningNeeds(startIndex, pageSize, processingStatus);
                count =safeTraningNeedsDAO.getUnprocessedTrainingNeeds();
            }else{
                pagingTraniningNeedsList= safeTraningNeedsDAO.pagingProcessedSafeTraningNeeds(startIndex, pageSize);
                count =safeTraningNeedsDAO.getProcessedTrainingNeeds();
            }

             int totalPage;
             if (count%pageSize==0){
                 totalPage = count/pageSize;
             }else {
                 totalPage = count/pageSize+1;
             }
             return new  PageData<PagingTraniningNeeds>(pageNo,pageSize,totalPage,count,pagingTraniningNeedsList);
         } catch (Exception e) {
              logger.error("分页获取需求提报信息失败，异常为{}", e);
              throw new RuntimeException("分页获取需求提报信息失败");
         }
    }
     /**
      * 分页查看自己提报或处理的需求
      */

    /**
     * 处理需求,拒绝或者生成计划，分配任务
     */
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void handlingRequirements(SafeTrainingNeeds safeTrainingNeeds) throws RuntimeException{

    }
}
