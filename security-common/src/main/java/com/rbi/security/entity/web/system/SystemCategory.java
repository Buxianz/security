package com.rbi.security.entity.web.system;

import lombok.Data;

import java.io.Serializable;

/**
 * (SystemCategory)实体类
 *
 * @author makejava
 * @since 2020-05-21 10:49:11
 */
@Data
public class SystemCategory implements Serializable {
    private static final long serialVersionUID = -62695150045067908L;
    /**
    * 制度类别id
    */
    private Integer id;
    /**
    * 制度类别名称
    */
    private String categoryName;
    /**
    * 存放路径
    */
    private String storagePath;


}