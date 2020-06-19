package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONArray;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrain;
import com.rbi.security.entity.web.safe.specialtype.PagingSpecialTraining;
import com.rbi.security.entity.web.safe.specialtype.SafeSpecialTrainingFiles;
import com.rbi.security.tool.PageData;
import org.springframework.web.multipart.MultipartFile;

public interface TrainingFileManagementService {
    /**特岗人员台账***/
    void updateSpecialTraining(SafeSpecialTrainingFiles safeSpecialTrainingFiles) throws RuntimeException;
    void insertSpecialTraining(SafeSpecialTrainingFiles safeSpecialTrainingFiles) throws RuntimeException;
    PageData<PagingSpecialTraining> pagingSpecialTraining(int pageNo, int pageSize , int startIndex) throws RuntimeException;
    PagingSpecialTraining getSpecialTrainingById(int id) throws RuntimeException;
    void importSpecialTrainings(MultipartFile multipartFiles) throws RuntimeException;
    //*******安全管理台账******/
    void importAdministratorTrains(MultipartFile multipartFiles)throws RuntimeException;
    String insertAdministratorTrain( SafeAdministratorTrain safeAdministratorTrain) throws RuntimeException;

    void deleteAdministratorTrain(Integer id);

    void updateAdministratorTrain(SafeAdministratorTrain safeAdministratorTrain);

    PageData findAdministratorTrainByPage(int pageNo, int pageSize);

    PageData findByCondition(String condition, String value, int pageNo, int pageSize);
}
