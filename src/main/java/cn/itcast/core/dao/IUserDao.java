package cn.itcast.core.dao;

import java.util.List;

import cn.itcast.core.bean.Menu;
import cn.itcast.core.bean.User;

public interface IUserDao {
    int deleteByPrimaryKey(Integer id);

    boolean insert(User user);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    boolean updateByPrimaryKey(User user);
    
    User findUser(User user);
    
    User findbyname(String username);
    
    List<User> selectUserList(User user);
    
    Integer selectUserListCount(User user);
    
    boolean DeleteUser(int[] chk_value);
    /*查询一级菜单*/
    List<Menu> selectMenuList(String username);
    /*查询二级菜单*/
    List<Menu> FindMenuList(String username);
}