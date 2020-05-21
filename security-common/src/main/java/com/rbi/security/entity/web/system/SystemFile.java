package com.rbi.security.entity.web.system;

import lombok.Data;

import java.io.Serializable;

/**
 * (SystemFile)实体类
 *
 * @author makejava
 * @since 2020-05-21 10:53:08
 */
@Data
public class SystemFile implements Serializable {
    private static final long serialVersionUID = 188731157158290270L;
    /**
    * 制度文件id
    */
    private Integer id;
    /**
    * 制度类别id
    */
    private Integer systemCategoryId;
    /**
    * 文件名称
    */
    private String fileName;
    /**
    * 文件路径
    */
    private String filePath;
    /**
    * 排序
    */
    private Integer order;
    /**
    * 操作人（公司员工信息id）
    */
    private Integer operatingStaff;
    /**
    * 创建时间
    */
    private String idt;

}