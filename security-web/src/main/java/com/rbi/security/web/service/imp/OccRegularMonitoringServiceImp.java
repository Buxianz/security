package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.health.OccDailyMonitoring;
import com.rbi.security.entity.web.health.OccRegularMonitoring;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.health.OccRegularMonitoringDAO;
import com.rbi.security.web.service.OccRegularMonitoringService;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Insert;
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
 * @NAME: OccRegularMonitoringServiceImp
 * @USER: "谢青"
 * @DATE: 2020/6/22
 * @TIME: 15:35
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 15
 * @MINUTE: 35
 * @PROJECT_NAME: security
 **/
@Service
public class OccRegularMonitoringServiceImp implements OccRegularMonitoringService {
    @Value("${uploadfile.ip}")
    private String fileIp;
    @Value("${hiddenPath}")
    private String hiddenPath;
    @Value("${path2}")
    private String path;
    @Autowired
    OccRegularMonitoringDAO occRegularMonitoringDAO;

    @Override
    public PageData findByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<OccRegularMonitoring> occRegularMonitorings = occRegularMonitoringDAO.findByPage(pageNo2,pageSize);
        for (int i=0;i<occRegularMonitorings.size();i++){
            occRegularMonitorings.get(i).setAnnex(fileIp+occRegularMonitorings.get(i).getAnnex());
        }
        int totalPage = 0;
        int count = occRegularMonitoringDAO.findByPageNum();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, occRegularMonitorings);
    }

    @Override
    public void add(OccRegularMonitoring occRegularMonitoring, MultipartFile file) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        occRegularMonitoring.setOperatingStaff(personnelId);
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        occRegularMonitoring.setIdt(idt);
        if (file!=null){
            String filename = file.getOriginalFilename();
            String timestamps = DateUtil.timeStamp();
            String newFileName = timestamps + filename;
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path+hiddenPath, newFileName));
            occRegularMonitoring.setAnnex(hiddenPath+newFileName);
        }
        occRegularMonitoringDAO.add(occRegularMonitoring);
    }

    @Override
    public void update(OccRegularMonitoring occRegularMonitoring, MultipartFile file) throws IOException {
        String udt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        occRegularMonitoring.setUdt(udt);
        if (file!=null){
            String filename = file.getOriginalFilename();
            String timestamps = DateUtil.timeStamp();
            String newFileName = timestamps + filename;
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path+hiddenPath, newFileName));
            occRegularMonitoring.setAnnex(hiddenPath+newFileName);
        }else {
            occRegularMonitoring.setAnnex(occRegularMonitoringDAO.findAnnex(occRegularMonitoring.getId()));
        }
        occRegularMonitoringDAO.update(occRegularMonitoring);
    }

    @Override
    public void delete(JSONObject json) {
        JSONArray array = json.getJSONArray("data");
        for (int i=0;i< array.size();i++){
            JSONObject result = (JSONObject) array.get(i);
            int id = result.getInteger("id");
            occRegularMonitoringDAO.delete(id);
        }
    }

    @Override
    public void deleteFile(Integer id) {
        occRegularMonitoringDAO.updateAnnex(id);
    }
}
