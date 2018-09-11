package cn.itcast.core.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.core.bean.JobEntity;
import cn.itcast.core.service.QuartzService;
import cn.itcast.core.web.controller.JobController;

@Service
public class QuartzServiceImpl implements QuartzService{

	@Autowired
	private Scheduler scheduler;// 获取调度器
	
	public boolean addJob(JobEntity jobEntity, HttpServletResponse response,JobController jobController) {
		boolean flag = false;
		try {
			Class s=Class.forName(jobEntity.getClazz());
			//Class cls=Class.forName(jobEntity.getClazz());
			//System.out.println(cls.getName());
			// 创建一项作业
			JobDetail job = JobBuilder.newJob(s)
			.withIdentity(jobEntity.getJobName(), jobEntity.getJobGroupName())
			.build();
			
			//测试传参
			String msg="枭雄";
			//方法一
			job.getJobDataMap().put("message", msg);
			//方法二
			scheduler.getContext().put("cn.itcast.core.web.controller.JobController", jobController);
			
			// 创建一个触发器
			CronTrigger trigger = TriggerBuilder.newTrigger()
			.withIdentity(jobEntity.getTriggerName(), jobEntity.getTriggerGroupName())
			.withSchedule(CronScheduleBuilder.cronSchedule(jobEntity.getCronExpr()))
			.build();
			// 告诉调度器使用该触发器来安排作业
			scheduler.scheduleJob(job, trigger);
			
			// 启动
			if(!scheduler.isShutdown()){
				scheduler.start();
			}
			flag = true;
		}catch (Exception e) {
			flag = false;
			throw new RuntimeException(e);			
		}
		return flag;
		
	}

}
