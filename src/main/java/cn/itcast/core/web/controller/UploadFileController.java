package cn.itcast.core.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.itcast.common.utils.Result;
import cn.itcast.core.service.UploadFileService;

@Controller
public class UploadFileController {
	private Logger logger = LoggerFactory.getLogger(UploadFileController.class);
	@Autowired
	private UploadFileService service;
	
	@ResponseBody
	@RequestMapping(value="/UploadFile", method = RequestMethod.POST)
	public JSONObject DeleteUser(@RequestParam MultipartFile[] myfiles,HttpServletRequest request,HttpServletResponse response, HttpSession session, Model model){
		JSONObject jsonObject = null;
		Result result =new Result();
		boolean flag = service.insert(request, session, response, myfiles);
		if (flag == true) {
			result =new Result(1,"上传成功");
		} else {
			result =new Result(0,"上传失败");
		}
        jsonObject = JSONObject.fromObject(result);
		return jsonObject;
		
	}
	
	@RequestMapping(value="/uploadFileImg")
	public String uploadFileImg(){
		
		return "quartzs/uploadFile";
		
	}
}
