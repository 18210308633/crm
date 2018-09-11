package cn.itcast.core.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.core.bean.JobEntity;
import cn.itcast.core.web.controller.JobController;

public interface QuartzService {

	public boolean addJob(JobEntity job, HttpServletResponse response,JobController jobController);
}
