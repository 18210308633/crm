package cn.itcast.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.bean.LoginLog;
import cn.itcast.core.bean.ResLog;
import cn.itcast.core.dao.LoginLogDao;
import cn.itcast.core.service.LoginLogService;
@Service("loginlogService")
@Transactional
public class LoginLogServiceImpl implements LoginLogService {
	@Autowired
	public LoginLogDao dao;

	@Override
	public void InsertLog(LoginLog ll) {
		// TODO Auto-generated method stub
		dao.InsertLog(ll);

	}

	/*@Override
	public void InsertResLog(ResLog rl) {
		// TODO Auto-generated method stub
		dao.InsertResLog(rl);
	}*/

}
