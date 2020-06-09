package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorReviewDTO;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrainDTO;
import com.rbi.security.tool.DateUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.safe.SafeAdministratorReviewDAO;
import com.rbi.security.web.service.SafeAdministratorReviewService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: SafeAdministratorReviewServiceImp
 * @USER: "谢青"
 * @DATE: 2020/6/8
 * @TIME: 16:22
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 08
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 16
 * @MINUTE: 22
 * @PROJECT_NAME: security
 **/
@Service
public class SafeAdministratorReviewServiceImp implements SafeAdministratorReviewService {
    @Autowired
    SafeAdministratorReviewDAO safeAdministratorReviewDAO;

    @Override
    public PageData findByPage(int pageNo, int pageSize) {
        int pageNo2 = pageSize * (pageNo - 1);
        List<SafeAdministratorReviewDTO> safeAdministratorReviewDTOS = safeAdministratorReviewDAO.findByPage(pageNo2,pageSize);
        int totalPage = 0;
        int count = safeAdministratorReviewDAO.findByPageNum();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, safeAdministratorReviewDTOS);
    }


    @Override
    public void review(SafeAdministratorReviewDTO safeAdministratorReviewDTO) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        String time = DateUtil.date(DateUtil.FORMAT_PATTERN);


        safeAdministratorReviewDTO.setOperatingStaff(personnelId);
        safeAdministratorReviewDTO.setCompletionStatus(3);
        safeAdministratorReviewDTO.setProcessingTime(time);

        safeAdministratorReviewDAO.updateReview(safeAdministratorReviewDTO);
        safeAdministratorReviewDAO.updateFile(safeAdministratorReviewDTO);
    }

    @Override
    public void cancel(SafeAdministratorReviewDTO safeAdministratorReviewDTO) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        Integer personnelId  =  currentUser.getCompanyPersonnelId();
        String time = DateUtil.date(DateUtil.FORMAT_PATTERN);

        safeAdministratorReviewDTO.setOperatingStaff(personnelId);
        safeAdministratorReviewDTO.setCompletionStatus(2);
        safeAdministratorReviewDTO.setProcessingTime(time);
        safeAdministratorReviewDAO.updateReview(safeAdministratorReviewDTO);
    }
}
