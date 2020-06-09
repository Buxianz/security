package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.safe.testpaper.SafeTestPaper;
import com.rbi.security.entity.web.safe.testpaper.SafeTestQuestionOptions;
import com.rbi.security.entity.web.safe.testpaper.SafeTestQuestions;
import com.rbi.security.web.DAO.safe.SafeTestQaperDAO;
import com.rbi.security.web.service.TestPaperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final Logger logger = LoggerFactory.getLogger(TestQaperServiceImp.class);
    @Autowired(required = false)
    SafeTestQaperDAO safeTestQaperDAO;
    /**
     * 添加试卷
     */
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public SafeTestPaper insertTestQaper(SafeTestPaper safeTestPaper) throws RuntimeException{
        try {
            safeTestQaperDAO.insertSafeTestPaper(safeTestPaper);
            insertTestQuestions(safeTestPaper.getSafeTestQuestionsList(),safeTestPaper.getId());
            return safeTestPaper;
        }catch (Exception e1) {
            logger.error("添加试卷失败，异常信息为{}", e1);
            e1.printStackTrace();
            throw new RuntimeException(e1.getMessage());
        }
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
    public List<SafeTestQuestions> insertTestQuestions(List<SafeTestQuestions> safeTestQuestionsList,Integer TestPapreId) throws RuntimeException{
        try {
            for (int i = 0; i < safeTestQuestionsList.size(); i++) {
                safeTestQuestionsList.get(i).setTestPapreId(TestPapreId);
                safeTestQaperDAO.insertSafeTestQuestions(safeTestQuestionsList.get(i));
                if(safeTestQuestionsList.get(i).getSubjectType()==1 || safeTestQuestionsList.get(i).getSubjectType()==2)
                insertTestQuestionOptions(safeTestQuestionsList.get(i).getSafeTestQuestionOptionsList(), safeTestQuestionsList.get(i).getId());
            }
            return safeTestQuestionsList;
        }catch (Exception e1) {
            logger.error("添加试题失败，异常信息为{}", e1);
            e1.printStackTrace();
            throw new RuntimeException(e1.getMessage());
        }
    }
    /**
     * 添加试卷题目的选项
     */
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void insertTestQuestionOptions(List<SafeTestQuestionOptions> safeTestQuestionOptionsList,Integer SubjectId) throws RuntimeException{
        try {
            for (int i = 0; i < safeTestQuestionOptionsList.size(); i++) {
                safeTestQuestionOptionsList.get(i).setSubjectId(SubjectId);
                safeTestQaperDAO.insertSafeTestQuestionOptions(safeTestQuestionOptionsList.get(i));
            }
        }catch (Exception e1) {
            logger.error("添加选项失败，异常信息为{}", e1);
            e1.printStackTrace();
            throw new RuntimeException(e1.getMessage());
        }
    }
    /**
     * 分页查看试卷 目前不开发
     */
}
