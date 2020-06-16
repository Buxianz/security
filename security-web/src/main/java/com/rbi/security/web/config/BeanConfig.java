package com.rbi.security.web.config;

import com.rbi.security.web.service.util.ImportSpecialTrainingsMethed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @PACKAGE_NAME: com.rbi.security.web.config
 * @NAME: BeanConfig
 * @USER: "吴松达"
 * @DATE: 2020/6/15
 * @TIME: 16:17
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 15
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 16
 * @MINUTE: 17
 * @PROJECT_NAME: security
 **/
@Configuration
public class BeanConfig {
    @Bean
    public ImportSpecialTrainingsMethed createdImportSpecialTrainingsMethed(){
        return new ImportSpecialTrainingsMethed();
    }
}
