package com.rbi.security.web.service.imp;

import com.rbi.security.entity.web.PlatformSettings;
import com.rbi.security.web.DAO.safe.PlatformSettingsDAO;
import com.rbi.security.web.service.PlatformSettingsService;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: PlatformSettingsServiceImp
 * @USER: "吴松达"
 * @DATE: 2020/6/28
 * @TIME: 10:25
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 28
 * @DAY_NAME_SHORT: 周日
 * @DAY_NAME_FULL: 星期日
 * @HOUR: 10
 * @MINUTE: 25
 * @PROJECT_NAME: security
 **/
@Service
public class PlatformSettingsServiceImp implements PlatformSettingsService {
    private static final Logger logger = LoggerFactory.getLogger(PlatformSettingsServiceImp.class);
    @Autowired
    PlatformSettingsDAO platformSettingsDAO;
    public PlatformSettings getSpecialDaySet() throws RuntimeException{
        PlatformSettings platformSettings=null;
        try{
            platformSettings=platformSettingsDAO.getSpecialDaySet();
        }catch (Exception e){
            logger.error("获取特种人员复审提前通知时间设置失败，异常信息为{}", e);
            throw new RuntimeException(e.getMessage());
        }
        return platformSettings;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateSpecialDaySet(PlatformSettings platformSettings) throws RuntimeException{
         try{
             platformSettingsDAO.updateValues(platformSettings);
         }catch (Exception e){
             logger.error("更新特种人员复审提前通知时间设置失败，异常信息为{}", e);
         }
    }
}
