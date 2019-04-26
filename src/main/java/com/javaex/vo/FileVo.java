package com.javaex.vo;

public class FileVo {

	private int no;
	private String orgName;
	private String exName;
	private String saveName;
	private String filePath;
	private long fileSize;

	public FileVo() {
	}

	public FileVo(int no, String orgName, String exName, String saveName, String filePath, long fileSize) {
		this.no = no;
		this.orgName = orgName;
		this.exName = exName;
		this.saveName = saveName;
		this.filePath = filePath;
		this.fileSize = fileSize;
	}

	public FileVo(String orgName, String exName, String saveName, String filePath, long fileSize) {
		this.orgName = orgName;
		this.exName = exName;
		this.saveName = saveName;
		this.filePath = filePath;
		this.fileSize = fileSize;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getExName() {
		return exName;
	}

	public void setExName(String exName) {
		this.exName = exName;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "FileVo [no=" + no + ", orgName=" + orgName + ", exName=" + exName + ", saveName=" + saveName
				+ ", filePath=" + filePath + ", fileSize=" + fileSize + "]";
	}

}
