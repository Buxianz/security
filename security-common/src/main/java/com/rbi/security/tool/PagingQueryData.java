package com.rbi.security.tool;


import java.util.List;

public class PagingQueryData<T> {
	    private int currentPage = 1; // 当前页
	    private int pageSize = 20; //每页显示记录数
	    private int startRecord = 1; //起始查询记录
	    private int totalPage = 0; //总页数
	    private int totalRecord = 0; //总记录数

	    private List<T> datas;

	    public  PagingQueryData(){}

	    public  PagingQueryData(int currentPage, int pageSize) {
	        this.currentPage = currentPage;
	        this.pageSize = pageSize;
	        if(this.currentPage <= 0) {
	            this.currentPage = 1;
	        }
	        if(this.pageSize <=0) {
	            this.pageSize = 1;
	        }
	    }

	    public  PagingQueryData(int currentPage, int pageSize, int totalRecord) {
	        this(currentPage, pageSize);
	        this.totalRecord = totalRecord;
	        if(this.totalRecord <=0) {
	            this.totalRecord = 1;
	        }
	    }

	    public int getCurrentPage() {
	        if(currentPage <= 0) {
	            return 1;
	        }
	        return currentPage;
	    }

	    public void setCurrentPage(int currentPage) {
	        this.currentPage = currentPage;
	    }

	    public int getPageSize() {
	        return pageSize;
	    }
	    public void setPageSize(int pageSize) {
	        this.pageSize = pageSize;
	    }
	    public int getTotalRecord() {
	        if(totalRecord < 0) {
	            return 0;
	        }
	        return totalRecord;
	    }
	    public void setTotalRecord(int totalRecord) {
	        this.totalRecord = totalRecord;
	    }
	    public List<T> getDatas() {
	        return datas;
	    }
	    public void setDatas(List<T> datas) {
	        this.datas = datas;
	    }

	    public int getTotalPage() {
	        return totalPage;
	    }
	    public void setTotalPage() {
	        if(this.totalRecord <= 0) {
	        	this.totalRecord = 0;
	        }else{
	        int size =this.totalRecord / this.pageSize;//总条数/每页显示的条数=总页数
	        int mod = this.totalRecord % this.pageSize;//最后一页的条数
	        if(mod != 0) {
	            size++;
	        }
	        this.totalPage = size;
	        }
	    }
	    public int getStartRecord() {
	     
	        return startRecord;
	    }
	    public void setStartRecord() {
	       this.startRecord = (getCurrentPage() - 1) * pageSize+1;
	    }
}
