package cn.itcast.core.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.common.utils.Page;
import cn.itcast.common.utils.Result;
import cn.itcast.core.bean.User;
import cn.itcast.core.service.IUserService;
import cn.itcast.core.service.UploadFileService;

@Controller
public class UserController {
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;
	@Autowired
	private UploadFileService fileService;
	/**
	 * 用户管理
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/listuser")
	public String ListUser(@RequestParam(defaultValue="1")Integer pageNo, @RequestParam(defaultValue="10")Integer pageSize,HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model) throws IOException{		
		String Name = request.getParameter("Name");
		String DeptId = request.getParameter("deptid");
		Page<User> pagelist = userService.selectUserList(pageNo, pageSize, Name, DeptId);		
		//JSONObject jsonObject = JSONObject.fromObject(pagelist.getRows());
		JSONArray json = JSONArray.fromObject(pagelist.getRows());
		model.addAttribute("list", pagelist.getRows());
		model.addAttribute("json",  json.toString());
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("Name", Name);
		model.addAttribute("DeptId", DeptId);
		model.addAttribute("totalcount", pagelist.getTotal());
		return "user/listuser";
		
	}
	@ResponseBody
	@RequestMapping(value="/selectuser")
	public JSONObject SelectUser(HttpSession session, Model model) throws IOException{
		JSONObject jsonObject = null;
		String Name = (String) session.getAttribute("name");
		String Persontype = (String) session.getAttribute("persontype");
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", Name);
        map.put("persontype", Persontype);
        jsonObject = JSONObject.fromObject(map);
		return jsonObject;
		
	}
	/**
	 * 删除用户信息
	 * @param chkvalue
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/Deleteuser")
	public JSONObject DeleteUser(int[] chkvalue,HttpServletRequest request,HttpServletResponse response, Model model){
		JSONObject jsonObject = null;
		Result result =new Result();
		boolean flag = userService.DeleteUser(chkvalue);
		if (flag == true) {
			result =new Result(1,"删除成功");
		} else {
			result =new Result(0,"删除失败");
		}
        jsonObject = JSONObject.fromObject(result);
		return jsonObject;
		
	}
	/**
	 * 增加用户
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/Insertuser")
	public JSONObject InsertUser(HttpServletRequest request,HttpServletResponse response, Model model){
		JSONObject jsonObject = null;
		Result result =new Result();
		boolean flag = userService.insert(request);
		if (flag == true) {
			result =new Result(1,"添加成功");
		} else {
			result =new Result(0,"添加失败");
		}
        jsonObject = JSONObject.fromObject(result);
		return jsonObject;
		
	}
	/**
	 * 修改用户信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/Updateuser")
	public JSONObject UpdateUser(HttpServletRequest request,HttpServletResponse response, Model model){
		JSONObject jsonObject = null;
		Result result =new Result();
		boolean flag = userService.updateByPrimaryKey(request);
		if (flag == true) {
			result =new Result(1,"修改成功");
		} else {
			result =new Result(0,"修改失败");
		}
        jsonObject = JSONObject.fromObject(result);
		return jsonObject;
		
	}
	/**
	 * 查询主页头像图片
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ImageUser")
	public JSONObject ImageUser(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		JSONObject jsonObject = null;
		User user = (User)session.getAttribute("user");
		String Image = fileService.ImageUser(user.getUser_name());
		if (Image == null || Image.compareTo("") == 0) {
			Image = "/image/banner.jpg";
		}
		String Url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath()+Image;
		Map<String, String> map = new HashMap<String, String>();
		map.put("url", Url);
        jsonObject = JSONObject.fromObject(map);
		return jsonObject;
		
	}
}
