package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.risk.RiskControl;
import com.rbi.security.entity.web.risk.RiskControlPicture;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorReviewDTO;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.Tools;
import com.rbi.security.web.DAO.risk.RiskControlDAO;
import com.rbi.security.web.service.RiskControlService;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: RiskControlServiceImp
 * @USER: "谢青"
 * @DATE: 2020/6/18
 * @TIME: 11:05
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 18
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 11
 * @MINUTE: 05
 * @PROJECT_NAME: security
 **/
@Service
public class RiskControlServiceImp implements RiskControlService {
    @Value("${uploadfile.ip}")
    private String fileIp;
    @Value("${riskPath}")
    private String riskPath;
    @Value("${path2}")
    private String path;

    @Autowired
    RiskControlDAO riskControlDAO;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String addInside(RiskControl riskControl, MultipartFile[] picture) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        riskControl.setOperatingStaff(personnelId);
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        String riskCode = DateUtil.timeStamp();
        try {
            riskControl.setRiskCode(riskCode);
            riskControl.setRiskType("1");
            riskControl.setIdt(idt);
            //照片添加
            if (picture.length > 6) {
                return "照片数量不能大于6张";
            }
            if (picture.length > 0) {
                for (int i = 0; i < picture.length; i++) {
                    String contentType = picture[i].getContentType();
                    if (contentType.startsWith("image")) {
                        String timestamps = DateUtil.timeStamp();
                        String newFileName = timestamps + new Random().nextInt() + ".jpg";
                        FileUtils.copyInputStreamToFile(picture[i].getInputStream(), new File(path+riskPath, newFileName));
                        riskControlDAO.addPicture(riskCode,riskPath+newFileName);
                    }
                }
            }
            //所属组织
            SysOrganization sysOrganization2 = riskControlDAO.findAllByOrganizationId(riskControl.getOrganizationId());
            int level = sysOrganization2.getLevel();
            if (level == 4 ){
                riskControl.setClassId(sysOrganization2.getId());
                riskControl.setClassName(sysOrganization2.getOrganizationName());
            }
            if (level == 3 ){
                riskControl.setWorkshopId(sysOrganization2.getId());
                riskControl.setWorkshopName(sysOrganization2.getOrganizationName());
            }
            if (level == 2 ){
                riskControl.setFactoryId(sysOrganization2.getId());
                riskControl.setFactoryName(sysOrganization2.getOrganizationName());
            }
            if (level == 1 ){
                riskControl.setCompanyId(sysOrganization2.getId());
                riskControl.setCompanyName(sysOrganization2.getOrganizationName());
            }
            Integer parentId = sysOrganization2.getParentId();
            level = level -1;
            while (level !=0){
                SysOrganization sysOrganization3 = riskControlDAO.findAllByOrganizationId(parentId);
                if (level == 3 ){
                    riskControl.setWorkshopId(sysOrganization3.getId());
                    riskControl.setWorkshopName(sysOrganization3.getOrganizationName());
                }
                if (level == 2 ){
                    riskControl.setFactoryId(sysOrganization3.getId());
                    riskControl.setFactoryName(sysOrganization3.getOrganizationName());
                }
                if (level == 1 ){
                    riskControl.setCompanyId(sysOrganization3.getId());
                    riskControl.setCompanyName(sysOrganization3.getOrganizationName());
                }
                parentId = sysOrganization3.getParentId();
                level=level - 1;
            }
            riskControlDAO.add(riskControl);
            return "1000";
        }catch (NullPointerException e){
            return "没有创建完整的单位/登录用户没有绑定组织等为空异常";
        }catch (NumberFormatException e){
            return "数据格式错误";
        }catch (IndexOutOfBoundsException e){
            return "数组溢出";
        }
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String update(RiskControl riskControl, MultipartFile[] picture) throws IOException {
        String udt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        riskControl.setUdt(udt);
        try {
            //照片添加
            int num = riskControlDAO.findPictureNum(riskControl.getRiskCode());
            if (picture.length > 6-num) {
                return "照片数量不能大于6张";
            }
            if (picture.length > 0) {
                for (int i = 0; i < picture.length; i++) {
                    String contentType = picture[i].getContentType();
                    if (contentType.startsWith("image")) {
                        String timestamps = DateUtil.timeStamp();
                        String newFileName = timestamps + new Random().nextInt() + ".jpg";
                        FileUtils.copyInputStreamToFile(picture[i].getInputStream(), new File(path+riskPath, newFileName));
                        riskControlDAO.addPicture(riskControl.getRiskCode(),riskPath+newFileName);
                    }
                }
            }
            //所属组织
            SysOrganization sysOrganization2 = riskControlDAO.findAllByOrganizationId(riskControl.getOrganizationId());
            int level = sysOrganization2.getLevel();
            if (level == 4 ){
                riskControl.setClassId(sysOrganization2.getId());
                riskControl.setClassName(sysOrganization2.getOrganizationName());
            }
            if (level == 3 ){
                riskControl.setWorkshopId(sysOrganization2.getId());
                riskControl.setWorkshopName(sysOrganization2.getOrganizationName());
            }
            if (level == 2 ){
                riskControl.setFactoryId(sysOrganization2.getId());
                riskControl.setFactoryName(sysOrganization2.getOrganizationName());
            }
            if (level == 1 ){
                riskControl.setCompanyId(sysOrganization2.getId());
                riskControl.setCompanyName(sysOrganization2.getOrganizationName());
            }
            Integer parentId = sysOrganization2.getParentId();
            level = level -1;
            while (level !=0){
                SysOrganization sysOrganization3 = riskControlDAO.findAllByOrganizationId(parentId);
                if (level == 3 ){
                    riskControl.setWorkshopId(sysOrganization3.getId());
                    riskControl.setWorkshopName(sysOrganization3.getOrganizationName());
                }
                if (level == 2 ){
                    riskControl.setFactoryId(sysOrganization3.getId());
                    riskControl.setFactoryName(sysOrganization3.getOrganizationName());
                }
                if (level == 1 ){
                    riskControl.setCompanyId(sysOrganization3.getId());
                    riskControl.setCompanyName(sysOrganization3.getOrganizationName());
                }
                parentId = sysOrganization3.getParentId();
                level=level - 1;
            }
            riskControlDAO.update(riskControl);
            return "1000";
        }catch (NullPointerException e){
            return "没有创建完整的单位/登录用户没有绑定组织等为空异常";
        }catch (NumberFormatException e){
            return "数据格式错误";
        }catch (IndexOutOfBoundsException e){
            return "数组溢出";
        }
    }

