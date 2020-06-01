package com.rbi.security.web.service.imp;

import com.rbi.security.entity.web.safe.testpaper.SafeTestPaper;
import com.rbi.security.entity.web.safe.testpaper.SafeTestQuestionOptions;
import com.rbi.security.entity.web.safe.testpaper.SafeTestQuestions;
import com.rbi.security.web.service.TestPaperService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: TestQuestionsServiceImp
 * @USER: "吴松达"
 * @DATE: 2020/5/31
 * @TIME: 22:04
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 31
 * @DAY_NAME_SHORT: 周日
 * @DAY_NAME_FULL: 星期日
 * @HOUR: 22
 * @MINUTE: 04
 * @PROJECT_NAME: security
 **/
@Service
public class TestQaperServiceImp implements TestPaperService {
    /**
     * 添加试卷
     */
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public SafeTestPaper insertTestQaper(SafeTestPaper safeTestPaper) throws RuntimeException{
       return safeTestPaper;
    }
    /**
     * 修改试卷  目前不开发
     */
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateTestQuestions(SafeTestPaper safeTestPaper) throws RuntimeException{

    }

    /**
     * 添加试卷的题目
     */
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public List<SafeTestQuestions> insertTestQuestions(List<SafeTestQuestions> safeTestQuestionsList) throws RuntimeException{


       return safeTestQuestionsList;
    }
    /**
     * 添加试卷题目的选项
     */
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void insertTestQuestionOptions(List<SafeTestQuestionOptions> safeTestQuestionOptionsList) throws RuntimeException{

    }
    /**
     * 分页查看试卷 目前不开发
     */
}
