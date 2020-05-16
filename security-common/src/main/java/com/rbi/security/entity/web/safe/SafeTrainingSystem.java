package com.rbi.security.entity.web.safe;

import java.util.Date;
import java.io.Serializable;

/**
 * (SafeTrainingSystem)实体类
 *
 * @author makejava
 * @since 2020-05-16 18:16:33
 */
public class SafeTrainingSystem implements Serializable {
    private static final long serialVersionUID = 983722767367508058L;
    /**
    * 培训制度id
    */
    private Long id;
    /**
    * 文件名称
    */
    private String fileName;
    /**
    * 文件路径
    */
    private String filePath;
    /**
    * 文件顺序
    */
    private Integer order;
    /**
    * 创建时间
    */
    private String idt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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