    @Override
    public Map<String, Object> riskValueAndGrade(RiskControl riskControl) {
        Map<String, Object> map = new HashMap<>();
        double value = riskControl.getConsequence()*riskControl.getExpose()*riskControl.getPossibility();
        double riskValue = Tools.doubleChangeValue(value,1);
        String riskGrad = null;
        if (riskValue>=400){
            riskGrad = "一级";
        }else if (200<= riskValue && riskValue < 400){
            riskGrad = "二级";
        }else if (70<= riskValue && riskValue < 200){
            riskGrad = "三级";
        }else if (riskValue < 70){
            riskGrad = "四级";
        }
        map.put("riskValue",riskValue);
        map.put("riskGrad",riskGrad);
        return map;
    }

    @Override
    public Map<String, Object> measuresResult(RiskControl riskControl) {
        Map<String, Object> map = new HashMap<>();
        double value = riskControl.getRiskValue()/(riskControl.getMeasuresCost()*riskControl.getMeasuresEffective());
        double measuresResult = Tools.doubleChangeValue(value,1);
        map.put("measuresResult",measuresResult);
        return map;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public String addOutside(RiskControl riskControl, MultipartFile[] picture) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        Integer userId = currentUser.getId();
        riskControl.setOperatingStaff(personnelId);
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        String riskCode = DateUtil.timeStamp();
        try {
            riskControl.setRiskCode(riskCode);
            riskControl.setRiskType("2");
            riskControl.setIdt(idt);
            //排查前照片添加
            if (picture.length > 6) {
                return "照片数量不能大于6张";
            }
            if (picture.length > 0) {
                for (int i = 0; i < picture.length; i++) {
                    String contentType = picture[i].getContentType();
                    if (contentType.startsWith("image")) {
                        String timestamps = DateUtil.timeStamp();
                        String newFileName = timestamps + new Random().nextInt() + ".jpg";
                        FileUtils.copyInputStreamToFile(picture[i].getInputStream(), new File(path+riskPath, newFileName));
                        riskControlDAO.addPicture(riskCode,riskPath+newFileName);
                    }
                }
            }
            //所属组织
            SysOrganization sysOrganization2 = riskControlDAO.findAllByOrganizationId(riskControl.getOrganizationId());
            int level = sysOrganization2.getLevel();
            if (level == 4 ){
                riskControl.setClassId(sysOrganization2.getId());
                riskControl.setClassName(sysOrganization2.getOrganizationName());
            }
            if (level == 3 ){
                riskControl.setWorkshopId(sysOrganization2.getId());
                riskControl.setWorkshopName(sysOrganization2.getOrganizationName());
            }
            if (level == 2 ){
                riskControl.setFactoryId(sysOrganization2.getId());
                riskControl.setFactoryName(sysOrganization2.getOrganizationName());
            }
            if (level == 1 ){
                riskControl.setCompanyId(sysOrganization2.getId());
                riskControl.setCompanyName(sysOrganization2.getOrganizationName());
            }
            Integer parentId = sysOrganization2.getParentId();
            level = level -1;
            while (level !=0){
                SysOrganization sysOrganization3 = riskControlDAO.findAllByOrganizationId(parentId);
                if (level == 3 ){
                    riskControl.setWorkshopId(sysOrganization3.getId());
                    riskControl.setWorkshopName(sysOrganization3.getOrganizationName());
                }
                if (level == 2 ){
                    riskControl.setFactoryId(sysOrganization3.getId());
                    riskControl.setFactoryName(sysOrganization3.getOrganizationName());
                }
                if (level == 1 ){
                    riskControl.setCompanyId(sysOrganization3.getId());
                    riskControl.setCompanyName(sysOrganization3.getOrganizationName());
                }
                parentId = sysOrganization3.getParentId();
                level=level - 1;
            }
            riskControlDAO.add(riskControl);
            return "1000";
        }catch (NullPointerException e){
            return "没有创建完整的单位/登录用户没有绑定组织等为空异常";
        }catch (NumberFormatException e){
            return "数据格式错误";
        }catch (IndexOutOfBoundsException e){
            return "数组溢出";
        }
    }

