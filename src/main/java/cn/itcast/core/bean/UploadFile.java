package cn.itcast.core.bean;

public class UploadFile {
	private String KeyID;  //数据编号
	private String AppealID;  //事项表ID
	private String FileName;  //文件名称
	private String FileExt;  //文件扩展名
	private String FileSize;  //文件大小
	private String RealPath;  //存储路径
	private String FileType;  //文件类型
	private String DeptID;  //登记部门名称
	private String DeptName;  //登记部门编号   
	private String UserID;  //登记人ID
	private String UserName;  //登记人名称
	private String CreateTime;  //记录时间   存贮到年月日小时秒分
	public String getKeyID() {
		return KeyID;
	}
	public void setKeyID(String keyID) {
		KeyID = keyID;
	}
	public String getAppealID() {
		return AppealID;
	}
	public void setAppealID(String appealID) {
		AppealID = appealID;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getFileExt() {
		return FileExt;
	}
	public void setFileExt(String fileExt) {
		FileExt = fileExt;
	}
	public String getFileSize() {
		return FileSize;
	}
	public void setFileSize(String fileSize) {
		FileSize = fileSize;
	}
	public String getRealPath() {
		return RealPath;
	}
	public void setRealPath(String realPath) {
		RealPath = realPath;
	}
	public String getFileType() {
		return FileType;
	}
	public void setFileType(String fileType) {
		FileType = fileType;
	}
	public String getDeptID() {
		return DeptID;
	}
	public void setDeptID(String deptID) {
		DeptID = deptID;
	}
	public String getDeptName() {
		return DeptName;
	}
	public void setDeptName(String deptName) {
		DeptName = deptName;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

}
