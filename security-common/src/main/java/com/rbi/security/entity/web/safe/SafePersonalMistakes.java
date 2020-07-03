package com.rbi.security.entity.web.safe;

import lombok.Data;

import java.io.Serializable;

/**
 * (SafePersonalMistakes)实体类
 *
 * @author makejava
 * @since 2020-06-30 09:33:22
 */
@Data
public class SafePersonalMistakes implements Serializable {
    private static final long serialVersionUID = 553403551412239216L;
    /**
    * 个人的错题id
    */
    private Integer id;
    /**
    * 公司人员信息id
    */
    private Integer companyPersonnelId;
    /**
    * 试卷的题目id
    */
    private Integer subjectId;
    /**
    * 创建时间
    */
    private String idt;

    public SafePersonalMistakes() {
    }

    public SafePersonalMistakes(Integer companyPersonnelId, Integer subjectId, String idt) {
        this.companyPersonnelId = companyPersonnelId;
        this.subjectId = subjectId;
        this.idt = idt;
    }
}