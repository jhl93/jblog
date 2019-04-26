package com.javaex.vo;

import java.sql.Date;

public class CategoryVo {

	private int cateNo;
	private String id;
	private String cateName;
	private String description;
	private Date regDate;
	private int postCnt;

	public CategoryVo() {
	}

	public CategoryVo(int cateNo, String id, String cateName, String description, Date regDate) {
		this.cateNo = cateNo;
		this.id = id;
		this.cateName = cateName;
		this.description = description;
		this.regDate = regDate;
	}

	public CategoryVo(String id, String cateName, String description, Date regDate) {
		this.id = id;
		this.cateName = cateName;
		this.description = description;
		this.regDate = regDate;
	}

	public CategoryVo(int cateNo, String id, String cateName, String description, Date regDate, int postCnt) {
		this.cateNo = cateNo;
		this.id = id;
		this.cateName = cateName;
		this.description = description;
		this.regDate = regDate;
		this.postCnt = postCnt;
	}

	public int getCateNo() {
		return cateNo;
	}

	public void setCateNo(int cateNo) {
		this.cateNo = cateNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getPostCnt() {
		return postCnt;
	}

	public void setPostCnt(int postCnt) {
		this.postCnt = postCnt;
	}

	@Override
	public String toString() {
		return "CategoryVo [cateNo=" + cateNo + ", id=" + id + ", cateName=" + cateName + ", description=" + description
				+ ", regDate=" + regDate + ", postCnt=" + postCnt + "]";
	}

}
