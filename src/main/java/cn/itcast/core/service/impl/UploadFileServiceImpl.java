package cn.itcast.core.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.itcast.core.bean.UploadFile;
import cn.itcast.core.bean.User;
import cn.itcast.core.dao.UploadFileDao;
import cn.itcast.core.service.UploadFileService;

@Service("uploadfileService")
@Transactional
public class UploadFileServiceImpl implements UploadFileService {
	private Logger logger = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	@Autowired
	public UploadFileDao dao;

	@Override
	public boolean insert(HttpServletRequest request, HttpSession session, HttpServletResponse response, MultipartFile[] myfiles) {
		// TODO Auto-generated method stub
		User user = (User)session.getAttribute("user");
		String fileAppealID = user.getUser_name();
		String fileType = "图片";
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String DateTime = sdf.format(date);
		String curYear=DateTime.substring(0,4);
		String curMonth=DateTime.substring(5,7);
		String filePath=curYear+"/"+curMonth+"/"+fileAppealID;
		String fullPath=request.getSession().getServletContext().getRealPath("/image");
		//读取文件夹路径
		File f = new File(fullPath);
		//判断文件夹路径是否存在，不存在创建
		if(!f.exists())
		{
			f.mkdirs();
		}
		List<Map<String,String>> list = new ArrayList<Map<String,String>>(); 
		//文件名
		String originalFilename = null;
		for(MultipartFile myfile : myfiles){  
        	Map<String,String> map = new HashMap<String,String>(); 
        	    //取得文件名
	            originalFilename = myfile.getOriginalFilename();
	            //取得文件后缀
	            String extension =FilenameUtils.getExtension(originalFilename);
	            //判断文件类型
	            if("jpg".equalsIgnoreCase(extension)||"png".equalsIgnoreCase(extension)){
		            originalFilename=user.getUser_name()+"_"+System.currentTimeMillis()+"."+FilenameUtils.getExtension(originalFilename);
		            try {  
		            	myfile.transferTo(new File(fullPath, originalFilename));
		            	//保存文件路径
		            	//creditFileService.insertFile(File.separator+"upload"+File.separator+day+File.separator+originalFilename, companyId, creditId);
		            	map.put("pathUrl","/image/"+originalFilename);
		            	list.add(map);
		            	
		            	//将文件信息存入数据库
		            	UploadFile uf = new UploadFile();
						   uf.setAppealID(fileAppealID);
						   uf.setFileName(originalFilename);
						   uf.setFileExt(extension);
						   //uf.setFileSize(strFileSize);
						   uf.setRealPath("/image/"+originalFilename);
						   uf.setFileType(fileType);
						   uf.setDeptID(user.getDeptid());
						   uf.setDeptName(user.getDeptidname());
						   uf.setUserID(user.getUser_name());
						   uf.setUserName(user.getName());
						   uf.setCreateTime(DateTime);
						   String IsShow = dao.FindFile(fileAppealID);
						   boolean flag = false;
						   if (StringUtils.isNotBlank(IsShow)) {
							   flag = dao.updateFile(uf);
							   logger.info("修改成功");
							   if (flag == false) {
								   logger.info("修改失败");
							   }
						   }else{
							   flag = dao.insert(uf);
							   logger.info("添加成功");
							   if (flag == false) {
								   logger.info("添加失败");
							   }
						   }
						   
		                logger.info("load success " + request.getContextPath()+"image"+originalFilename);
		                logger.info("leaving upload!");
		            }catch (Exception e) {
		            	logger.info("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
		                e.printStackTrace();  
		                logger.info("文件上传失败，请重试！！");
		                return (Boolean) null;  
		    		}
	            }else{
	            	   
	                logger.info("load success 只支持jpg,png格式");
	            } 
        }				 
						
						   
						
		return false;
	}

	@Override
	public String ImageUser(String AppealID) {
		// TODO Auto-generated method stub
		String Image = dao.ImageUser(AppealID);
		return Image;
	}

}
