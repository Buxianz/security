package com.rbi.security.entity.web.hid;

import lombok.Data;

@Data
public class HidDangerPictureDTO {
    private Integer id;
    private String hidDangerCode;
    private String beforePicture;
    private String afterPicture;


}
