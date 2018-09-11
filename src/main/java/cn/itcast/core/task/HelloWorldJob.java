package cn.itcast.core.task;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloWorldJob implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println("First Job--------->" + new Date().toLocaleString());
	}

}
