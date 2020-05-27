package com.rbi.security.web.service;

import com.rbi.security.entity.web.safe.specialtype.PagingSpecialReview;
import com.rbi.security.entity.web.safe.specialtype.SafeSpecialReview;
import com.rbi.security.tool.PageData;

public interface SpecialReviewService {
    void handleSpecialReview(SafeSpecialReview safeSpecialReview) throws RuntimeException;
    PageData<PagingSpecialReview> pagingSpecialReview(int pageNo, int pageSize, int startIndex) throws RuntimeException;
}
