package com.rbi.security.web.service;

import com.rbi.security.entity.web.safe.specialtype.PagingSpecialTraining;
import com.rbi.security.entity.web.safe.specialtype.SafeSpecialTrainingFiles;
import com.rbi.security.tool.PageData;

public interface TrainingFileManagementService {
    void updateSpecialTraining(SafeSpecialTrainingFiles safeSpecialTrainingFiles) throws RuntimeException;
    void insertSpecialTraining(SafeSpecialTrainingFiles safeSpecialTrainingFiles) throws RuntimeException;
    PageData<PagingSpecialTraining> pagingSpecialTraining(int pageNo, int pageSize , int startIndex) throws RuntimeException;
}
