package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.rbi.security.entity.web.SeriousDanger.PagingSeriousDanger;
import com.rbi.security.entity.web.entity.SafeSubject;
import com.rbi.security.entity.web.entity.SeriousDanger;
import com.rbi.security.entity.web.entity.SeriousDangerPicture;
import com.rbi.security.entity.web.safe.PagingSafe;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.SeriousDangerDAO;
import com.rbi.security.web.DAO.SeriousDangerPictureDAO;
import com.rbi.security.web.service.SeriousDangerService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: SeriousDangerServiceImpl
 * @USER: "林新元"
 * @DATE: 2020/6/16
 * @TIME: 15:32
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 六月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 16
 * @DAY_NAME_SHORT: 星期二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 15
 * @MINUTE: 32
 * @PROJECT_NAME: security
 **/
@Service
public class SeriousDangerServiceImpl implements SeriousDangerService {
    private static final Logger logger = LoggerFactory.getLogger(SafeSubjectServiceImpl.class);
    @Value("${uploadfile.ip}")
    private String fileIp;//此ip与此应用部署的服务区ip一致
    @Value("${seriousDangerPath}")
    private String seriousDangerPath;

    @Autowired(required = false)
    SeriousDangerDAO seriousDangerDAO;
    @Autowired(required = false)
    SeriousDangerPictureDAO seriousDangerPictureDAO;

    @Override
    public String insertSeriousDanger(SeriousDanger seriousDanger, MultipartFile[] seriousDangerPicture) throws IOException {
        try {

            seriousDangerDAO.insertSeriousDanger(seriousDanger);

            if (seriousDangerPicture.length > 6) {
                return "照片数量不能大于6张";
            }else if (seriousDangerPicture.length > 0) {
                for (int i = 0; i < seriousDangerPicture.length; i++) {
                    SeriousDangerPicture seriousDangerPicture1 = new SeriousDangerPicture();
                    String contentType = seriousDangerPicture[i].getContentType();
                    if (contentType.startsWith("image")) {
                        String timestamps = DateUtil.timeStamp();
                        String newFileName = timestamps + new Random().nextInt() + ".jpg";
                        FileUtils.copyInputStreamToFile(seriousDangerPicture[i].getInputStream(), new File(seriousDangerPath, newFileName));
                        seriousDangerPicture1.setSeriousDangerId(seriousDanger.getId());
                        seriousDangerPicture1.setSeriousDangerPicturePath(seriousDangerPath + newFileName);
                        seriousDangerPictureDAO.insertSeriousDangerPicture(seriousDangerPicture1);
                    }
                }
            }
            return "1000";
        } catch (NumberFormatException e) {
            return "数据格式错误";
        }
    }

