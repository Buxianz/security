package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.doubleduty.DoubleDutyEvaluation;
import com.rbi.security.entity.web.doubleduty.DoubleDutyEvaluationContentDTO;
import com.rbi.security.entity.web.doubleduty.DoubleDutyTemplate;
import com.rbi.security.entity.web.doubleduty.DoubleDutyTemplateContent;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.doubleduty.DoubleDutyFieDAO;
import com.rbi.security.web.DAO.doubleduty.DoubleDutyTemplateDAO;
import com.rbi.security.web.service.DoubleDutyFileService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: DoubleDutyFileServiceImp
 * @USER: "谢青"
 * @DATE: 2020/7/21
 * @TIME: 11:46
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 21
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 11
 * @MINUTE: 46
 * @PROJECT_NAME: security
 **/
@Service
public class DoubleDutyFileServiceImp implements DoubleDutyFileService {
    @Autowired
    DoubleDutyFieDAO doubleDutyFieDAO;

    @Override
    public PageData findByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<DoubleDutyEvaluation> doubleDutyEvaluations = doubleDutyFieDAO.findByPage(pageNo2,pageSize);
        int totalPage = 0;
        int count = doubleDutyFieDAO.findByPageNum();
        for (int i=0;i<doubleDutyEvaluations.size();i++){
            List<DoubleDutyEvaluationContentDTO> doubleDutyEvaluationContentDTOS = doubleDutyFieDAO.findEvaluationContentById(doubleDutyEvaluations.get(i).getId());
            doubleDutyEvaluations.get(i).setDoubleDutyEvaluationContentDTOS(doubleDutyEvaluationContentDTOS);
        }
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, doubleDutyEvaluations);
    }
}
