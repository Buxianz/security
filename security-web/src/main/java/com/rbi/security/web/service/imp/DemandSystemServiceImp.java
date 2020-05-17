package com.rbi.security.web.service.imp;

import com.rbi.security.web.service.DemandSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class DemandSystemServiceImp implements DemandSystemService {
    private static final Logger logger = LoggerFactory.getLogger(DemandSystemServiceImp.class);
    @Value("${uploadfile.path}")
    private String filePath;
    @Transactional(propagation= Propagation.REQUIRED)
    public void uploadDemandSystemFiles(MultipartFile[] multipartFiles) throws RuntimeException {
        try {
            // 创建文件在服务器端的存放路径
            File fileDir = new File(filePath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            // 生成文件在服务器端存放的名字
            for (int i = 0; i < multipartFiles.length; i++) {
                String fileSuffix = multipartFiles[i].getOriginalFilename()
                        .substring(multipartFiles[i].getOriginalFilename().lastIndexOf("."));
                String fileName = multipartFiles[i].getOriginalFilename();
                        //UUID.randomUUID().toString() + fileSuffix;
                System.out.println(fileDir.toString());
                File file = new File(fileDir + "/" + fileName);
                // 上传
                multipartFiles[i].transferTo(file);
            }
        } catch (Exception e) {
            logger.error("上传教育制度文件失败，异常为{}",e);
            throw new RuntimeException();
        }
    }
}
