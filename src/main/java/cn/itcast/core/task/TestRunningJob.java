package cn.itcast.core.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestRunningJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		for(long i=0;i<9l;i++){
			System.out.println(i);
		}

	}

}
