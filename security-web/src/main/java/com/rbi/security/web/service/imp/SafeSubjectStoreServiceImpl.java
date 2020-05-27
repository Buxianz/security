package com.rbi.security.web.service.imp;

import com.rbi.security.entity.web.safe.SafeSubjectStore;
import com.rbi.security.exception.NonExistentException;
import com.rbi.security.exception.RepeatException;
import com.rbi.security.web.DAO.safe.SafeSubjectStoreDAO;
import com.rbi.security.web.service.SafeSubjectStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SafeSubjectStoreServiceImpl
 * @Description TODO
 * @Author muyizg
 * @Date 2020/5/26 21:25
 * @Version 1.0
 **/
@Service
public class SafeSubjectStoreServiceImpl implements SafeSubjectStoreService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImp.class);

    @Autowired(required = false)
    private SafeSubjectStoreDAO safeSubjectStoreDAO;

    @Override
    public List<SafeSubjectStore> getAllSafeSubjectStore() {
        return safeSubjectStoreDAO.getAllSafeSubjectStore();
    }


    @Override
    public void insertSafeSubjectStore(SafeSubjectStore safeSubjectStore){
        try {
            if(safeSubjectStore.getSubjectStoreName().length() <= 0 || safeSubjectStore.getSubjectStoreName().contains(" ")){
                throw new NonExistentException("输入不符合字符");
            }
            SafeSubjectStore safeSubjectStoreOne = safeSubjectStoreDAO.getSafeSubjectStoreByName(safeSubjectStore.getSubjectStoreName());
            if (safeSubjectStoreOne != null) {
                throw new RepeatException("添加题库名称重复");
            } else {
                safeSubjectStoreDAO.insertSubjectStoreName(safeSubjectStore.getSubjectStoreName());
            }
        }catch (Exception e){
            logger.error("添加题库名称失败",safeSubjectStore.toString(),e);
            throw new RuntimeException("添加题库名称失败");
        }
    }

    @Override
    public void updateSafeSubjectStore(SafeSubjectStore safeSubjectStore) {

        try {
            if(safeSubjectStore.getSubjectStoreName().length() <= 0 || safeSubjectStore.getSubjectStoreName().contains(" ")){
                throw new NonExistentException("题库输入不符合字符");
            }
            if (safeSubjectStoreDAO.getSafeSubjectStoreById(safeSubjectStore.getId()) != null) {
                safeSubjectStoreDAO.updateSubjectStoreNameById(safeSubjectStore);
            }else {
                throw new NonExistentException("不存在当前题库名称");
            }
        }
        catch (Exception e) {
            logger.error("更新题库名称失败",safeSubjectStore.toString(),e);
            throw new RuntimeException("更新题库名称失败");
        }
    }

    @Override
    public void deleteSafeSubjectStore(int id) {

        try {
            if(safeSubjectStoreDAO.getSafeSubjectStoreById(id).length()<=0){
                throw new NonExistentException("不存在当前删除数据");
            }
            safeSubjectStoreDAO.deleteSubjectStoreNameById(id);
        } catch (Exception e) {
            logger.error("删除题库名称失败",e);
            throw new RuntimeException("删除题库名称失败");
        }

    }




}
