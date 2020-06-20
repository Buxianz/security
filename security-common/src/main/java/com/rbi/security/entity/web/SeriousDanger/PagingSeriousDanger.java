package com.rbi.security.entity.web.SeriousDanger;

import com.rbi.security.entity.web.entity.SeriousDanger;
import com.rbi.security.entity.web.entity.SeriousDangerPicture;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.SeriousDanger
 * @NAME: PagingSeriousDanger
 * @USER: "林新元"
 * @DATE: 2020/6/17
 * @TIME: 17:50
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 六月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 17
 * @DAY_NAME_SHORT: 星期三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 17
 * @MINUTE: 50
 * @PROJECT_NAME: security
 **/
@Data
public class PagingSeriousDanger {
    SeriousDanger seriousDanger;
    List<SeriousDangerPicture> seriousDangerPictureList=new ArrayList<>();
}
