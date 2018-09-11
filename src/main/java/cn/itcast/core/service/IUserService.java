package cn.itcast.core.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.itcast.common.utils.Page;
import cn.itcast.core.bean.Menu;
import cn.itcast.core.bean.User;

public interface IUserService {

	public User getUserById(int id);
	
	public User getUser(User u);
	
	public User findbyname(String username);
	
	public Page<User> selectUserList(Integer page, Integer rows, String Name, String DeptId);
	public boolean DeleteUser(int[] chk_value);
	
	public boolean insert(HttpServletRequest request);
	public boolean updateByPrimaryKey(HttpServletRequest request);
	/*查询一级菜单*/
	public List<Menu> selectMenuList(String username);
    /*查询二级菜单*/
	public List<Menu> FindMenuList(String username);
}

