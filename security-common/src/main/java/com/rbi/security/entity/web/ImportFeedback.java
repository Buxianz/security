package com.rbi.security.entity.web;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web
 * @NAME: ImportFeedback
 * @USER: "吴松达"
 * @DATE: 2020/6/22
 * @TIME: 15:14
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 15
 * @MINUTE: 14
 * @PROJECT_NAME: security
 **/
@Data
public class ImportFeedback<T> {
    private int successSize;
    private int failSize;
    private T failTecord;
    public void successSizeIncrease(int size){
           this.successSize=this.successSize+size;
    }
    public void failSizeIncrease(int size){
        this.failSize=this.failSize+size;
    }
}
