package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorReviewDTO;
import com.rbi.security.entity.web.safe.content.SafeContentCategory;
import com.rbi.security.entity.web.safe.content.SafeTrainingMaterials;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.safe.SafeContentCategoryDAO;
import com.rbi.security.web.service.SafeContentCategoryService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: SafeContentCategoryServiceImp
 * @USER: "谢青"
 * @DATE: 2020/6/17
 * @TIME: 14:22
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 17
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 14
 * @MINUTE: 22
 * @PROJECT_NAME: security
 **/
@Service
public class SafeContentCategoryServiceImp implements SafeContentCategoryService {
    @Autowired
    SafeContentCategoryDAO safeContentCategoryDAO;

    @Override
    public PageData findByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<SafeContentCategory> safeContentCategories = safeContentCategoryDAO.findByPage(pageNo2,pageSize);
        int totalPage = 0;
        int count = safeContentCategoryDAO.findByPageNum();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, safeContentCategories);
    }

    @Override
    public void add(SafeContentCategory safeContentCategory) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        safeContentCategory.setOperatingStaff(personnelId);
        String idt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        safeContentCategory.setIdt(idt);
        safeContentCategoryDAO.add(safeContentCategory);
    }

    @Override
    public void update(SafeContentCategory safeContentCategory) {
        String udt = DateUtil.date(DateUtil.FORMAT_PATTERN);
        safeContentCategory.setUdt(udt);
        safeContentCategoryDAO.update(safeContentCategory);
    }

    @Override
    public String deleteById(Integer id) {
        int num = safeContentCategoryDAO.findUseNum(id);
        if (num == 0){
            safeContentCategoryDAO.deleteById(id);
            return "1000";
        }else {
            return "此内容分类已被使用，不能删除！若要删除，需先删除培训内容关联的项目";
        }
    }

    @Override
    public PageData findByContentCategoryName(int pageNo, int pageSize, String contentCategoryName) {
        String name = "'%"+contentCategoryName+"%'";
        int pageNo2 = pageSize * (pageNo - 1);
        List<SafeContentCategory> safeContentCategories = safeContentCategoryDAO.findByContentCategoryName(pageNo2,pageSize,name);
        int totalPage = 0;
        int count = safeContentCategoryDAO.findByContentCategoryNameNum(name);
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, safeContentCategories);
    }
}
