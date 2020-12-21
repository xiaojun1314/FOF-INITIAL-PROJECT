package com.fof.common.util;

import com.fof.common.entity.BaseNoIdEntity;

public class Page extends BaseNoIdEntity {
    private int offset = 0;
    private int limit  = -1;
    private String sortField;
    private String sortType;
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
    
}
