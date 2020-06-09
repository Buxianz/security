package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.role.PagingRole;
import com.rbi.security.entity.web.safe.SafeTrainingPlan;
import com.rbi.security.entity.web.safe.content.SafeDataPlan;
import com.rbi.security.entity.web.safe.demand.PagingTraniningNeeds;
import com.rbi.security.entity.web.safe.demand.SafeTrainingNeeds;
import com.rbi.security.entity.web.safe.task.SafeTrainingTasks;
import com.rbi.security.entity.web.safe.testpaper.SafeTestPaper;
import com.rbi.security.exception.NonExistentException;
import com.rbi.security.exception.RepeatException;
import com.rbi.security.tool.LocalDateUtils;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.CompanyPersonnelDAO;
import com.rbi.security.web.DAO.safe.SafeTrainingPlanDAO;
import com.rbi.security.web.DAO.safe.SafeTrainingTasksDAO;
import com.rbi.security.web.DAO.safe.SafeTraningNeedsDAO;
import com.rbi.security.web.service.SafeDemandReportService;
import com.rbi.security.web.service.TestPaperService;
import com.rbi.security.web.service.TrainingContentService;
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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
     @Autowired
    CompanyPersonnelDAO companyPersonnelDAO;
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
     * 分页查看未处理/处理了的需求
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
@Autowired
    TestPaperService testPaperService;
@Autowired
    TrainingContentService trainingContentService;
@Autowired
    SafeTrainingPlanDAO safeTrainingPlanDAO;
    /**
     * 处理需求,拒绝或者生成计划，分配任务
     */
    @Autowired
    SafeTrainingTasksDAO safeTrainingTasksDAO;
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void handlingRequirements(SafeTrainingNeeds safeTrainingNeeds, List<SafeDataPlan> safeDataPlanList, SafeTestPaper safeTestPaper) throws RuntimeException{
        try{
        String idt = LocalDateUtils.localDateTimeFormat(LocalDateTime.now(), LocalDateUtils.FORMAT_PATTERN);
        if(safeTraningNeedsDAO.getTrainingNeedsByIdAndStatus(safeTrainingNeeds)==null)
            throw new RuntimeException("此需求已经处理");
        //更新需求提报信息
            Subject subject = SecurityUtils.getSubject();
            safeTrainingNeeds.setOperatingStaff(((AuthenticationUserDTO)subject.getPrincipal()).getCompanyPersonnelId());
            safeTrainingNeeds.setProcessingTime(idt);
          safeTraningNeedsDAO.updateTrainingNeeds(safeTrainingNeeds);
          if(safeTrainingNeeds.getProcessingStatus()==2) {
            //产生（添加）计划
            SafeTrainingPlan safeTrainingPlan = new SafeTrainingPlan();
            safeTrainingPlan.setTargetSet(safeTrainingNeeds.getTargetSet());
            safeTrainingPlan.setTrainingNeedsId(safeTrainingNeeds.getId());
            safeTrainingPlan.setIdt(idt);
            safeTrainingPlanDAO.addTrainingPlan(safeTrainingPlan);
            //添加培训内容与计划关联
            for (int i = 0; i < safeDataPlanList.size(); i++) {
                safeDataPlanList.get(i).setTrainingPlanId(safeTrainingPlan.getId());
            }
            trainingContentService.add(safeDataPlanList);
            //添加试卷
            safeTestPaper.setTrainingPlanId(safeTrainingPlan.getId());
            testPaperService.insertTestQaper(safeTestPaper);
            //发布任务给目标人员
            String[] t=safeTrainingNeeds.getTargetSet().split(",");
            //培训人员目标id(公司人员信息id)
            List<Integer> targets=Arrays.stream(Arrays.stream(t).mapToInt(Integer::parseInt).toArray()).boxed().collect(Collectors.toList());
            if(companyPersonnelDAO.getCompanyPersonneCountByIds(targets)!=targets.size()){
                throw new RuntimeException("有公司人员信息被删除，不在被培训目标集合中，请刷新页面");
            }
            List<SafeTrainingTasks> safeTrainingTasksList=new LinkedList<SafeTrainingTasks>();
            for(int i=0;i<targets.size();i++){
                SafeTrainingTasks safeTrainingTasks=new SafeTrainingTasks();
                safeTrainingTasks.setCompanyPersonnelId(targets.get(i));
                safeTrainingTasks.setTrainingPlanId(safeTrainingPlan.getId());
                safeTrainingTasks.setProcessingStatus(1);
                safeTrainingTasks.setIdt(idt);
                safeTrainingTasksList.add(safeTrainingTasks);
            }
            safeTrainingTasksDAO.insertTrainingTasks(safeTrainingTasksList);
        }
        }catch (Exception e){
            logger.error("处理培训需求失败，异常为{}",e);
            throw new RuntimeException("处理培训需求失败："+e.getMessage());
        }
    }

    @Override
    public SafeTrainingNeeds getTrainingNeedsById(int id) throws RuntimeException {
        SafeTrainingNeeds safeTrainingNeeds=null;
        try{
            safeTrainingNeeds=safeTraningNeedsDAO.getTrainingNeedsById(id);
        }catch (Exception e){
            logger.error("根据id获取培训需求计划内容失败，异常为{}",e);
            throw new RuntimeException("根据id获取培训需求计划内容失败："+e.getMessage());
        }
        return safeTrainingNeeds;
    }
}
