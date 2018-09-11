package cn.itcast.core.dao;

import java.util.List;

import cn.itcast.core.bean.School;

public interface SchoolDao {
	List<School> selectSchoolList(School School);
	Integer selectSchoolListCount(School School);
	School getSchoolById(int id);
	void updateSchool(School School);
	void deleteSchool(int id);
	void insertSchool(School School);
}
