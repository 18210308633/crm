package cn.itcast.core.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import cn.itcast.core.bean.UploadFile;


public interface UploadFileService {
	public boolean insert(HttpServletRequest request, HttpSession session, HttpServletResponse response, MultipartFile[]  myfiles);
	String ImageUser(String AppealID);
}
