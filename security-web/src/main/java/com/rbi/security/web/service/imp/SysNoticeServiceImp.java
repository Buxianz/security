package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.notice.SysNotice;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.notice.SysNoticeDAO;
import com.rbi.security.web.service.SysNoticeService;
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
 * @Author: SXD
 * @Description:
 * @Date: create in 2020/6/26 19:30
 */
@Service
public class SysNoticeServiceImp implements SysNoticeService {
    @Value("${uploadfile.ip}")
    private String fileIp;
    @Value("${hiddenPath}")
    private String hiddenPath;
    @Autowired
    SysNoticeDAO sysNoticeDAO;

    @Override
    public PageData findByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<SysNotice> sysNotices = sysNoticeDAO.findByPage(pageNo2,pageSize);
        for (int i=0;i<sysNotices.size();i++){
            sysNotices.get(i).setAnnex(fileIp+sysNotices.get(i).getAnnex());
        }
        int totalPage = 0;
        int count = sysNoticeDAO.findByPageNum();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, sysNotices);
    }

    @Override
    public void add(SysNotice sysNotice, MultipartFile file) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        sysNotice.setOperatingStaff(personnelId);
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        sysNotice.setIdt(idt);
        if (file!=null){
            String filename = file.getOriginalFilename();
            String timestamps = DateUtil.timeStamp();
            String newFileName = timestamps + filename;
            System.out.println(newFileName);
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(hiddenPath, newFileName));
            sysNotice.setAnnex(hiddenPath+newFileName);
        }
        sysNoticeDAO.add(sysNotice);
    }
}
