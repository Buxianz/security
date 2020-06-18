package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.safe.specialtype.ExportReview;
import com.rbi.security.entity.web.safe.specialtype.PagingSpecialReview;
import com.rbi.security.entity.web.safe.specialtype.SafeSpecialReview;
import com.rbi.security.tool.ExcelPOI;
import com.rbi.security.tool.LocalDateUtils;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.StringUtils;
import com.rbi.security.web.DAO.safe.SafeSpecialReviewDAO;
import com.rbi.security.web.service.SpecialReviewService;
import com.rbi.security.web.service.util.ImportExcleFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: SpecialReviewServiceImp
 * @USER: "吴松达"
 * @DATE: 2020/5/27
 * @TIME: 16:47
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 27
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 16
 * @MINUTE: 47
 * @PROJECT_NAME: security
 **/
@Service
public class SpecialReviewServiceImp implements SpecialReviewService {
    private static final Logger logger = LoggerFactory.getLogger(SpecialReviewServiceImp.class);
    @Value("${uploadfile.ip}")
    private String fileIp;//此ip与此应用部署的服务区ip一致
    private static final String excelPath=null;

    //导出excel文件表头
    private static List<String> titleMaps=new LinkedList<String>() {{
        this.add("序号");
        this.add("姓名");
        this.add("性别");
        this.add("文化程度");
        this.add("工种");
        this.add("操作项目");
        this.add("本工种年限");
        this.add("操作证号");
        this.add("身份证号");
        this.add("备注");
    }
    };
    //表头对应字段
    private static Map<String,String> titleMap=new HashMap<String,String>(){
        {
            this.put("no","序号");
            this.put("name","姓名");
            this.put("gender","性别");
            this.put("degreeOfEducation","文化程度");
            this.put("typeOfWork","工种");
            this.put("operationItems","操作项目");
            this.put("yearsOfWork","本工种年限");
            this.put("operationCertificateNo","操作证号");
            this.put("idCardNo","身份证号");
            this.put("remarks","备注");
        }
    };
    @Autowired
    SafeSpecialReviewDAO safeSpecialReviewDAO;

    /**
     * 分页查看复审记录
     */
    public PageData<PagingSpecialReview> pagingSpecialReview(int pageNo, int pageSize, int startIndex) throws RuntimeException{
        List<PagingSpecialReview> pagingSpecialReviewList=null;
        try{
            /**
             * 获取特征人员复审分页记录
             */
            pagingSpecialReviewList=safeSpecialReviewDAO.pagingSpecialReview(startIndex,pageSize);
            for(int i=0;i<pagingSpecialReviewList.size();i++){
                if(StringUtils.isNotBlank(pagingSpecialReviewList.get(i).getSixReviewTime())){
                    pagingSpecialReviewList.get(i).setDateOfIssue(pagingSpecialReviewList.get(i).getSixReviewTime());
                    break;
                }
                if(StringUtils.isNotBlank(pagingSpecialReviewList.get(i).getFiveReviewTime())){
                    pagingSpecialReviewList.get(i).setDateOfIssue(pagingSpecialReviewList.get(i).getFiveReviewTime());
                    break;
                }if(StringUtils.isNotBlank(pagingSpecialReviewList.get(i).getFourReviewTime())){
                    pagingSpecialReviewList.get(i).setDateOfIssue(pagingSpecialReviewList.get(i).getFourReviewTime());
                    break;
                }if(StringUtils.isNotBlank(pagingSpecialReviewList.get(i).getThreeReviewTime())){
                    pagingSpecialReviewList.get(i).setDateOfIssue(pagingSpecialReviewList.get(i).getThreeReviewTime());
                    break;
                }if(StringUtils.isNotBlank(pagingSpecialReviewList.get(i).getTowReviewTime())){
                    pagingSpecialReviewList.get(i).setDateOfIssue(pagingSpecialReviewList.get(i).getTowReviewTime());
                    break;
                }if(StringUtils.isNotBlank(pagingSpecialReviewList.get(i).getOneReviewTime())){
                    pagingSpecialReviewList.get(i).setDateOfIssue(pagingSpecialReviewList.get(i).getOneReviewTime());
                    break;
                }
            }
            int count =safeSpecialReviewDAO.getSpecialReviewCount();
            int totalPage;
            if (count%pageSize==0){
                totalPage = count/pageSize;
            }else {
                totalPage = count/pageSize+1;
            }
          return new PageData<PagingSpecialReview>(pageNo,pageSize,totalPage,count,pagingSpecialReviewList);
        }catch (Exception e){
            logger.error("分页获取特种人员复审信息失败，异常为{}", e.getMessage());
            throw new RuntimeException("分页获取特种人员复审信息失败");
        }
    }
    /**
     * 处理复审情况
     */
    public void handleSpecialReview(SafeSpecialReview safeSpecialReview) throws RuntimeException{
         try{
             String processingTime= LocalDateUtils.localDateTimeFormat(LocalDateTime.now(), LocalDateUtils.FORMAT_PATTERN);
             Subject subject = SecurityUtils.getSubject();
             AuthenticationUserDTO user= (AuthenticationUserDTO)subject.getPrincipal();
             safeSpecialReview.setProcessingTime(processingTime);
             safeSpecialReview.setOperatingStaff(user.getCompanyPersonnelId());
             safeSpecialReviewDAO.updateSpecialReview(safeSpecialReview);
         }catch (Exception e){
             logger.error("处理特种人员复审信息失败，异常为{}", e.getMessage());
             throw new RuntimeException("处理特种人员复审信息失败");
         }
    }
    /**
     * 删除复审情况
     */
    public void deleteSpecialReview() throws RuntimeException{
        try{

        }catch (Exception e){

        }
    }
    /**
     * 修改复审情况
     */
    public void updateSpecialReview() throws RuntimeException{
        try{

        }catch (Exception e){

        }
    }
    /**
     * 导出特种人员复审人员名单
     */
    public String exportSpecialReview(int completionStatus) throws RuntimeException{
        String idt=LocalDateUtils.localDateTimeFormat(LocalDateTime.now(), LocalDateUtils.DATE_FORMAT_PATTERN);
        String filepath = "/usr/work/specialReviewExcel/";
        String downloadPath=null;
        String sheetName = "";
        List<ExportReview> exportReviewList=null;
        List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
        try{
            if(completionStatus==1){
                sheetName="未完成特种人员复审人员名单";
            }else {
                if(completionStatus==2){
                    sheetName="被拒绝特种人员复审人员名单";
                }else {
                    if(completionStatus==3){
                        sheetName="已完成特种人员复审人员名单";
                    }
                }
            }
            filepath=filepath+sheetName+idt+".xlsx";
            exportReviewList=safeSpecialReviewDAO.getAllReviewByStatus(completionStatus);
            for (int i=0;i<exportReviewList.size();i++){
                values.add(ImportExcleFactory.objectToMap(exportReviewList.get(i),titleMap));
            }
            ExcelPOI.writeExcel(filepath,sheetName,titleMaps,values);
            downloadPath=fileIp+filepath;
        }catch (FileNotFoundException e){
            logger.error("导出主要负责人复审名单失败：表格在占用（或找不到路径），请关闭打开的excel表格");
            throw new RuntimeException("表格在占用（或找不到路径），请关闭打开的excel表格");
        }
        catch (Exception e){
            logger.error("导出主要负责人复审名单异常，异常为{}", e.getMessage());
            throw new RuntimeException("导出主要负责人复审名单异常");
        }
        return downloadPath;
    }
}
