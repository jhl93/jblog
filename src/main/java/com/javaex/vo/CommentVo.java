package com.javaex.vo;

import java.sql.Date;

public class CommentVo {

	private int cmtNo;
	private int postNo;
	private int userNo;
	private String cmtContent;
	private Date regDate;
	private String userName;

	public CommentVo() {
	}

	public CommentVo(int cateNo, int postNo, int userNo, String cmtContent, Date regDate) {
		this.cmtNo = cateNo;
		this.postNo = postNo;
		this.userNo = userNo;
		this.cmtContent = cmtContent;
		this.regDate = regDate;
	}

	public int getCmtNo() {
		return cmtNo;
	}

	public void setCmtNo(int cmtNo) {
		this.cmtNo = cmtNo;
	}

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getCmtContent() {
		return cmtContent;
	}

	public void setCmtContent(String cmtContent) {
		this.cmtContent = cmtContent;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "CommentsVo [cmtNo=" + cmtNo + ", postNo=" + postNo + ", userNo=" + userNo + ", cmtContent="
				+ cmtContent + ", regDate=" + regDate + "]";
	}

}
