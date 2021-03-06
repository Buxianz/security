package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.hid.HidDangerDO;
import com.rbi.security.entity.web.safe.content.SafeContentCategory;
import com.rbi.security.entity.web.safe.content.SafeTrainingMaterials;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.hid.HidDangerDAO;
import com.rbi.security.web.DAO.safe.SafeTrainingMaterialsDAO;
import com.rbi.security.web.service.HidDangerService;
import com.rbi.security.web.service.SafeTrainingMaterialsService;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: SafeTrainingMaterialsServiceImpl
 * @USER: "谢青"
 * @DATE: 2020/5/27
 * @TIME: 11:13
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 27
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 11
 * @MINUTE: 13
 * @PROJECT_NAME: security
 **/


@Service
public class SafeTrainingMaterialsServiceImpl implements SafeTrainingMaterialsService {

    @Value("${uploadfile.ip}")
    private String fileIp;
    @Value("${hiddenPath}")
    private String hiddenPath;
    @Value("${path2}")
    private String path;

    @Autowired
    SafeTrainingMaterialsDAO safeTrainingMaterialsDAO;
    @Autowired
    HidDangerDAO hidDangerDAO;


    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String add(SafeTrainingMaterials safeTrainingMaterials, MultipartFile file) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        SysCompanyPersonnel sysCompanyPersonnel = hidDangerDAO.findPersonnelById(currentUser.getCompanyPersonnelId());
        if (file!=null){
            String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
            String filename = file.getOriginalFilename();
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path+hiddenPath, filename));
            String resourcePath = hiddenPath+filename;
            int count = safeTrainingMaterialsDAO.countByResourcePath(resourcePath);
            if (count != 0 ){
                return "系统存在有相同的文件，请修改文件名";
            }
            safeTrainingMaterials.setResourceName(filename);
            safeTrainingMaterials.setResourcePath(resourcePath);
            safeTrainingMaterials.setOperatingStaff(sysCompanyPersonnel.getId());
            safeTrainingMaterials.setOperatorName(sysCompanyPersonnel.getName());
            safeTrainingMaterials.setIdt(idt);
            safeTrainingMaterialsDAO.add(safeTrainingMaterials);
            return "1000";
        }else {
            return "文件为空";
        }
    }

    @Override
    public PageData findByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<SafeTrainingMaterials> safeTrainingMaterials = safeTrainingMaterialsDAO.findByPage(pageNo2,pageSize);
        for (int i=0;i< safeTrainingMaterials.size();i++){
            safeTrainingMaterials.get(i).setResourcePath(fileIp+safeTrainingMaterials.get(i).getResourcePath());
        }
        int totalPage = 0;
        int count = safeTrainingMaterialsDAO.findByPageNum();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, safeTrainingMaterials);
    }

    @Override
    public List<SafeContentCategory> findType() {
        try {
            List<SafeContentCategory> safeContentCategories = safeTrainingMaterialsDAO.findType();
            return safeContentCategories;
        }catch (NullPointerException e){
            return null;
        }
    }


    @Override
    public PageData findByCondition(int pageNo, int pageSize, int value) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<SafeTrainingMaterials> safeTrainingMaterials = safeTrainingMaterialsDAO.findByCondition(pageNo2,pageSize,value);
        for (int i=0;i< safeTrainingMaterials.size();i++){
            safeTrainingMaterials.get(i).setResourcePath(fileIp+safeTrainingMaterials.get(i).getResourcePath());
        }
        int totalPage = 0;
        int count = safeTrainingMaterialsDAO.findByConditionNum(value);
        if (0 == count % pageSize) {
        totalPage = count / pageSize;
        } else {
        totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, safeTrainingMaterials);
    }

    @Override
    public PageData findByName(int pageNo, int pageSize, String value) {
        String name = "'%"+value+"%'";
        int pageNo2 = pageSize * (pageNo - 1);
        List<SafeTrainingMaterials> safeTrainingMaterials = safeTrainingMaterialsDAO.findByName(pageNo2,pageSize,name);
        for (int i=0;i< safeTrainingMaterials.size();i++){
            safeTrainingMaterials.get(i).setResourcePath(fileIp+safeTrainingMaterials.get(i).getResourcePath());
        }
        int totalPage = 0;
        int count = safeTrainingMaterialsDAO.findByNameNum(name);
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, safeTrainingMaterials);
    }

    @Override
    public String deleteByIds(JSONArray array) {
            JSONObject obj = (JSONObject) array.get(0);
            int id = obj.getInteger("id");
            int num = safeTrainingMaterialsDAO.findPlanNum(id);
            if (num == 0){
                safeTrainingMaterialsDAO.deleteById(id);
                return "1000";
            }else {
                return "该培训内容已被使用";
            }
    }

    @Override
    public PageData findFileByCategory(int pageNo, int pageSize, Integer value) {
        try {
            int pageNo2 = pageSize * (pageNo - 1);
            List<SafeTrainingMaterials> safeTrainingMaterials = safeTrainingMaterialsDAO.findFileByCategory(pageNo2,pageSize,value);
            for (int i=0;i< safeTrainingMaterials.size();i++){
                safeTrainingMaterials.get(i).setResourcePath(fileIp+safeTrainingMaterials.get(i).getResourcePath());
            }
            int totalPage = 0;
            int count = safeTrainingMaterialsDAO.findFileByCategoryNum(value);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, safeTrainingMaterials);
        }catch (NullPointerException e){
            return null;
        }

    }

    @Override
    public PageData findVideoByCategory(int pageNo, int pageSize, Integer value) {
        try {
            int pageNo2 = pageSize * (pageNo - 1);
            List<SafeTrainingMaterials> safeTrainingMaterials = safeTrainingMaterialsDAO.findVideoByCategory(pageNo2, pageSize, value);
            for (int i = 0; i < safeTrainingMaterials.size(); i++) {
                safeTrainingMaterials.get(i).setResourcePath(fileIp +safeTrainingMaterials.get(i).getResourcePath());
            }
            int totalPage = 0;
            int count = safeTrainingMaterialsDAO.findVideoByCategoryNum(value);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, safeTrainingMaterials);
        } catch (NullPointerException e) {
            return null;
        }

    }
}
