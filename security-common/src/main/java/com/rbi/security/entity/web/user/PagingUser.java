package com.rbi.security.entity.web.user;

/**
 * 用户信息分页查看
 */
public class PagingUser {
    private int id;//用户id
    private String username;//用户账号
    private int companyPersonnelId;//公司人员id
    /**
     * 组织id
     */
    private Long organizationId;
    private long companyId; //单位ID
    private String companyName;//单位名称
    private long factoryId;//工厂ID
    private String factoryName;//厂名称
    private long workshopId;//车间ID
    private String workshopName;//车间名称
    private long teamId;//班组ID
    private String teamName;//班组名称
    private String name;//姓名
    private String idCardNo;//身份证号码

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public int getCompanyPersonnelId() {
        return companyPersonnelId;
    }

    public void setCompanyPersonnelId(int companyPersonnelId) {
        this.companyPersonnelId = companyPersonnelId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public long getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(long factoryId) {
        this.factoryId = factoryId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public long getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(long workshopId) {
        this.workshopId = workshopId;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }
}
