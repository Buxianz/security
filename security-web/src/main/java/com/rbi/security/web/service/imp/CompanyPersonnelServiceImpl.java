package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.importlog.ImportCompanyPersonnelLogDTO;
import com.rbi.security.tool.ExcelPOI;
import com.rbi.security.tool.LocalDateUtils;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.StringUtils;
import com.rbi.security.web.DAO.CompanyPersonnelDAO;
import com.rbi.security.web.DAO.OrganizationDAO;
import com.rbi.security.web.service.CompanyPersonnelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CompanyPersonnelServiceImpl implements CompanyPersonnelService {

    private final static Logger logger = LoggerFactory.getLogger(CompanyPersonnelServiceImpl.class);

    @Autowired(required = false)
    OrganizationDAO organizationDAO;

    @Autowired(required = false)
    CompanyPersonnelDAO companyPersonnelDAO;

    @Override
    public Map<String, Object> excelImport(MultipartFile file) {
        String currentTime = LocalDateUtils.localDateTimeFormat(LocalDateTime.now(),LocalDateUtils.FORMAT_PATTERN);
        String columns[] = {"serialNumber","employeeNumber","organizationName","factory","workshop","team",
                "name","gender","nation","maritalStatus", "idCardNo","dateOfBirth","degreeOfEducation",
                "position","jobNature","workType","entryTime","remarks"};
        List<Map<String,String>> mapList = ExcelPOI.getData(file,columns);
        logger.info("读取旧账单数据成功！数据量：{}",mapList.size());
        Random random = new Random();
        int ends = random.nextInt(99);
        String importCode = String.valueOf(new Date().getTime())+ends;
        List<ImportCompanyPersonnelLogDTO> importCompanyPersonnelLogDTOS = new ArrayList<>();
        //记录导入成功条数
        int count = 0;
        for (int i = 0;i<mapList.size();i++) {
            try {
                Map<String,String> map = mapList.get(i);
                String employeeNumber = map.get("employeeNumber");
                String organizationName = map.get("organizationName");
                String factoryName = map.get("factoryName");
                String workshopName = map.get("workshopName");
                String teamName = map.get("teamName");
                String name = map.get("name");
                String gender = map.get("gender");
                String nation = map.get("nation");
                String maritalStatus = map.get("maritalStatus");
                String idCardNo = map.get("idCardNo");
                String dateOfBirth = map.get("dateOfBirth");
                String degreeOfEducation = map.get("degreeOfEducation");
                String position = map.get("position");
                String jobNature = map.get("jobNature");
                String workType = map.get("workType");
                String entryTime = map.get("entryTime");
                String remarks = map.get("remarks");

                Integer organizationId;
                List<SysOrganization> organizationList = organizationDAO.queryOrganizationInfoByOrganizationName(organizationName);
                if (1 != organizationList.size()){
                    ImportCompanyPersonnelLogDTO importCompanyPersonnelLogDTO = new ImportCompanyPersonnelLogDTO();
                    importCompanyPersonnelLogDTO.setCode(importCode);
                    importCompanyPersonnelLogDTO.setResult("导入失败");
                    importCompanyPersonnelLogDTO.setEmployeeNumber(employeeNumber);
                    importCompanyPersonnelLogDTO.setName(name);
                    importCompanyPersonnelLogDTO.setIdt(currentTime);
                    importCompanyPersonnelLogDTO.setRemarks("第" + (i + 2)+"行，"+",导入失败！原因：匹配单位失败！");
                    importCompanyPersonnelLogDTOS.add(importCompanyPersonnelLogDTO);
                    continue;
                }
                organizationId = organizationList.get(0).getId();
                if (StringUtils.isNotBlank(factoryName)){
                    SysOrganization organizationDOFactory = organizationDAO.queryOrganizationInfoByOrganizationNameAndParentId(factoryName,organizationId);
                    if (null == organizationDOFactory){
                        ImportCompanyPersonnelLogDTO importCompanyPersonnelLogDTO = new ImportCompanyPersonnelLogDTO();
                        importCompanyPersonnelLogDTO.setCode(importCode);
                        importCompanyPersonnelLogDTO.setResult("导入失败");
                        importCompanyPersonnelLogDTO.setEmployeeNumber(employeeNumber);
                        importCompanyPersonnelLogDTO.setName(name);
                        importCompanyPersonnelLogDTO.setIdt(currentTime);
                        importCompanyPersonnelLogDTO.setRemarks("第" + (i + 2)+"行，"+",导入失败！原因：匹配厂（矿）失败！");
                        importCompanyPersonnelLogDTOS.add(importCompanyPersonnelLogDTO);
                        continue;
                    }
                    organizationId = organizationDOFactory.getId();
                }

                if (StringUtils.isNotBlank(workshopName)){
                    SysOrganization organizationDOWorkshop = organizationDAO.queryOrganizationInfoByOrganizationNameAndParentId(workshopName,organizationId);
                    if (null == organizationDOWorkshop){
                        ImportCompanyPersonnelLogDTO importCompanyPersonnelLogDTO = new ImportCompanyPersonnelLogDTO();
                        importCompanyPersonnelLogDTO.setCode(importCode);
                        importCompanyPersonnelLogDTO.setResult("导入失败");
                        importCompanyPersonnelLogDTO.setEmployeeNumber(employeeNumber);
                        importCompanyPersonnelLogDTO.setName(name);
                        importCompanyPersonnelLogDTO.setIdt(currentTime);
                        importCompanyPersonnelLogDTO.setRemarks("第" + (i + 2)+"行，"+",导入失败！原因：匹配车间失败！");
                        importCompanyPersonnelLogDTOS.add(importCompanyPersonnelLogDTO);
                        continue;
                    }
                    organizationId = organizationDOWorkshop.getId();
                }

                if (StringUtils.isNotBlank(teamName)){
                    SysOrganization organizationDOTeam = organizationDAO.queryOrganizationInfoByOrganizationNameAndParentId(teamName,organizationId);
                    if (null == organizationDOTeam){
                        ImportCompanyPersonnelLogDTO importCompanyPersonnelLogDTO = new ImportCompanyPersonnelLogDTO();
                        importCompanyPersonnelLogDTO.setCode(importCode);
                        importCompanyPersonnelLogDTO.setResult("导入失败");
                        importCompanyPersonnelLogDTO.setEmployeeNumber(employeeNumber);
                        importCompanyPersonnelLogDTO.setName(name);
                        importCompanyPersonnelLogDTO.setIdt(currentTime);
                        importCompanyPersonnelLogDTO.setRemarks("第" + (i + 2)+"行，"+",导入失败！原因：匹配车间失败！");
                        importCompanyPersonnelLogDTOS.add(importCompanyPersonnelLogDTO);
                        continue;
                    }
                    organizationId = organizationDOTeam.getId();
                }
                //效验工号和身份证号
                if (0<companyPersonnelDAO.queryCountByEmployeeNumber(employeeNumber)){
                    ImportCompanyPersonnelLogDTO importCompanyPersonnelLogDTO = new ImportCompanyPersonnelLogDTO();
                    importCompanyPersonnelLogDTO.setCode(importCode);
                    importCompanyPersonnelLogDTO.setResult("导入失败");
                    importCompanyPersonnelLogDTO.setEmployeeNumber(employeeNumber);
                    importCompanyPersonnelLogDTO.setName(name);
                    importCompanyPersonnelLogDTO.setIdt(currentTime);
                    importCompanyPersonnelLogDTO.setRemarks("第" + (i + 2)+"行，"+",导入失败！原因：员工号已存在！");
                    importCompanyPersonnelLogDTOS.add(importCompanyPersonnelLogDTO);
                    continue;
                }
                if (0<companyPersonnelDAO.queryCountByIdCardNo(idCardNo)){
                    ImportCompanyPersonnelLogDTO importCompanyPersonnelLogDTO = new ImportCompanyPersonnelLogDTO();
                    importCompanyPersonnelLogDTO.setCode(importCode);
                    importCompanyPersonnelLogDTO.setResult("导入失败");
                    importCompanyPersonnelLogDTO.setEmployeeNumber(employeeNumber);
                    importCompanyPersonnelLogDTO.setName(name);
                    importCompanyPersonnelLogDTO.setIdt(currentTime);
                    importCompanyPersonnelLogDTO.setRemarks("第" + (i + 2)+"行，"+",导入失败！原因：身份证号已存在！");
                    importCompanyPersonnelLogDTOS.add(importCompanyPersonnelLogDTO);
                    continue;
                }
                SysCompanyPersonnel sysCompanyPersonnel = new SysCompanyPersonnel();
                sysCompanyPersonnel.setEmployeeNumber(employeeNumber);
                sysCompanyPersonnel.setOrganizationId(organizationId);
                sysCompanyPersonnel.setName(name);
                sysCompanyPersonnel.setGender(gender);
                sysCompanyPersonnel.setNation(nation);
                sysCompanyPersonnel.setMaritalStatus(maritalStatus);
                sysCompanyPersonnel.setIdCardNo(idCardNo);
                if (StringUtils.isNotBlank(dateOfBirth)){
                    sysCompanyPersonnel.setDateOfBirth((java.sql.Date) new Date(dateOfBirth));
                }
                sysCompanyPersonnel.setDegreeOfEducation(degreeOfEducation);
                sysCompanyPersonnel.setPosition(position);
                sysCompanyPersonnel.setJobNature(jobNature);
                sysCompanyPersonnel.setWorkType(workType);
                if (StringUtils.isNotBlank(entryTime)){
                    sysCompanyPersonnel.setEntryTime((java.sql.Date) new Date(entryTime));
                }
                sysCompanyPersonnel.setRemarks(remarks);
                sysCompanyPersonnel.setIdt(currentTime);
                sysCompanyPersonnel.setUdt(currentTime);
                companyPersonnelDAO.insertCompanyPersonnelInfo(sysCompanyPersonnel);
                count++;
            } catch (Exception e) {
                logger.error("【公司人员信息Service类】导入数据失败，ERROR：{}",e);
                ImportCompanyPersonnelLogDTO importCompanyPersonnelLogDTO = new ImportCompanyPersonnelLogDTO();
                importCompanyPersonnelLogDTO.setCode(importCode);
                importCompanyPersonnelLogDTO.setResult("导入失败");
                importCompanyPersonnelLogDTO.setIdt(currentTime);
                importCompanyPersonnelLogDTO.setRemarks("第" + (i + 2)+"行，"+",导入失败！原因：系统处理失败！");
                importCompanyPersonnelLogDTOS.add(importCompanyPersonnelLogDTO);
                continue;
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("totalNumber",mapList.size()); //导入数据条数
        map.put("realNumber",count); //导入成功条数
        map.put("failNumber", mapList.size()-count);//失败条数
        map.put("importLog",importCompanyPersonnelLogDTOS);
        return map;
    }

    @Override
    public PageData<SysCompanyPersonnel> queryByPage(JSONObject jsonObject) {
        int pageNum = jsonObject.getInteger("pageNo");
        int pageSize = jsonObject.getInteger("pageSize");
        String searchCriteria = "";
        try {
            long organizationId = jsonObject.getLong("organizationId");
            searchCriteria = "INNER JOIN (select id from sys_organization where FIND_IN_SET(id,getOrganizationChildList("+
                    organizationId+"))) AS oi ON sys_company_personnel.organization_id = oi.id";
        } catch (NullPointerException e){
            //不能并行收搜
            String employeeNumber = jsonObject.getString("employeeNumber");
            String name = jsonObject.getString("name");
            String idCardNo = jsonObject.getString("idCardNo");
            String position = jsonObject.getString("position");
            if (StringUtils.isNotBlank(employeeNumber)){
                searchCriteria = "WHERE employee_number LIKE '%"+employeeNumber+"%'";
            }
            if (StringUtils.isNotBlank(name)){
                if (StringUtils.isNotBlank(searchCriteria)){
                    searchCriteria = searchCriteria + " AND name LIKE '%"+name+"%'";
                }else {
                    searchCriteria = "WHERE name LIKE '%"+name+"%'";
                }
            }
            if (StringUtils.isNotBlank(name)){
                if (StringUtils.isNotBlank(searchCriteria)){
                    searchCriteria = searchCriteria + " AND id_card_no LIKE '%"+idCardNo+"%'";
                }else {
                    searchCriteria = "WHERE id_card_no LIKE '%"+idCardNo+"%'";
                }
            }
            if (StringUtils.isNotBlank(name)){
                if (StringUtils.isNotBlank(searchCriteria)){
                    searchCriteria = searchCriteria + " AND position LIKE '%"+position+"%'";
                }else {
                    searchCriteria = "WHERE position LIKE '%"+position+"%'";
                }
            }
        }
        int pageNo = (pageNum-1)*pageSize;
        List<SysCompanyPersonnel> sysCompanyPersonnelList = companyPersonnelDAO.queryDataByPage(searchCriteria,pageNo,pageSize);
        sysCompanyPersonnelList.forEach(sysCompanyPersonnel ->{
            List<SysOrganization> organizationList = organizationDAO.queryAllParentDate(sysCompanyPersonnel.getOrganizationId());
            organizationList.forEach(sysOrganization -> {
                switch (sysOrganization.getLevel()) {
                    case 1: {
                        sysCompanyPersonnel.setCompanyId(sysOrganization.getId());
                        sysCompanyPersonnel.setCompanyName(sysOrganization.getOrganizationName());
                    }break;
                    case 2:{
                        sysCompanyPersonnel.setFactoryId(sysOrganization.getId());
                        sysCompanyPersonnel.setFactoryName(sysOrganization.getOrganizationName());
                    }break;
                    case 3:{
                        sysCompanyPersonnel.setWorkshopId(sysOrganization.getId());
                        sysCompanyPersonnel.setWorkshopName(sysOrganization.getOrganizationName());
                    }break;
                    case 4:{
                        sysCompanyPersonnel.setTeamId(sysOrganization.getId());
                        sysCompanyPersonnel.setTeamName(sysOrganization.getOrganizationName());
                    }break;
                }
            });
        });

        int count = companyPersonnelDAO.queryCountByPage(searchCriteria);
        int totalPage;
        if (count%pageSize==0){
            totalPage = count/pageSize;
        }else {
            totalPage = count/pageSize+1;
        }
        return new PageData<>(pageNum,pageSize,totalPage,count,sysCompanyPersonnelList);
    }

    @Override
    public boolean add(JSONObject jsonObject) {
        String currentTime = LocalDateUtils.localDateTimeFormat(LocalDateTime.now(),LocalDateUtils.FORMAT_PATTERN);
        SysCompanyPersonnel sysCompanyPersonnel = JSONObject.parseObject(jsonObject.toJSONString(), SysCompanyPersonnel.class);
        sysCompanyPersonnel.setIdt(currentTime);
        sysCompanyPersonnel.setUdt(currentTime);
        //效验工号和身份证号
        if (0<companyPersonnelDAO.queryCountByEmployeeNumber(sysCompanyPersonnel.getEmployeeNumber())){
            return false;
        }
        if (0<companyPersonnelDAO.queryCountByIdCardNo(sysCompanyPersonnel.getIdCardNo())){
            return false;
        }
        companyPersonnelDAO.insertCompanyPersonnelInfo(sysCompanyPersonnel);
        return true;
    }

    @Override
    public boolean update(JSONObject jsonObject) {
        String currentTime = LocalDateUtils.localDateTimeFormat(LocalDateTime.now(),LocalDateUtils.FORMAT_PATTERN);
        SysCompanyPersonnel sysCompanyPersonnel = JSONObject.parseObject(jsonObject.toJSONString(), SysCompanyPersonnel.class);
        sysCompanyPersonnel.setUdt(currentTime);
        //效验工号和身份证号
        if (0<companyPersonnelDAO.queryCountByEmployeeNumberAndNotId(sysCompanyPersonnel.getEmployeeNumber(),sysCompanyPersonnel.getId())){
            return false;
        }
        if (0<companyPersonnelDAO.queryCountByIdCardNoAndNotId(sysCompanyPersonnel.getIdCardNo(),sysCompanyPersonnel.getId())){
            return false;
        }
        companyPersonnelDAO.uodate(sysCompanyPersonnel);
        return true;
    }

    @Override
    public void delete(JSONObject jsonObject) {
        List<String> ids = new ArrayList<>(Arrays.asList(jsonObject.getString("ids").split(",")));
        String idList = Joiner.on(",").join(ids).replaceAll("'","");
        companyPersonnelDAO.delete(idList);
    }
}


