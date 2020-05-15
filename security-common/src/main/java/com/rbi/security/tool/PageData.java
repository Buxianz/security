package com.rbi.security.tool;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageData<T> {
	private int pageNo; //查询页数
	private int pageSize; //查询条数
	private int totalPage;	//总页数
	private long totalRecord; //总条数
	private List<T> contents = new ArrayList<T>(); //分页数据

	public PageData() {
	}

	public PageData(int pageNo, int pageSize, int totalPage, long totalRecord, List<T> contents) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalRecord = totalRecord;
		this.contents = contents;
	}

	public PageData(Page<T> page) {
		this.pageNo = page.getNumber() + 1;
		this.pageSize = page.getSize();
		this.totalPage = page.getTotalPages();
		this.totalRecord = page.getTotalElements();
		this.contents = page.getContent();
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public List<T> getContents() {
		return contents;
	}

	public void setContents(ArrayList<T> contents) {
		this.contents = contents;
	}

	public void setContents(List<T> contents) {
		this.contents = contents;
	}
	

}
