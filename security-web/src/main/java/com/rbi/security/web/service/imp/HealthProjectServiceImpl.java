package com.rbi.security.web.service.imp;

import com.rbi.security.entity.web.health.OccHealthProject;
import com.rbi.security.exception.NonExistentException;
import com.rbi.security.exception.RepeatException;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.health.HealthProjectDAO;
import com.rbi.security.web.service.HealthProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName HealthProjectServiceImpl
 * @Description TODO
 * @Author muyizg
 * @Date 2020/6/19 11:49
 * @Version 1.0
 **/
@Service
public class HealthProjectServiceImpl implements HealthProjectService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImp.class);

    @Autowired(required = false)
    HealthProjectDAO healthProjectDAO;

    @Override
    public PageData<OccHealthProject> getPageHealthPro(int pageNo, int pageSize, int startIndex) throws RuntimeException {
        List<OccHealthProject> occHealthProjectsList = null;
        try {
            occHealthProjectsList = healthProjectDAO.getPageHealthPro(startIndex,pageSize);
            int count = healthProjectDAO.getHealthCount();
            int totalPage;
            if (count%pageSize==0){
                totalPage = count/pageSize;
            }else {
                totalPage = count/pageSize+1;
            }
            return new PageData<OccHealthProject>(pageNo,pageSize,totalPage,count,occHealthProjectsList);
        }catch (Exception e){
            logger.error("分页获取职业健康三同时失败,异常{}",e);
            throw new RuntimeException("分页获取职业健康三同时");
        }

    }

    @Override
    public void insertHealthPro(OccHealthProject occHealthProject) throws RuntimeException {
        try {
            if (Objects.isNull(occHealthProject)||occHealthProject.getHealthProjectName().length()<=0){
                throw new NonExistentException("传入occHealthProject对象为空");
            }
            String occHealthProjectNameOne = healthProjectDAO.getOneHealthProByName(occHealthProject.getHealthProjectName());
            if (Objects.isNull(occHealthProjectNameOne)){
                healthProjectDAO.insertHealthPro(occHealthProject);
            }else {
                throw new RepeatException("添加重复");
            }
        }catch (Exception e){
            logger.error("添加职业健康三同时失败",occHealthProject.toString(),e);
            throw new RuntimeException("添加职业健康三同时失败");
        }
    }

    @Override
    public void updateHealthPro(OccHealthProject occHealthProject) throws RuntimeException {
        try{
            String occHealthProjectNameOne = healthProjectDAO.getOneHealthProByName(occHealthProject.getHealthProjectName());
            if ( !Objects.isNull(occHealthProjectNameOne)){
                throw new RepeatException("更新职业健康三同时重复");
            }
            if (healthProjectDAO.getOneHealthProById(occHealthProject.getId()) != null){
                healthProjectDAO.updateHealthPro(occHealthProject);
            }else {
                throw new NonExistentException("不存在职业健康三同时数据");
            }
        }catch (Exception e){
            logger.error("更新职业三同时失败",occHealthProject.toString(),e);
            throw new RuntimeException("更新三同时表失败");
        }
    }

    @Override
    public void deleteHealthPro(String ids) throws RuntimeException {
        try{
            if (Objects.isNull(ids))
            {
                throw new NonExistentException("不存在当前删除数据");
            }
            healthProjectDAO.deleteHealthPro(ids);
        }catch (Exception e){
            logger.error("删除职业健康三同时失败",e);
            throw new RuntimeException("删除职业健康三同时失败");
        }
    }
}
