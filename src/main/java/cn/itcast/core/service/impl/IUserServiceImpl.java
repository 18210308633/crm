package cn.itcast.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.utils.CommonConstant;
import cn.itcast.common.utils.Page;
import cn.itcast.common.utils.Utils;
import cn.itcast.core.bean.Menu;
import cn.itcast.core.bean.User;
import cn.itcast.core.dao.IUserDao;
import cn.itcast.core.service.IUserService;

@Service("userService")
@Transactional
public class IUserServiceImpl  implements IUserService{

	@Autowired
	public IUserDao udao;
	
	@Override
	public User getUserById(int id) {
		return udao.selectByPrimaryKey(id);
	}

	@Override
	public User getUser(User u) {
		// TODO Auto-generated method stub
		return udao.findUser(u);
	}

	@Override
	public User findbyname(String username) {
		// TODO Auto-generated method stub
		return udao.findbyname(username);
	}

	@Override
	public Page<User> selectUserList(Integer page, Integer rows, String Name,
			String DeptId) {
		// TODO Auto-generated method stub
		User user = new User();
		if(StringUtils.isNotBlank(Name)){
			Name = Name.replace("", "%");
			user.setName(Name);
		}
		if(StringUtils.isNotBlank(DeptId)){
			user.setDeptid(DeptId);
		}
		//当前页
		user.setStart((page-1) * rows) ;
		//每页数
		user.setRows(rows);
		//查询用户列表
		List<User> list = udao.selectUserList(user);
		//查询用户列表总记录数
		Integer count = udao.selectUserListCount(user);
		//创建Page返回对象
		Page<User> result = new Page<>();
		result.setPage(page);
		result.setRows(list);
		result.setSize(rows);
		result.setTotal(count);
		return result;
	}

	@Override
	public boolean DeleteUser(int[] chk_value) {
		// TODO Auto-generated method stub
		boolean flag = udao.DeleteUser(chk_value);
		return flag;
	}

	@Override
	public boolean insert(HttpServletRequest request) {
		// TODO Auto-generated method stub
		User user = new User();
		//取得当前时间
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String DateTime = sdf.format(date);
		
		String User_Name = request.getParameter("user_name");
		user.setUser_name(User_Name);
		String Name = request.getParameter("name");
		user.setName(Name);
		String DeptId = request.getParameter("deptid");
		user.setDeptid(DeptId);
		String PersonType = request.getParameter("persontype");
		user.setPersontype(PersonType);
		String TellPhone = request.getParameter("tellphone");
		user.setTellphone(TellPhone);
		String Email = request.getParameter("email");
		user.setEmail(Email);
		String IsDisable = request.getParameter("isdisable");
		user.setIsdisable(IsDisable);
		user.setPassword(Utils.md5Pwd(CommonConstant.LONGIN_Password, User_Name));
		user.setCreatedate(DateTime);
		boolean flag = udao.insert(user);
		return flag;
	}

	@Override
	public boolean updateByPrimaryKey(HttpServletRequest request) {
		// TODO Auto-generated method stub
		User user = new User();
		//取得当前时间
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String DateTime = sdf.format(date);
		String Id = request.getParameter("id");
		int srtId = Integer.parseInt(Id);
		user.setId(srtId);
		String User_Name = request.getParameter("user_name");
		user.setUser_name(User_Name);
		String Name = request.getParameter("name");
		user.setName(Name);
		String DeptId = request.getParameter("deptid");
		user.setDeptid(DeptId);
		String PersonType = request.getParameter("persontype");
		user.setPersontype(PersonType);
		String TellPhone = request.getParameter("tellphone");
		user.setTellphone(TellPhone);
		String Email = request.getParameter("email");
		user.setEmail(Email);
		String IsDisable = request.getParameter("isdisable");
		user.setIsdisable(IsDisable);
		user.setUpdatedate(DateTime);
		
		boolean flag = udao.updateByPrimaryKey(user);
		return flag;
	}

	@Override
	public List<Menu> selectMenuList(String username) {
		// TODO Auto-generated method stub
		List<Menu> list = udao.selectMenuList(username);
		return list;
	}

	@Override
	public List<Menu> FindMenuList(String username) {
		// TODO Auto-generated method stub
		List<Menu> list = udao.FindMenuList(username);
		return list;
	}

	


}
