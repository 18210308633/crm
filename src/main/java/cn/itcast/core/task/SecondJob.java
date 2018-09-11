package cn.itcast.core.task;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SecondJob implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
	System.out.println("Second  Job--------->"+new Date().toLocaleString());
		
	}

}
