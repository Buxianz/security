package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.health.OccStatusEvaluation;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.health.OccStatusEvaluationDAO;
import com.rbi.security.web.service.OccStatusEvaluationService;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: OccStatusEvaluationServiceImp
 * @USER: "谢青"
 * @DATE: 2020/6/22
 * @TIME: 17:06
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 17
 * @MINUTE: 06
 * @PROJECT_NAME: security
 **/
@Service
public class OccStatusEvaluationServiceImp implements OccStatusEvaluationService {
    @Value("${uploadfile.ip}")
    private String fileIp;
    @Value("${hiddenPath}")
    private String hiddenPath;
    @Value("${path2}")
    private String path;
    @Autowired
    OccStatusEvaluationDAO occStatusEvaluationDAO;

    @Override
    public PageData findByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<OccStatusEvaluation> occStatusEvaluations = occStatusEvaluationDAO.findByPage(pageNo2,pageSize);
        for (int i=0;i<occStatusEvaluations.size();i++){
            occStatusEvaluations.get(i).setAnnex(fileIp+occStatusEvaluations.get(i).getAnnex());
        }
        int totalPage = 0;
        int count = occStatusEvaluationDAO.findByPageNum();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, occStatusEvaluations);
    }

    @Override
    public void add(OccStatusEvaluation occStatusEvaluation, MultipartFile file) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        occStatusEvaluation.setOperatingStaff(personnelId);
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        occStatusEvaluation.setIdt(idt);
        if (file!=null){
            String filename = file.getOriginalFilename();
            String timestamps = DateUtil.timeStamp();
            String newFileName = timestamps + filename;
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path+hiddenPath, newFileName));
            occStatusEvaluation.setAnnex(hiddenPath+newFileName);
        }
        occStatusEvaluationDAO.add(occStatusEvaluation);
    }

    @Override
    public void update(OccStatusEvaluation occStatusEvaluation, MultipartFile file) throws IOException {
        String udt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        occStatusEvaluation.setUdt(udt);
        if (file!=null){
            String filename = file.getOriginalFilename();
            String timestamps = DateUtil.timeStamp();
            String newFileName = timestamps + filename;
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path+hiddenPath, newFileName));
            occStatusEvaluation.setAnnex(hiddenPath+newFileName);
        }else {
            occStatusEvaluation.setAnnex(occStatusEvaluationDAO.findAnnex(occStatusEvaluation.getId()));
        }
        occStatusEvaluationDAO.update(occStatusEvaluation);
    }

    @Override
    public void delete(JSONObject json) {
        JSONArray array = json.getJSONArray("data");
        for (int i=0;i< array.size();i++){
            JSONObject result = (JSONObject) array.get(i);
            int id = result.getInteger("id");
            occStatusEvaluationDAO.delete(id);
        }
    }

    @Override
    public void deleteFile(Integer id) {
        occStatusEvaluationDAO.updateAnnex(id);
    }
}