    @Override
    public PageData findSeriousDangerByPage(JSONObject json) throws IOException {
        int totalPage=0;
        int count=0;
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            int recNo = pageSize * (pageNo - 1);
            List<PagingSeriousDanger> pagingSeriousDangerList = seriousDangerDAO.findSeriousDangerByPage(recNo, pageSize);
            for (int j=0;j<pagingSeriousDangerList.size();j++) {
                Integer Id = pagingSeriousDangerList.get(j).getId();
                List<SeriousDangerPicture> seriousDangerPictureList = seriousDangerPictureDAO.findSeriousDangerPictureByPageAndSeriousDangerId(Id);
                for (int i = 0; i < seriousDangerPictureList.size(); i++) {
                    seriousDangerPictureList.get(i).setSeriousDangerPicturePath(fileIp + seriousDangerPictureList.get(i).getSeriousDangerPicturePath());
                }
                pagingSeriousDangerList.get(j).setSeriousDangerPictureList(seriousDangerPictureList);
            }
            count = seriousDangerDAO.findNumSeriousDanger();
            if (count % pageSize == 0) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, pagingSeriousDangerList);
        } catch (Exception e) {
            logger.error("查询信息异常，异常信息为{}", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public PageData findSeriousDangerByPageAndName(JSONObject json) throws IOException {
        int totalPage=0;
        int count=0;
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            int recNo = pageSize * (pageNo - 1);
            List<PagingSeriousDanger> pagingSeriousDangerList = seriousDangerDAO.findSeriousDangerByPageAndName(json.getString("seriousDangerName"),recNo, pageSize);
            for (int j=0;j<pagingSeriousDangerList.size();j++) {
                Integer Id = pagingSeriousDangerList.get(j).getId();
                List<SeriousDangerPicture> seriousDangerPictureList = seriousDangerPictureDAO.findSeriousDangerPictureByPageAndSeriousDangerId(Id);
                for (int i = 0; i < seriousDangerPictureList.size(); i++) {
                    seriousDangerPictureList.get(i).setSeriousDangerPicturePath(fileIp + seriousDangerPictureList.get(i).getSeriousDangerPicturePath());
                }
                pagingSeriousDangerList.get(j).setSeriousDangerPictureList(seriousDangerPictureList);
            }
            count = seriousDangerDAO.findNumSeriousDangerByName(json.getString("seriousDangerName"));
            if (count % pageSize == 0) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, pagingSeriousDangerList);
        } catch (Exception e) {
            logger.error("查询信息异常，异常信息为{}", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public PagingSeriousDanger findSeriousDangerByID(JSONObject json) throws IOException {
        PagingSeriousDanger pagingSeriousDanger = seriousDangerDAO.findSeriousDangerByID(json.getInteger("id"));
        List<SeriousDangerPicture> seriousDangerPictureList = seriousDangerPictureDAO.findSeriousDangerPictureByPageAndSeriousDangerId(json.getInteger("id"));
        for (int i=0;i<seriousDangerPictureList.size();i++){
            seriousDangerPictureList.get(i).setSeriousDangerPicturePath(fileIp +seriousDangerPictureList.get(i).getSeriousDangerPicturePath());
        }
        pagingSeriousDanger.setSeriousDangerPictureList(seriousDangerPictureList);
        return pagingSeriousDanger;
    }

    @Override
    public String updateSeriousDanger(SeriousDanger seriousDanger,Integer[] pictureId, MultipartFile[] seriousDangerPicture) throws IOException {
        try {
            for (int j=0;j<pictureId.length;) {
                seriousDangerPictureDAO.deleteSeriousDangerPicture(pictureId[j]);
                if (seriousDangerPictureDAO.findSeriousDangerPictureByPageAndSeriousDangerId(pictureId[j]).isEmpty()) {
                    j++;
                } else {
                    return pictureId[j] + "删除失败！";
                }
            }
                seriousDangerDAO.updateSeriousDanger(seriousDanger);
                if (seriousDangerPicture.length > 6) {
                    return "照片数量不能大于6张";
                } else if (seriousDangerPicture.length > 0) {
                    for (int i = 0; i < seriousDangerPicture.length; i++) {
                        SeriousDangerPicture seriousDangerPicture1 = new SeriousDangerPicture();
                        String contentType = seriousDangerPicture[i].getContentType();
                        if (contentType.startsWith("image")) {
                            String timestamps = DateUtil.timeStamp();
                            String newFileName = timestamps + new Random().nextInt() + ".jpg";
                            FileUtils.copyInputStreamToFile(seriousDangerPicture[i].getInputStream(), new File(seriousDangerPath, newFileName));
                            seriousDangerPicture1.setSeriousDangerId(seriousDanger.getId());
                            seriousDangerPicture1.setSeriousDangerPicturePath(seriousDangerPath + newFileName);
                            seriousDangerPictureDAO.insertSeriousDangerPicture(seriousDangerPicture1);
                        }
                    }
                }
            return "1000";
        } catch (NumberFormatException e) {
            return "数据格式错误";
        }
    }
}