    @Override
    public PageData findByPage(String riskType,int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<RiskControl> riskControls = riskControlDAO.findByPage(riskType,pageNo2,pageSize);
        for (int j=0;j<riskControls.size();j++){
            List<RiskControlPicture> riskControlPictures = riskControlDAO.findPictureByRiskCode(riskControls.get(j).getRiskCode());
            for (int i=0; i< riskControlPictures.size(); i++){
                riskControlPictures.get(i).setPicture(fileIp+riskControlPictures.get(i).getPicture());
            }
            riskControls.get(j).setImg(riskControlPictures);
        }
        int totalPage = 0;
        int count = riskControlDAO.findNum(riskType);
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, riskControls);
    }

    @Override
    public PageData findSeriousRiskByPage(String riskGrad, int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<RiskControl> riskControls = riskControlDAO.findSeriousRiskByPage(riskGrad,pageNo2,pageSize);
        for (int j=0;j<riskControls.size();j++){
            List<RiskControlPicture> riskControlPictures = riskControlDAO.findPictureByRiskCode(riskControls.get(j).getRiskCode());
            for (int i=0; i< riskControlPictures.size(); i++){
                riskControlPictures.get(i).setPicture(fileIp+riskControlPictures.get(i).getPicture());
            }
            riskControls.get(j).setImg(riskControlPictures);
            //区域内外
            if (riskControls.get(j).getRiskType().equals("1")){
                riskControls.get(j).setRiskType("区域内");
            }else {
                riskControls.get(j).setRiskType("区域外");
            }
        }
        int totalPage = 0;
        int count = riskControlDAO.findSeriousRiskByPageNum(riskGrad);
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, riskControls);
    }

    @Override
    public void deleteByPictureId(int id) {
        riskControlDAO.deleteByPictureId(id);
    }


    @Override
    public PageData findInsideByCondition(String type, String value, int pageNo, int pageSize) {
        if (type.equals("单位")) {
            String value2 = "'%" + value + "%'";
            int pageNo2 = pageSize * (pageNo - 1);
            List<RiskControl> riskControls = riskControlDAO.findUnitByPage("1",value2, pageNo2, pageSize);
            for (int j = 0; j < riskControls.size(); j++) {
                List<RiskControlPicture> riskControlPictures = riskControlDAO.findPictureByRiskCode(riskControls.get(j).getRiskCode());
                for (int i = 0; i < riskControlPictures.size(); i++) {
                    riskControlPictures.get(i).setPicture(fileIp +riskControlPictures.get(i).getPicture());
                }
                riskControls.get(j).setImg(riskControlPictures);
            }
            int totalPage = 0;
            int count = riskControlDAO.findUnitByPageNum("1",value2);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, riskControls);
        } else {
            String value2 = "'%" + value + "%'";
            int pageNo2 = pageSize * (pageNo - 1);
            List<RiskControl> riskControls = riskControlDAO.findWorkTypeByPage("1",value2, pageNo2, pageSize);
            for (int j = 0; j < riskControls.size(); j++) {
                List<RiskControlPicture> riskControlPictures = riskControlDAO.findPictureByRiskCode(riskControls.get(j).getRiskCode());
                for (int i = 0; i < riskControlPictures.size(); i++) {
                    riskControlPictures.get(i).setPicture(fileIp +riskControlPictures.get(i).getPicture());
                }
                riskControls.get(j).setImg(riskControlPictures);
            }
            int totalPage = 0;
            int count = riskControlDAO.findWorkTypeByPageNum("1",value2);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, riskControls);
        }
    }

    @Override
    public PageData findOutsideByCondition(String type, String value, int pageNo, int pageSize) {
        if (type.equals("单位")) {
            String value2 = "'%" + value + "%'";
            int pageNo2 = pageSize * (pageNo - 1);
            List<RiskControl> riskControls = riskControlDAO.findUnitByPage("2",value2, pageNo2, pageSize);
            for (int j = 0; j < riskControls.size(); j++) {
                List<RiskControlPicture> riskControlPictures = riskControlDAO.findPictureByRiskCode(riskControls.get(j).getRiskCode());
                for (int i = 0; i < riskControlPictures.size(); i++) {
                    riskControlPictures.get(i).setPicture(fileIp + riskControlPictures.get(i).getPicture());
                }
                riskControls.get(j).setImg(riskControlPictures);
            }
            int totalPage = 0;
            int count = riskControlDAO.findUnitByPageNum("2",value2);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, riskControls);
        } else {
            String value2 = "'%" + value + "%'";
            int pageNo2 = pageSize * (pageNo - 1);
            List<RiskControl> riskControls = riskControlDAO.findWorkTypeByPage("2",value2, pageNo2, pageSize);
            for (int j = 0; j < riskControls.size(); j++) {
                List<RiskControlPicture> riskControlPictures = riskControlDAO.findPictureByRiskCode(riskControls.get(j).getRiskCode());
                for (int i = 0; i < riskControlPictures.size(); i++) {
                    riskControlPictures.get(i).setPicture(fileIp +riskControlPictures.get(i).getPicture());
                }
                riskControls.get(j).setImg(riskControlPictures);
            }
            int totalPage = 0;
            int count = riskControlDAO.findWorkTypeByPageNum("2",value2);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, riskControls);
        }
    }

    @Override
    public PageData findSeriousByCondition(String type, String value, int pageNo, int pageSize) {
        if (type.equals("单位")) {
            String value2 = "'%" + value + "%'";
            int pageNo2 = pageSize * (pageNo - 1);
            List<RiskControl> riskControls = riskControlDAO.findSeriousUnitByPage(value2, pageNo2, pageSize);
            for (int j = 0; j < riskControls.size(); j++) {
                List<RiskControlPicture> riskControlPictures = riskControlDAO.findPictureByRiskCode(riskControls.get(j).getRiskCode());
                for (int i = 0; i < riskControlPictures.size(); i++) {
                    riskControlPictures.get(i).setPicture(fileIp +riskControlPictures.get(i).getPicture());
                }
                riskControls.get(j).setImg(riskControlPictures);
                //区域内外
                if (riskControls.get(j).getRiskType().equals("1")){
                    riskControls.get(j).setRiskType("区域内");
                }else {
                    riskControls.get(j).setRiskType("区域外");
                }
            }
            int totalPage = 0;
            int count = riskControlDAO.findSeriousUnitByPageNum(value2);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, riskControls);
        } else {
            String value2 = "'%" + value + "%'";
            int pageNo2 = pageSize * (pageNo - 1);
            List<RiskControl> riskControls = riskControlDAO.findSeriousWorkTypeByPage(value2, pageNo2, pageSize);
            for (int j = 0; j < riskControls.size(); j++) {
                List<RiskControlPicture> riskControlPictures = riskControlDAO.findPictureByRiskCode(riskControls.get(j).getRiskCode());
                for (int i = 0; i < riskControlPictures.size(); i++) {
                    riskControlPictures.get(i).setPicture(fileIp +riskControlPictures.get(i).getPicture());
                }
                riskControls.get(j).setImg(riskControlPictures);
            }
            int totalPage = 0;
            int count = riskControlDAO.findSeriousWorkTypeByPageNum(value2);
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, riskControls);
        }
    }
}
