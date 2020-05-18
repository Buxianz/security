package com.rbi.security.web.controller;


import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.DemandSystemService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*",allowCredentials = "true")
@RestController
public class DemandSystemController {
    @Value("${uploadfile.path}")
    private String filePath;
    /**
     * 上传教育制度文件
     * @param multipartFile
     * @return
     */
    @Autowired
    DemandSystemService demandSystemService;
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseModel uploadFiles(MultipartFile[] multipartFiles,int order) {
        try {
            demandSystemService.uploadDemandSystemFiles(multipartFiles);
        } catch (Exception e) {
            return ResponseModel.build("1001", "上传文件失败");
        }
        return ResponseModel.build("1000", "上传文件成功");
    }

}
