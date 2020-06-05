package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.hid.HidDangerDO;
import com.rbi.security.entity.web.hid.HidDangerProcessDO;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrain;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrainDTO;
import com.rbi.security.entity.web.safe.specialtype.PagingSpecialTraining;
import com.rbi.security.entity.web.safe.specialtype.SafeSpecialTrainingFiles;
import com.rbi.security.entity.web.user.PagingUser;
import com.rbi.security.exception.NonExistentException;
import com.rbi.security.exception.RepeatException;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.LocalDateUtils;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.CompanyPersonnelDAO;
import com.rbi.security.web.DAO.safe.SafeAdministratorTrainDAO;
import com.rbi.security.web.DAO.safe.SafeSpecialTrainingFilesDao;
import com.rbi.security.web.service.TrainingFileManagementService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: TrainingFileManagementServiceImp
 * @USER: "吴松达"
 * @DATE: 2020/5/25
 * @TIME: 14:53
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 25
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 14
 * @MINUTE: 53
 * @PROJECT_NAME: security
 **/
@Service
public class TrainingFileManagementServiceImp implements TrainingFileManagementService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    /**
     * 增加特种培训记录
     */
    @Autowired
    SafeAdministratorTrainDAO safeAdministratorTrainDAO;
    @Autowired
    CompanyPersonnelDAO companyPersonnelDAO;
    @Autowired
    SafeSpecialTrainingFilesDao safeSpecialTrainingFilesDao;
     public void insertSpecialTraining(SafeSpecialTrainingFiles safeSpecialTrainingFiles) throws RuntimeException{
         try {
             Integer companyPersonnelId = companyPersonnelDAO.getPersonnelByIdCardNo(safeSpecialTrainingFiles.getIdCardNo());
             if (companyPersonnelId == null) {
                 throw new NonExistentException("身份证不存在,此人不在公司人员信息表中");
             }
             Subject subject = SecurityUtils.getSubject();
             String idt = LocalDateUtils.localDateTimeFormat(LocalDateTime.now(), LocalDateUtils.FORMAT_PATTERN);
             safeSpecialTrainingFiles.setIdt(idt);
             safeSpecialTrainingFiles.setCompanyPersonnelId(companyPersonnelId);
             safeSpecialTrainingFiles.setOperatingStaff(((AuthenticationUserDTO)subject.getPrincipal()).getCompanyPersonnelId());
             if(safeSpecialTrainingFilesDao.queryByIdCardNo(safeSpecialTrainingFiles.getIdCardNo())!=null)
                 throw new RepeatException("添加特种培训记录重复");
             safeSpecialTrainingFilesDao.insert(safeSpecialTrainingFiles);
         }catch (RepeatException e) {
             logger.error("添加特种培训记录重复，用户信息为{}", safeSpecialTrainingFiles.toString());
             throw new RuntimeException(e.getMessage());
         }
         catch (NonExistentException e) {
             logger.error("添加特种培训记录失败，异常为{}", e.getMessage());
             throw new RuntimeException(e.getMessage());
         } catch (Exception e) {
             logger.error("添加特种培训记录失败，用户信息为{}，异常为{}", safeSpecialTrainingFiles.toString(), e);
             throw new RuntimeException("添加特种培训记录失败");
         }
     }
    /**
     * 删除特种培训记录
     */
   public void deleteSpecialTraining(int id) throws RuntimeException{
              try{
                  safeSpecialTrainingFilesDao.deleteById(id);
              }catch (Exception e){
                  logger.error("删除特种培训信息失败，异常为{}",e);
                  throw new RuntimeException("删除特种培训信息失败");
              }
    }
    /**
     * 更新特种培训记录
     */
   public void updateSpecialTraining(SafeSpecialTrainingFiles safeSpecialTrainingFiles) throws RuntimeException{
       try{
           safeSpecialTrainingFilesDao.update(safeSpecialTrainingFiles);
       }catch (Exception e){
           logger.error("更新特种培训信息失败，异常为{}",e);
           throw new RuntimeException("更新特种培训信息失败");
       }
    }
    /**
     * 分页查看特种培训记录
     */
    public PageData<PagingSpecialTraining> pagingSpecialTraining(int pageNo, int pageSize, int startIndex) throws RuntimeException{
        List<PagingSpecialTraining> pagingSpecialTrainingList=null;
        try{
            pagingSpecialTrainingList=safeSpecialTrainingFilesDao.queryAllByLimit(startIndex,pageSize);
            int count =safeSpecialTrainingFilesDao.getRecordCount();
            int totalPage;
            if (count%pageSize==0){
                totalPage = count/pageSize;
            }else {
                totalPage = count/pageSize+1;
            }
            return new PageData<PagingSpecialTraining>(pageNo,pageSize,totalPage,count,pagingSpecialTrainingList);
        }catch (Exception e){
            logger.error("分页获取特种培训信息失败，异常为{}",e);
            throw new RuntimeException("分页获取特种培训信息失败");
        }
    }

    @Override
    public PagingSpecialTraining getSpecialTrainingById(int id) throws RuntimeException {
        PagingSpecialTraining pagingSpecialTraining=null;
        try{
            pagingSpecialTraining= safeSpecialTrainingFilesDao.getSpecialTrainingById(id);
        }catch (Exception e){
            logger.error("根据id获取特种培训信息失败，异常为{}",e);
            throw new RuntimeException("根据id获取特种培训信息失败");
        }
        return pagingSpecialTraining;
    }


    /****************安全培训管理**谢青********************/
    @Override
    public String insertAdministratorTrain(SafeAdministratorTrain safeAdministratorTrain) throws RuntimeException {
         try{
             Subject subject = SecurityUtils.getSubject();
             AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
             Integer personnelId  =  currentUser.getCompanyPersonnelId();
             SysCompanyPersonnel sysCompanyPersonnel = safeAdministratorTrainDAO.findPersonnelByIdCardNo(safeAdministratorTrain.getIdCardNo());
             if (null == sysCompanyPersonnel){
                 return "身份证不存在,此人不在公司人员信息表中";
             }
             safeAdministratorTrain.setCompanyPersonnelId(sysCompanyPersonnel.getId());
             String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
             safeAdministratorTrain.setOperatingStaff(personnelId);
             safeAdministratorTrain.setIdt(idt);
             safeAdministratorTrainDAO.add(safeAdministratorTrain);
             return "1000";
         }catch (Exception e){
             logger.error("添加主要负责人/安全生产管理员培训台账失败，异常为{}",e);
             throw new RuntimeException("添加主要负责人/安全生产管理员培训台账失败");
         }
    }

    @Override
    public void deleteAdministratorTrain(JSONArray jsonArray) {
        for (int i = 0; i< jsonArray.size(); i++){
            JSONObject json = (JSONObject)jsonArray.get(i);
            Integer id = 0;
            safeAdministratorTrainDAO.deleteById(id);
        }
    }

    @Override
    public void updateAdministratorTrain(SafeAdministratorTrain safeAdministratorTrain) {
        String udt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        safeAdministratorTrain.setUdt(udt);
        safeAdministratorTrainDAO.update(safeAdministratorTrain);
    }

    @Override
    public PageData findAdministratorTrainByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<SafeAdministratorTrainDTO> safeAdministratorTrains = safeAdministratorTrainDAO.findByPage(pageNo2,pageSize);
        int totalPage = 0;
        int count = safeAdministratorTrainDAO.findByPageNum();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, safeAdministratorTrains);
    }
}
