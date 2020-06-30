package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SysPermission;
import com.rbi.security.entity.web.permission.PagingPermission;
import com.rbi.security.entity.web.role.PagingRole;
import com.rbi.security.entity.web.safe.HandlePersonalMistakes;
import com.rbi.security.entity.web.safe.PagePersonalMistakes;
import com.rbi.security.entity.web.safe.testpaper.SafeTestQuestionOptions;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.safe.SafePersonalMistakesDAO;
import com.rbi.security.web.DAO.safe.SafeTestQaperDAO;
import com.rbi.security.web.service.SafePersonalMistakesService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: SafePersonalMistakesServiceImp
 * @USER: "吴松达"
 * @DATE: 2020/6/30
 * @TIME: 9:50
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 30
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 09
 * @MINUTE: 50
 * @PROJECT_NAME: security
 **/
@Service
public class SafePersonalMistakesServiceImp implements SafePersonalMistakesService {
    private static final Logger logger = LoggerFactory.getLogger(SafePersonalMistakesServiceImp.class);
    @Autowired
    SafePersonalMistakesDAO SafePersonalMistakesDAO;
    @Autowired
    SafeTestQaperDAO safeTestQaperDAO;
    /**
     * 分页获取自身错题
     */
    public PageData findPersonalMistakesByPage(int pageNo,int pageSize ,int startIndex) throws RuntimeException{
        List<PagePersonalMistakes> pagePersonalMistakesList=null;

        try {
            List<SafeTestQuestionOptions> safeTestQuestionOptionsList = null;
            Subject subject = SecurityUtils.getSubject();
            pagePersonalMistakesList=SafePersonalMistakesDAO.getPagePersonalMistakes(((AuthenticationUserDTO)subject.getPrincipal()).getCompanyPersonnelId(),startIndex,pageSize);
            List<Integer> testQuestionsIds=new LinkedList<>();
            for (int i = 0; i < pagePersonalMistakesList.size(); i++) {
                testQuestionsIds.add(pagePersonalMistakesList.get(i).getSubjectId());
            }
            safeTestQuestionOptionsList = safeTestQaperDAO.getTestQuestionOptions(testQuestionsIds);
            /**
             * 整合题目与选项
             */
            for (int i = 0; i < pagePersonalMistakesList.size(); i++) {
                for (int j = 0; j < safeTestQuestionOptionsList.size(); j++) {
                    if (pagePersonalMistakesList.get(i).getSubjectId().intValue() == safeTestQuestionOptionsList.get(j).getSubjectId().intValue()) {
                        if (pagePersonalMistakesList.get(i).getSafeTestQuestionOptionsList() == null) {
                            pagePersonalMistakesList.get(i).setSafeTestQuestionOptionsList(new LinkedList<SafeTestQuestionOptions>());
                        }
                        pagePersonalMistakesList.get(i).getSafeTestQuestionOptionsList().add(safeTestQuestionOptionsList.get(j));
                    }
                }
            }
            int count =SafePersonalMistakesDAO.getPagePersonalMistakesCount(((AuthenticationUserDTO)subject.getPrincipal()).getCompanyPersonnelId());
            int totalPage;
            if (count%pageSize==0){
                totalPage = count/pageSize;
            }else {
                totalPage = count/pageSize+1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, pagePersonalMistakesList);
        }catch (Exception e){
            logger.error("分页获取自身错题信息失败，异常为{}",  e);
            throw new RuntimeException("分页获取自身错题信息失败");
        }
    }
    /***
     * 处理错题记录
     */
    public void handlePersonalMistakes(List<HandlePersonalMistakes> handlePersonalMistakes) throws RuntimeException{
          try{
              List<Integer> ids=new LinkedList<>();
              for (int i=0;i<handlePersonalMistakes.size();i++){
                  if(handlePersonalMistakes.get(i).getRightKey().equals(handlePersonalMistakes.get(i).getAnswerResults())){
                      ids.add(handlePersonalMistakes.get(i).getId());
                  }
              }
              if(ids.size()!=0)
                SafePersonalMistakesDAO.deletes(ids);
          }catch (Exception e){
              logger.error("批量删除自身错题信息失败，异常为{}",  e);
              throw new RuntimeException("批量删除自身错题信息失败");
          }
    }
}
