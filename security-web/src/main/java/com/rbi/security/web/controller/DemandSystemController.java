package com.rbi.security.web.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@CrossOrigin(origins = "*",allowCredentials = "true")
@RestController
public class DemandSystemController {
    @Value("${uploadfile.path}")
    private String filePath;
    /**
     * 上传文件
     * @param multipartFile
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFiles(MultipartFile[] multipartFile,
                              HttpServletRequest httpServletRequest) {
        try {
            // 创建文件在服务器端的存放路径
           // String dir = httpServletRequest.getServletContext().getRealPath("/static");
            File fileDir = new File(filePath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            // 生成文件在服务器端存放的名字
            for (int i = 0; i < multipartFile.length; i++) {
                String fileSuffix = multipartFile[i].getOriginalFilename()
                        .substring(multipartFile[i].getOriginalFilename().lastIndexOf("."));
                String fileName = UUID.randomUUID().toString() + fileSuffix;
                File file = new File(fileDir + "/" + fileName);
                // 上传
                multipartFile[i].transferTo(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
        return "上传成功";
    }

}
