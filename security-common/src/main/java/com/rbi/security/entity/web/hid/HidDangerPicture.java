package com.rbi.security.entity.web.hid;

import lombok.Data;

@Data
public class HidDangerPicture {
    private Integer id;
    private String hidDangerCode;
    private String beforePicture1;
    private String beforePicture2;
    private String beforePicture3;
    private String beforePicture4;
    private String beforePicture5;//隐患处理状态：1、上报未整改 2、责令未整改 3、已通知待整改 4、已整改待审核 5、审核通过 6、审核不通过
    private String beforePicture6;
    private String afterPicture1;
    private String afterPicture2;
    private String afterPicture3;
    private String afterPicture4;
    private String afterPicture5;
    private String afterPicture6;

}
