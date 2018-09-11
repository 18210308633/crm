package cn.itcast.core.task;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import cn.itcast.core.web.controller.JobController;

public class AlertJob implements Job{

	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		String msg=context.getJobDetail().getJobDataMap().getString("message");
		System.out.println("msg--------third-------->"+msg+"--------->");
		try {
			JobController jobController=(JobController)context.getScheduler().getContext().get("cn.itcast.core.web.controller.JobController");
			jobController.test(msg);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
