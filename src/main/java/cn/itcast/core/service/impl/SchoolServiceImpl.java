package cn.itcast.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.utils.Page;
import cn.itcast.core.bean.School;
import cn.itcast.core.dao.SchoolDao;
import cn.itcast.core.service.SchoolService;
@Service("SchoolService")
@Transactional
public class SchoolServiceImpl implements SchoolService {
	// 定义dao属性
	@Autowired
	private SchoolDao schoolDao;
	
	@Override
	public Page<School> findSchoolList(Integer page, Integer rows,
			String score) {
		// TODO Auto-generated method stub
		School school = new School();
		if(StringUtils.isNotBlank(score)){
			score=score.trim();
			school.setScore(score);
		}
		
		//当前页
		school.setStart((page-1) * rows);
		//每页数
		school.setRows(rows);
		//查询客户列表
		List<School> schoollist = schoolDao.selectSchoolList(school);
		//查询客户列表总记录数
		Integer count = schoolDao.selectSchoolListCount(school);
		//创建Page返回对象
		Page<School> result = new Page<>();
		result.setPage(page);
		result.setRows(schoollist);
		result.setSize(rows);
		result.setTotal(count);
		return result;
	}
	

	@Override
	public School getSchoolById(int id) {
		// TODO Auto-generated method stub
		School School = schoolDao.getSchoolById(id);
		return School;
	}

	@Override
	public void updateSchool(School School) {
		// TODO Auto-generated method stub
		Date date= new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String Updatetime=sdf.format(date);
		School.setUpdatetime(Updatetime);
		schoolDao.updateSchool(School);
	}

	@Override
	public void deleteSchool(int id) {
		// TODO Auto-generated method stub
		schoolDao.deleteSchool(id);
	}

	@Override
	public void insertSchool(School School) {
		// TODO Auto-generated method stub
		Date date= new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String Createtime=sdf.format(date);
		School.setCreatetime(Createtime);
		schoolDao.insertSchool(School);
		
	}

}
