package cn.itcast.core.dao;

import cn.itcast.core.bean.UploadFile;

public interface UploadFileDao {
	boolean insert(UploadFile uf);
	String FindFile(String AppealID);	
	boolean updateFile(UploadFile uf);
	String ImageUser(String AppealID);
}
