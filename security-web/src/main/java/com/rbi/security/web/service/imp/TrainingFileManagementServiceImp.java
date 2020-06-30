package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.ImportFeedback;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.importlog.logAdministratorTrain;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrain;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrainDTO;
import com.rbi.security.entity.web.safe.specialtype.PagingSpecialTraining;
import com.rbi.security.entity.web.safe.specialtype.SafeSpecialTrainingFiles;
import com.rbi.security.exception.NonExistentException;
import com.rbi.security.exception.RepeatException;
import com.rbi.security.tool.*;
import com.rbi.security.web.DAO.CompanyPersonnelDAO;
import com.rbi.security.web.DAO.safe.SafeAdministratorTrainDAO;
import com.rbi.security.web.DAO.safe.SafeSpecialTrainingFilesDao;
import com.rbi.security.web.service.TrainingFileManagementService;
import com.rbi.security.web.service.util.ImportExcleFactory;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

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
    @Value("${uploadfile.ip}")
    private String fileIp;
    @Value("${excelWritePath}")
    private String excelWritePath;
    private static final String specialColumns[] = {"no", "name", "gender", "idCardNo", "degreeOfEducation", "typeOfWork", "operationItems", "yearsOfWork", "workingYears", "theoreticalAchievements", "actualResults",
            "operationCertificateNo", "dateOfIssue", "oneReviewResults", "oneReviewTime", "towReviewResults", "towReviewTime",
            "threeReviewResults", "threeReviewTime", "fourReviewResults", "fourReviewTime", "fiveReviewResults", "fiveReviewTime", "sixReviewResults", "sixReviewTime",
            "remarks"};
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    @Autowired
    SafeAdministratorTrainDAO safeAdministratorTrainDAO;
    @Autowired
    CompanyPersonnelDAO companyPersonnelDAO;
    @Autowired
    SafeSpecialTrainingFilesDao safeSpecialTrainingFilesDao;
    /**特种人员文件导入**/
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void importSpecialTrainings(MultipartFile multipartFiles) throws RuntimeException{
          try{
              int lastIndexOf = multipartFiles.getOriginalFilename().lastIndexOf(".");
              //获取文件的后缀名 .xls
              System.out.println(multipartFiles.getOriginalFilename());
              String suffix = multipartFiles.getOriginalFilename().substring(lastIndexOf);
              if(suffix.equals(".xls") || suffix.equals(".xlsx")) {
                  List<SafeSpecialTrainingFiles> safes = new LinkedList<SafeSpecialTrainingFiles>();
                  ;
                  safes = ImportExcleFactory.getDate(multipartFiles, safes, SafeSpecialTrainingFiles.class, specialColumns, 5, 0);
                  Subject subject = SecurityUtils.getSubject();
                  String idt = LocalDateUtils.localDateTimeFormat(LocalDateTime.now(), LocalDateUtils.FORMAT_PATTERN);
                  /**
                   * 进行数据筛选，批量添加
                   */
                  for (int i = 0; i < safes.size(); ) {
                      Integer companyPersonnelId = companyPersonnelDAO.getPersonnelByIdCardNo(safes.get(i).getIdCardNo());
                      if (companyPersonnelId == null) {
                          //公司人员信息不存在
                          //删除此节点，放入导入记录
                          safes.remove(i);
                          continue;
                      }
                      if (safeSpecialTrainingFilesDao.queryByIdCardNo(safes.get(i).getIdCardNo()) != null) {
                          //导入数据重复
                          //删除此节点，放入导入记录
                          safes.remove(i);
                          continue;
                      }
                      safes.get(i).setIdt(idt);
                      safes.get(i).setCompanyPersonnelId(companyPersonnelId);
                      safes.get(i).setOperatingStaff(((AuthenticationUserDTO) subject.getPrincipal()).getCompanyPersonnelId());
                      safes.get(i).setValidityPeriod(3);
                      i++;
                  }
                  if (safes.size() != 0) {
                      safeSpecialTrainingFilesDao.inserts(safes);
                  }
              }else {
                  throw new RuntimeException("文件不是excel文件");
              }
          }catch (Exception e){
              logger.error("批量导入数据失败，异常为{}", e.getMessage());
              throw new RuntimeException(e.getMessage());
          }
    }


    /**
     * 增加特种培训记录
     */
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
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
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
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
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
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

    @Override
    public PagingSpecialTraining findCertificate() {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        int personalId = currentUser.getCompanyPersonnelId();
        String idCardNo = safeSpecialTrainingFilesDao.findIdCardNo(personalId);
        PagingSpecialTraining pagingSpecialTraining = safeSpecialTrainingFilesDao.findAllByIdCardNo(idCardNo);
        return pagingSpecialTraining;
    }
    /****************安全培训管理**谢青********************/
    /**
     * 文件导入安全培训  吴松达
     * @param multipartFiles gender
     * @throws RuntimeException
     */
    private static final String administratorColumns[] = {"no", "name", "idCardNo", "unit", "dateOfIssue", "termOfValidity", "gender", "degreeOfEducation", "typeOfCertificate",
            "oneTrainingTime", "twoTrainingTime", "threeTrainingTime", "remarks"};
    @Override
    public void importAdministratorTrains(MultipartFile multipartFiles) throws RuntimeException {
           try{
               ImportFeedback importFeedback=new ImportFeedback();
               List<SafeAdministratorTrain> safeAdministratorTrainDTOList=null;
               safeAdministratorTrainDTOList =(List<SafeAdministratorTrain>) ImportExcleFactory.getDate(multipartFiles, safeAdministratorTrainDTOList, SafeAdministratorTrain.class, administratorColumns, 1, 0);
               Subject subject = SecurityUtils.getSubject();
               AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
               String idt = LocalDateUtils.localDateTimeFormat(LocalDateTime.now(), LocalDateUtils.FORMAT_PATTERN);
               List<String> idCardNos=new LinkedList<>();
               for(int i=0;i<safeAdministratorTrainDTOList.size();){
                   if(!StringUtils.isNotBlank(safeAdministratorTrainDTOList.get(i).getIdCardNo())){
                       //说明第i+1个人员信息不完整
                       // 1.存入日志文件

                       //2.记录失败数
                       importFeedback.failSizeIncrease(1);
                       // 3.删除
                       safeAdministratorTrainDTOList.remove(i);
                       continue;
                   }
                   idCardNos.add(safeAdministratorTrainDTOList.get(i).getIdCardNo());
                   safeAdministratorTrainDTOList.get(i).setOperatingStaff(currentUser.getCompanyPersonnelId());
                   safeAdministratorTrainDTOList.get(i).setIdt(idt);
                   i++;
               }
               List<SysCompanyPersonnel> sysCompanyPersonnels=null;
               if(idCardNos.size()!=0) {
                   //根据身份证集合获取公司人员信息
                   sysCompanyPersonnels = companyPersonnelDAO.getCompanyPersonnelByIdCardNos(idCardNos);
                   //根据身份证集合获取主要安全负责人信息

               }
               for (int j=0;j<safeAdministratorTrainDTOList.size();){
                   int i=0;
                   for (;i<sysCompanyPersonnels.size();i++){
                       if(sysCompanyPersonnels.get(i).getIdCardNo().equals(safeAdministratorTrainDTOList.get(j).getIdCardNo())){
                           safeAdministratorTrainDTOList.get(j).setCompanyPersonnelId(sysCompanyPersonnels.get(i).getId());
                           sysCompanyPersonnels.remove(i);
                           break;
                       }
                   }
                   if(i==sysCompanyPersonnels.size()){
                       //说明第j+1个人员信息不在公司人员信息中
                       // 1.存入日志文件

                       //2.记录失败数
                       importFeedback.failSizeIncrease(1);
                       // 3.删除
                       safeAdministratorTrainDTOList.remove(j);
                   }else {
                       j++;
                   }
               }
               if(safeAdministratorTrainDTOList.size()!=0)
                   safeAdministratorTrainDAO.adds(safeAdministratorTrainDTOList);
               importFeedback.successSizeIncrease(safeAdministratorTrainDTOList.size());
               System.out.println("导入成功");
           }catch (Exception e){
               logger.error("批量导入数据失败，异常为{}", e.getMessage());
               throw new RuntimeException(e.getMessage());
           }
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String insertAdministratorTrain(SafeAdministratorTrain safeAdministratorTrain) throws RuntimeException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        SysCompanyPersonnel sysCompanyPersonnel = safeAdministratorTrainDAO.findPersonnelByIdCardNo(safeAdministratorTrain.getIdCardNo());
        if (null == sysCompanyPersonnel){
            return "身份证不存在,此人不在公司人员信息表中";
        }
        int num = safeAdministratorTrainDAO.findIdCardNoNum(safeAdministratorTrain.getIdCardNo());
        if (num >= 1){
            return "该员工信息以添加";
        }
        if (StringUtils.isNotBlank(safeAdministratorTrain.getOneTrainingTime())) {
            try {
                String time2 = safeAdministratorTrain.getOneTrainingTime().substring(0, safeAdministratorTrain.getOneTrainingTime().indexOf("至"));
                DateUtil.StringToDate(time2);
            } catch (Exception e) {
                return "时间格式错误";
            }
        }
        if (StringUtils.isNotBlank(safeAdministratorTrain.getTwoTrainingTime())) {
            try {
                String time2 = safeAdministratorTrain.getTwoTrainingTime().substring(0, safeAdministratorTrain.getTwoTrainingTime().indexOf("至"));
                DateUtil.StringToDate(time2);
            } catch (Exception e) {
                return "时间格式错误";
            }
        }
        if (StringUtils.isNotBlank(safeAdministratorTrain.getThreeTrainingTime())) {
            try {
                String time2 = safeAdministratorTrain.getThreeTrainingTime().substring(0, safeAdministratorTrain.getThreeTrainingTime().indexOf("至"));
                DateUtil.StringToDate(time2);
            } catch (Exception e) {
                return "时间格式错误";
            }
        }
        safeAdministratorTrain.setCompanyPersonnelId(sysCompanyPersonnel.getId());
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        safeAdministratorTrain.setOperatingStaff(personnelId);
        safeAdministratorTrain.setIdt(idt);
        safeAdministratorTrainDAO.add(safeAdministratorTrain);
        return "1000";
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteAdministratorTrain(Integer id) {
            safeAdministratorTrainDAO.deleteById(id);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String updateAdministratorTrain(SafeAdministratorTrain safeAdministratorTrain) {
        if (StringUtils.isNotBlank(safeAdministratorTrain.getOneTrainingTime())) {
            try {
                String time2 = safeAdministratorTrain.getOneTrainingTime().substring(0, safeAdministratorTrain.getOneTrainingTime().indexOf("至"));
                DateUtil.StringToDate(time2);
            } catch (Exception e) {
                return "时间格式错误";
            }
        }
        if (StringUtils.isNotBlank(safeAdministratorTrain.getTwoTrainingTime())) {
            try {
                String time2 = safeAdministratorTrain.getTwoTrainingTime().substring(0, safeAdministratorTrain.getTwoTrainingTime().indexOf("至"));
                DateUtil.StringToDate(time2);
            } catch (Exception e) {
                return "时间格式错误";
            }
        }
        if (StringUtils.isNotBlank(safeAdministratorTrain.getThreeTrainingTime())) {
            try {
                String time2 = safeAdministratorTrain.getThreeTrainingTime().substring(0, safeAdministratorTrain.getThreeTrainingTime().indexOf("至"));
                DateUtil.StringToDate(time2);
            } catch (Exception e) {
                return "时间格式错误";
            }
        }
        String udt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        safeAdministratorTrain.setUdt(udt);
        safeAdministratorTrainDAO.update(safeAdministratorTrain);
        return "1000";
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

    @Override
    public PageData findByCondition(String condition, String value, int pageNo, int pageSize) {
        String value2 = "'%"+value+"%'";
        if (condition.equals("姓名")){
            int pageNo2 = pageSize * (pageNo - 1);
            List<SafeAdministratorTrainDTO> safeAdministratorTrains = safeAdministratorTrainDAO.findByName(value2,pageNo2,pageSize);
            int totalPage = 0;
            int count = safeAdministratorTrainDAO.findByNameNum(value2);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, safeAdministratorTrains);
        }else if (condition.equals("身份证号")){
            int pageNo2 = pageSize * (pageNo - 1);
            List<SafeAdministratorTrainDTO> safeAdministratorTrains = safeAdministratorTrainDAO.findByidCardNo(value2,pageNo2,pageSize);
            int totalPage = 0;
            int count = safeAdministratorTrainDAO.findByidCardNoNum(value2);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, safeAdministratorTrains);
        }else {
            int pageNo2 = pageSize * (pageNo - 1);
            List<SafeAdministratorTrainDTO> safeAdministratorTrains = safeAdministratorTrainDAO.findByUnit(value2,pageNo2,pageSize);
            int totalPage = 0;
            int count = safeAdministratorTrainDAO.findByUnitNum(value2);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, safeAdministratorTrains);
        }
    }

    @Override
    public Map<String, Object> excelImport(MultipartFile file) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        List<logAdministratorTrain> log = new ArrayList<>();
        List<String> list = ExcelRead.readExcel(file, 9);
        int importCount = 0;
        String idCardNo2 = null;
        for (int i=0;i<list.size();i++){
            try {
                List<String> list1 = Arrays.asList(StringUtils.split(list.get(i), ","));
                String idCardNo = list1.get(0);
                String unit = list1.get(1);
                String dateOfIssue = list1.get(2);
                String termOfValidity = list1.get(3);
                String typeOfCertificate = list1.get(4);
                String oneTrainingTime = list1.get(5);
                String twoTrainingTime = list1.get(6);
                String threeTrainingTime = list1.get(7);
                String remarks = list1.get(8);
                logAdministratorTrain logAdministratorTrain = new logAdministratorTrain();
                if (idCardNo.equals("blank") || unit.equals("") || dateOfIssue.equals("blank") || termOfValidity.equals("blank") ||
                        typeOfCertificate.equals("blank")) {
                    logAdministratorTrain.setCode(importCount + 2);
                    logAdministratorTrain.setIdNum(idCardNo);
                    logAdministratorTrain.setResult("失败");
                    logAdministratorTrain.setReason("必填字段不能为空");
                    logAdministratorTrain.setIdt(idt);
                    safeAdministratorTrainDAO.addLogAdministratorTrain(logAdministratorTrain);
                    log.add(logAdministratorTrain);
                    continue;
                }
                idCardNo2 = idCardNo;
                int num1 = safeAdministratorTrainDAO.findPersonnelByIdCardNum(idCardNo);
                if (num1 == 0) {
                    logAdministratorTrain.setCode(importCount + 2);
                    logAdministratorTrain.setIdNum(idCardNo);
                    logAdministratorTrain.setResult("失败");
                    logAdministratorTrain.setReason("系统没有此身份证号对应的人员信息");
                    logAdministratorTrain.setIdt(idt);
                    safeAdministratorTrainDAO.addLogAdministratorTrain(logAdministratorTrain);
                    log.add(logAdministratorTrain);
                    continue;
                }
                if (oneTrainingTime.equals("blank")) {
                    oneTrainingTime = null;
                } else {
                    try {
                        String time2 = oneTrainingTime.substring(0, oneTrainingTime.indexOf("至"));
                        DateUtil.StringToDate(time2);
                    } catch (Exception e) {
                        System.out.println("时间格式错误");
                        continue;
                    }
                }

                if (twoTrainingTime.equals("blank")) {
                    twoTrainingTime = null;
                } else {
                    try {
                        String time2 = twoTrainingTime.substring(0, twoTrainingTime.indexOf("至"));
                        DateUtil.StringToDate(time2);
                    } catch (Exception e) {
                        System.out.println("时间格式错误");
                        continue;
                    }
                }
                if (threeTrainingTime.equals("blank")) {
                    threeTrainingTime = null;
                } else {
                    try {
                        String time2 = threeTrainingTime.substring(0, threeTrainingTime.indexOf("至"));
                        DateUtil.StringToDate(time2);
                    } catch (Exception e) {
                        System.out.println("时间格式错误");
                        continue;
                    }
                }
                if (remarks.equals("blank")) {
                    remarks = null;
                }
                SafeAdministratorTrain safeAdministratorTrain = new SafeAdministratorTrain();
                safeAdministratorTrain.setIdCardNo(idCardNo);
                safeAdministratorTrain.setUnit(unit);
                safeAdministratorTrain.setDateOfIssue(dateOfIssue);
                safeAdministratorTrain.setTermOfValidity(termOfValidity);
                safeAdministratorTrain.setTypeOfCertificate(typeOfCertificate);
                safeAdministratorTrain.setOneTrainingTime(oneTrainingTime);
                safeAdministratorTrain.setTwoTrainingTime(twoTrainingTime);
                safeAdministratorTrain.setThreeTrainingTime(threeTrainingTime);
                safeAdministratorTrain.setRemarks(remarks);
                safeAdministratorTrain.setOperatingStaff(personnelId);
                safeAdministratorTrain.setIdt(idt);
                SysCompanyPersonnel sysCompanyPersonnel = safeAdministratorTrainDAO.findPersonnelByIdCardNo(safeAdministratorTrain.getIdCardNo());
                safeAdministratorTrain.setCompanyPersonnelId(sysCompanyPersonnel.getId());

                int num2 = safeAdministratorTrainDAO.findIdCardNoNum(idCardNo);
                if (num2 >= 1) {
                    safeAdministratorTrainDAO.updateByIdNum(safeAdministratorTrain);
                    logAdministratorTrain.setCode(importCount + 2);
                    logAdministratorTrain.setIdNum(idCardNo);
                    logAdministratorTrain.setResult("更新成功");
                    logAdministratorTrain.setIdt(idt);
                    safeAdministratorTrainDAO.addLogAdministratorTrain(logAdministratorTrain);
                    log.add(logAdministratorTrain);
                } else {
                    safeAdministratorTrainDAO.add(safeAdministratorTrain);
                    logAdministratorTrain.setCode(importCount + 2);
                    logAdministratorTrain.setIdNum(idCardNo);
                    logAdministratorTrain.setResult("添加成功");
                    logAdministratorTrain.setIdt(idt);
                    safeAdministratorTrainDAO.addLogAdministratorTrain(logAdministratorTrain);
                    log.add(logAdministratorTrain);
                }
                importCount = importCount +1;
            }catch (Exception e){
                System.out.println("异常"+e);
                logAdministratorTrain logAdministratorTrain = new logAdministratorTrain();
                logAdministratorTrain.setCode(importCount + 2);
                logAdministratorTrain.setIdNum(idCardNo2);
                logAdministratorTrain.setResult("失败");
                logAdministratorTrain.setReason("处理异常");
                logAdministratorTrain.setIdt(idt);
                safeAdministratorTrainDAO.addLogAdministratorTrain(logAdministratorTrain);
                log.add(logAdministratorTrain);
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("totalNumber",list.size()); //导入数据条数
        map.put("realNumber",importCount); //导入成功条数
        map.put("log",log);//日志列表
        return map;
    }

    @Override
    public Map<String, Object> writeAdmin() throws IOException {
        try {
            String filepath = excelWritePath + "主要负责人、安全生产管理人员培训台账.xlsx";
            String sheetName = "sheet1";
            String findPath = fileIp + filepath;
            List<String> titles = new ArrayList<>();
            titles.add("姓名");
            titles.add("身份证号");
            titles.add("单位");
            titles.add("发证时间");
            titles.add("有效期");
            titles.add("性别");
            titles.add("文化程度");
            titles.add("合格证类型");
            titles.add("培训时间1");
            titles.add("培训时间2");
            titles.add("培训时间3");
            titles.add("备注");
            List<Map<String, Object>> values = new ArrayList<>();
            List<SafeAdministratorTrainDTO> safeAdministratorTrainDTOS = safeAdministratorTrainDAO.findAll();
            for (int i = 0; i < safeAdministratorTrainDTOS.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("姓名", safeAdministratorTrainDTOS.get(i).getName());
                map.put("身份证号", safeAdministratorTrainDTOS.get(i).getIdCardNo());
                map.put("单位", safeAdministratorTrainDTOS.get(i).getUnit());
                map.put("发证时间", safeAdministratorTrainDTOS.get(i).getDateOfIssue());
                map.put("有效期", safeAdministratorTrainDTOS.get(i).getTermOfValidity());
                map.put("性别", safeAdministratorTrainDTOS.get(i).getGender());
                map.put("文化程度", safeAdministratorTrainDTOS.get(i).getDegreeOfEducation());
                map.put("合格证类型", safeAdministratorTrainDTOS.get(i).getTypeOfCertificate());
                map.put("培训时间1", safeAdministratorTrainDTOS.get(i).getOneTrainingTime());
                map.put("培训时间2", safeAdministratorTrainDTOS.get(i).getTwoTrainingTime());
                map.put("培训时间3", safeAdministratorTrainDTOS.get(i).getThreeTrainingTime());
                map.put("备注", safeAdministratorTrainDTOS.get(i).getRemarks());
                values.add(map);
            }
            ExcelWrite.writeExcel(filepath, sheetName, titles, values);
            Map<String, Object> map = new HashMap<>();
            map.put("path", findPath);
            return map;
        } catch (Exception e) {
            System.out.println("错误：" + e);
            return null;
        }

    }

}
