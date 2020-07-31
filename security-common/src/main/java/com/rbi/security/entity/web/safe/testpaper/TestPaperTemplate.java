package com.rbi.security.entity.web.safe.testpaper;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe.testpaper
 * @NAME: TestPaperTemplate  试卷模板
 * @USER: "吴松达"
 * @DATE: 2020/7/30
 * @TIME: 19:19
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 30
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 19
 * @MINUTE: 19
 * @PROJECT_NAME: security
 **/
public class TestPaperTemplate {
    /**
     * 题目类型
     */
    private Integer subjectType;
    /**
     * 题库id
     */
    private Integer subjectStoreId;
    /**
     *数量
     */
    private int number;

    public TestPaperTemplate() {
    }

    public TestPaperTemplate(Integer subjectType, Integer subjectStoreId) {
        this.subjectType = subjectType;
        this.subjectStoreId = subjectStoreId;
    }

    public Integer getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }

    public Integer getSubjectStoreId() {
        return subjectStoreId;
    }

    public void setSubjectStoreId(Integer subjectStoreId) {
        this.subjectStoreId = subjectStoreId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public void addNumber(int number) {
        this.number = this.number+number;
    }
}
