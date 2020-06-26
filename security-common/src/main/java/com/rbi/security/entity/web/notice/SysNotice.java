package com.rbi.security.entity.web.notice;

import lombok.Data;

/**
 * @Author: SXD
 * @Description:
 * @Date: create in 2020/6/26 19:25
 */
@Data
public class SysNotice {
    private Integer id;
    private String title;
    private String content;
    private String annex;
    private String idt;
    private String udt;
    private Integer operatingStaff;
}
