package com.rbi.security.tool;

public class PagingQuery {
	private int currentPage;//当前页
    private int pageSize;//一页多少条记录
    private int startIndex;//从哪一行开始
    private int endIndex;//从哪一行结束
    public int getCurrentPage() {
        return currentPage;
    }   
    public int getStartIndex() {
        return startIndex;
    }
    public int getEndIndex() {
        return endIndex;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    //根据当前所在页数和每页显示记录数计算出startIndex和endIndex
    public void setStartIndexEndIndex(){
         this.startIndex=(this.getCurrentPage()-1)*this.getPageSize();
         this.endIndex= (this.getCurrentPage()-1)*this.getPageSize()+this.getPageSize();
    }
}
