package cn.itcast.core.web.controller;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.common.utils.CommonConstant;
import cn.itcast.common.utils.RandomValidateCode;
import cn.itcast.common.utils.Result;
import cn.itcast.common.utils.Utils;
import cn.itcast.core.bean.LoginLog;
import cn.itcast.core.bean.Menu;
import cn.itcast.core.bean.User;
import cn.itcast.core.service.IUserService;
import cn.itcast.core.service.LoginLogService;
@Controller
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);

		@Autowired
		private IUserService userService;
		@Autowired
		private LoginLogService logService;
		/**
		 * 注销和登录
		 * @param request
		 * @param response
		 * @param model
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value="/login")
		public String toLogin(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException{
            //清楚session信息，避免退出系统退回操作
			Enumeration em = request.getSession().getAttributeNames();
			while (em.hasMoreElements()) {
				request.getSession().removeAttribute(em.nextElement().toString());
			}
			System.out.println("进入登陆主页！");
			return "login";
			
		}
		/**
		 * 用户登录验证
		 * @param request
		 * @param response
		 * @param session
		 * @param model
		 * @return
		 * @throws IOException
		 * @throws ServletException
		 */
		@ResponseBody
		@RequestMapping(value="/loginSubmit")
		public JSONObject login(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model) throws IOException, ServletException{
			//获取帐户名
			String username = request.getParameter("username");
			//获取密码
			String password = request.getParameter("password");
			//获取验证码
			String safeCode = request.getParameter("safeCode");
			Result result =new Result();
	        JSONObject jsonObject = null;
			//从session中获取随机数
	        String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
	        String md5Pwd = Utils.md5Pwd(password, username);
	        User u = new User(username, md5Pwd);
			User user = userService.getUser(u);
			Date date = new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String DateTime = sdf.format(date);
			String DoFlag = "";//登录状态
			LoginLog log = new LoginLog();
			log.setOptTime(DateTime);
			log.setOptType("登录");
			log.setOptMode("系统登录");
			log.setOptUserId(username);
			 try {
				 if(user != null) {
					//得到 Subject 及创建用户名/密码身份验证 Token（即用户身份/凭证）
					Subject subject = SecurityUtils.getSubject();
					UsernamePasswordToken token = new UsernamePasswordToken(username,user.getPassword());
					token.setRememberMe(true);
                    if (random.equals(safeCode)) {					    
					    //登录，即身份验证
			        	subject.login(token);
			        	DoFlag = "成功";
			        	log.setOptUserName(user.getPersontype());
			        	log.setOptInfo(username+"：登录成功");
			        	System.out.println("认证结果：" + subject.isAuthenticated());			        				        	
			        	session.setAttribute("username", user.getUser_name());
			        	session.setAttribute("name", user.getName());
			        	session.setAttribute("persontype", user.getPersontype());
			        	session.setAttribute("user", user);
		                model.addAttribute("user", user);		                
		                result =new Result(1,"登陆成功");
			        } else {
			        	//model.addAttribute("error","验证码错误");
			        	DoFlag = "失败";
			        	log.setOptInfo(username+"：登录失败，验证码错误");
			        	result =new Result(0,"验证码错误");
			        }
				 } else {
					 DoFlag = "失败";
					 log.setOptInfo(username+"：登录失败，用户名或密码错误");
					 result =new Result(0,"用户名或密码错误");
				 }
				 
			 }catch (AuthenticationException e) {
		            e.printStackTrace();
		            //model.addAttribute("error","用户名或密码错误");
		            DoFlag = "失败";
		            result =new Result(0,"用户名或密码错误");
		        }
	         log.setDofLag(DoFlag);
	         logService.InsertLog(log);
			 jsonObject = JSONObject.fromObject(result);
			 return jsonObject;

		}
		
		/**
	     * 登录页面生成验证码
	     */
	    @RequestMapping(value = "/getVerify")
	    public void getVerify(HttpServletRequest request, HttpServletResponse response){
	        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
	        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expire", 0);
	        RandomValidateCode randomValidateCode = new RandomValidateCode();
	        try {
	            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
		
		/**
		 * 登陆后主页面信息初始化
		 * @param request
		 * @param session
		 * @param model
		 * @return
		 */
		@RequestMapping(value="home")
		public String index(HttpServletRequest request, HttpSession session, Model model){
			User user = (User)session.getAttribute("user");
			logger.info("--------初始化用户信息开始----------");
        	/*查询一级菜单*/
        	List<Menu> list = userService.selectMenuList(user.getUser_name());
        	/*查询二级菜单*/
        	List<Menu> menulist = userService.FindMenuList(user.getUser_name());        
        	logger.info("--------初始化用户信息结束----------");
        	model.addAttribute("list",list);
        	model.addAttribute("menulist",menulist);
			return "index/index";
		}
}
