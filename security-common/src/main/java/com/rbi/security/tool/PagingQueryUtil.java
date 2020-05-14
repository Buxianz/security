package com.rbi.security.tool;



public class PagingQueryUtil {
    public static <T> PagingQueryData<T>  initPage(PagingQueryData<T> page, int pageCount,
                                        PagingQuery pagingQuery) {
        page.setTotalRecord(pageCount);//设置总记录数
        page.setCurrentPage(pagingQuery.getCurrentPage());//设置当前页
        page.setPageSize(pagingQuery.getPageSize());//设置每页显示行数
        page.setTotalPage();//设置总页数
        page.setStartRecord();//设置起始记录数
        pagingQuery.setStartIndexEndIndex();//设置开始行和结束行
        return page;
    }
}
