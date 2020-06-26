package com.rbi.security.web.service;

import com.rbi.security.entity.web.notice.SysNotice;
import com.rbi.security.tool.PageData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: SXD
 * @Description:
 * @Date: create in 2020/6/26 19:30
 */
public interface SysNoticeService {

    PageData findByPage(int pageNo, int pageSize);

    void add(SysNotice sysNotice, MultipartFile file) throws IOException;
}
