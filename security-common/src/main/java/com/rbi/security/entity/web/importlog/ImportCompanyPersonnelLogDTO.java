package com.rbi.security.entity.web.importlog;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImportCompanyPersonnelLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String code;
    private String result;
    private String employeeNumber;
    private String name;
    private String remarks;
    private String idt;
    private String udt;
}
