package com.rbi.security.entity.web.safe;

import java.io.Serializable;

/**
 * (SafeDemandSystem)实体类
 *
 * @author makejava
 * @since 2020-05-17 19:27:37
 */
public class SafeDemandSystem implements Serializable {
    private static final long serialVersionUID = -69926847008826327L;
    
    private Integer id;
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
    * 创建时间
    */
    private String idt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getIdt() {
        return idt;
    }

    public void setIdt(String idt) {
        this.idt = idt;
    }

}