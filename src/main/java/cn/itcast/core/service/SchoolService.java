package cn.itcast.core.service;

import cn.itcast.common.utils.Page;
import cn.itcast.core.bean.School;



public interface SchoolService {

	// 查询客户列表
	public Page<School> findSchoolList(Integer page, Integer rows, 
			String score);

	public School getSchoolById(int id);

	public void updateSchool(School School);

	public void deleteSchool(int id);
	public void insertSchool(School School);

}
