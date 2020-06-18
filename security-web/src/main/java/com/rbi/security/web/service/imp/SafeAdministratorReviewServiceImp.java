package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.safe.administrator.ExportAdminstratorReview;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorReviewDTO;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrainDTO;
import com.rbi.security.entity.web.safe.specialtype.ExportReview;
import com.rbi.security.exception.NonExistentException;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.ExcelPOI;
import com.rbi.security.tool.LocalDateUtils;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.safe.SafeAdministratorReviewDAO;
import com.rbi.security.web.service.SafeAdministratorReviewService;
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

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: SafeAdministratorReviewServiceImp
 * @USER: "谢青"
 * @DATE: 2020/6/8
 * @TIME: 16:22
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 08
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 16
 * @MINUTE: 22
 * @PROJECT_NAME: security
 **/
@Service
public class SafeAdministratorReviewServiceImp implements SafeAdministratorReviewService {
    private static final Logger logger = LoggerFactory.getLogger(SafeAdministratorReviewServiceImp.class);
    @Autowired
    SafeAdministratorReviewDAO safeAdministratorReviewDAO;
    @Value("${uploadfile.ip}")
    private String fileIp;//此ip与此应用部署的服务区ip一致
    //导出excel文件表头
    private static List<String> titleMaps=new LinkedList<String>() {{
        this.add("序号");
        this.add("姓名");
        this.add("身份证号");
        this.add("单位");
        this.add("发证时间");
        this.add("性别");
        this.add("文化程度");
        this.add("合格证类型");
    }
    };
    //表头对应字段
    private static Map<String,String> titleMap=new HashMap<String,String>(){
        {
            this.put("no","序号");
            this.put("name","姓名");
            this.put("idCardNo","身份证号");
            this.put("unit","单位");
            this.put("dateOfIssue","发证时间");
            this.put("gender","性别");
            this.put("degreeOfEducation","文化程度");
            this.put("typeOfCertificate","合格证类型");
        }
    };
    /**
     * 导出安全负责人复审名单
     */
    public String exportAdminstratorReview(int completionStatus) throws RuntimeException{
        String idt= LocalDateUtils.localDateTimeFormat(LocalDateTime.now(), LocalDateUtils.DATE_FORMAT_PATTERN);
        /*String filepath = "F:\\";*/
        String filepath = "/usr/work/adminstratorReviewExcel/";
        String downloadPath=null;
        String sheetName = "";
        List<ExportAdminstratorReview> exportAdminstratorReviews=null;
        List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
        try{
            if(completionStatus==1){
                sheetName="未完成主要负责人复审人员名单";
            }else {
                if(completionStatus==2){
                    sheetName="被拒绝主要负责人复审人员名单";
                }else {
                    if(completionStatus==3){
                        sheetName="已完成主要负责人复审人员名单";
                    }
                }
            }
            filepath=filepath+sheetName+idt+".xlsx";
            exportAdminstratorReviews=safeAdministratorReviewDAO.getAllExportAdminstratorReviewByStatus(completionStatus);
            if(exportAdminstratorReviews.size()!=0) {
                for (int i = 0; i < exportAdminstratorReviews.size(); i++) {
                    values.add(ImportExcleFactory.objectToMap(exportAdminstratorReviews.get(i), titleMap));
                }
                ExcelPOI.writeExcel(filepath, sheetName, titleMaps, values);
                downloadPath = fileIp + filepath;
            }else {
                if(exportAdminstratorReviews.size()==0){
                    throw new NonExistentException("没有复审人员");
                }
            }
        }catch (NonExistentException e){
            logger.error(sheetName+"：不存在");
            throw new RuntimeException(e.getMessage());
        }
        catch (FileNotFoundException e){
            logger.error("导出特种人员复审名单失败：表格在占用（或找不到路径），请关闭打开的excel表格");
            throw new RuntimeException("表格在占用（或找不到路径），请关闭打开的excel表格");
        }
        catch (Exception e){
            logger.error("导出特种人员复审名单异常，异常为{}", e.getMessage());
            throw new RuntimeException("导出特种人员复审名单异常");
        }
        return downloadPath;
    }
    @Override
    public PageData findByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<SafeAdministratorReviewDTO> safeAdministratorReviewDTOS = safeAdministratorReviewDAO.findByPage(pageNo2,pageSize);
        int totalPage = 0;
        int count = safeAdministratorReviewDAO.findByPageNum();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, safeAdministratorReviewDTOS);
    }


    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void review(SafeAdministratorReviewDTO safeAdministratorReviewDTO) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        String time = DateUtil.date(DateUtil.FORMAT_PATTERN);


        safeAdministratorReviewDTO.setOperatingStaff(personnelId);
        safeAdministratorReviewDTO.setCompletionStatus(3);
        safeAdministratorReviewDTO.setProcessingTime(time);

        safeAdministratorReviewDAO.updateReview(safeAdministratorReviewDTO);
        safeAdministratorReviewDAO.updateFile(safeAdministratorReviewDTO);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void cancel(SafeAdministratorReviewDTO safeAdministratorReviewDTO) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        String time = DateUtil.date(DateUtil.FORMAT_PATTERN);

        safeAdministratorReviewDTO.setOperatingStaff(personnelId);
        safeAdministratorReviewDTO.setCompletionStatus(2);
        safeAdministratorReviewDTO.setProcessingTime(time);
        safeAdministratorReviewDAO.updateReview(safeAdministratorReviewDTO);
    }
}
