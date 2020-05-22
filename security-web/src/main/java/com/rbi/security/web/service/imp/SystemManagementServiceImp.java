package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.system.PagingSystemInfo;
import com.rbi.security.entity.web.system.SystemCategory;
import com.rbi.security.entity.web.system.SystemFile;
import com.rbi.security.entity.web.user.PagingUser;
import com.rbi.security.exception.NonExistentException;
import com.rbi.security.tool.LocalDateUtils;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.system.SystemCategoryDAO;
import com.rbi.security.web.DAO.system.SystemFileDAO;
import com.rbi.security.web.service.SystemManagementService;
import javafx.scene.control.Tab;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SystemManagementServiceImp implements SystemManagementService {
    private static final Logger logger = LoggerFactory.getLogger(SystemManagementServiceImp.class);

    @Value("${uploadfile.ip}")
    private String fileIp;//此ip与此应用部署的服务区ip一致
    @Autowired
    SystemCategoryDAO systemCategoryDAO;
    @Autowired
    SystemFileDAO systemFileDAO;
    /*
    上传制度文件
     */
    @Transactional(propagation= Propagation.REQUIRED)
    public void uploadDemandSystemFiles(MultipartFile[] multipartFiles,int systemCategoryId) throws RuntimeException {
        try {
            String idt = LocalDateUtils.localDateTimeFormat(LocalDateTime.now(), LocalDateUtils.FORMAT_PATTERN);
            SystemFile systemFile = new SystemFile();
            // 创建文件在服务器端的存放路径
            SystemCategory systemCategory=systemCategoryDAO.getSystemCategoryById(systemCategoryId);
            if(systemCategory==null){
                throw new NonExistentException("制度类型不存在");
            }
            File fileDir = new File(systemCategory.getStoragePath());
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
                //
                Subject subject = SecurityUtils.getSubject();
                AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
                systemFile.setFileName(fileName);
                systemFile.setSystemCategoryId(systemCategory.getId());
                systemFile.setFilePath(fileDir + "/" + fileName);
                systemFile.setOperatingStaff(currentUser.getCompanyPersonnelId());
                systemFile.setIdt(idt);
                systemFileDAO.insertSystemFile(systemFile);
            }
        } catch (NonExistentException e) {
            logger.error("上传制度文件失败，异常为{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }catch (Exception e) {
            logger.error("上传制度文件失败，异常为{}",e);
            throw new RuntimeException("上传制度文件失败");
        }
    }
    /**
     * 删除制度文件
     */
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteSystemFile(int id) throws RuntimeException {
        try{
            SystemFile systemFile=systemFileDAO.getSystemFileById(id);
            if(systemFile==null){
                throw new NonExistentException("制度文件不存在");
            }
            File file = new File(systemFile.getFilePath());
            if (!file.exists()) {
                logger.error("删除文件失败:" + systemFile.getFilePath() + "不存在！");
                throw new NonExistentException("制度文件不存在");
            } else {
                if (file.isFile()){
                    // 路径为文件且不为空则进行删除
                    if (file.isFile() && file.exists()) {
                        file.delete();
                    }
                 }
            }
            systemFileDAO.deleteSystemFileById(id);
        }catch (NonExistentException e) {
            logger.error("删除制度文件失败，异常为{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        catch (Exception e){
            logger.error("删除制度文件失败，异常为{}",e);
            throw new RuntimeException("删除制度文件失败");
        }
    }
    /**
     *修改制度文件
     */

    /**
     * 分页查看制度文件
     */
       public PageData<PagingSystemInfo> getPagingSystemInfo(int pageNo,int pageSize ,int startIndex)throws RuntimeException{
           List<PagingSystemInfo> pagingSystemInfoList=null;
           try{
               pagingSystemInfoList=systemFileDAO.getPagingSystemInfo(startIndex,pageSize);
               for(int i=0;i<pagingSystemInfoList.size();i++){
                   pagingSystemInfoList.get(i).setFilePath(fileIp+pagingSystemInfoList.get(i).getFilePath());
               }
               int count =systemFileDAO.getSystemFileCount();
               int totalPage;
               if (count%pageSize==0){
                   totalPage = count/pageSize;
               }else {
                   totalPage = count/pageSize+1;
               }
               return new PageData<PagingSystemInfo>(pageNo,pageSize,totalPage,count,pagingSystemInfoList);
           }catch (Exception e){
               logger.error("分页获取制度文件失败，异常为{}",e);
               throw new RuntimeException("分页获取制度文件失败");
           }

       }
}
