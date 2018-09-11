package cn.itcast.core.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.common.utils.Result;
import cn.itcast.core.bean.JobEntity;
import cn.itcast.core.bean.ScheduleJob;
import cn.itcast.core.service.QuartzService;

@Controller
public class JobController {

	@Autowired
	private QuartzService quartzService;
	
	@Autowired
	private Scheduler scheduler;

	/**
	 * 添加
	 * @param job
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="task/add",method=RequestMethod.POST)
	public JSONObject addJob(JobEntity job, HttpServletResponse response, HttpServletRequest request) {
		JSONObject jsonObject = null;
		Result result =new Result();
		String jobClass = request.getParameter("jobClass");
		job.setClazz(jobClass);
		boolean flag = quartzService.addJob(job,response,new JobController());
		if (flag == true) {
			result =new Result(1,"新增成功");
		} else {
			result =new Result(0,"新增失败");
		}
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}

	/**
	 * Test1获取所有计划中的任务列表
	 * @param model
	 * @return
	 * @throws SchedulerException
	 */
	@RequestMapping(value = "task/taskJob")
	public String getAllJobTest1(Model model) throws SchedulerException{
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		scheduler = schedulerFactory.getScheduler();
		scheduler.getSchedulerName();
		List<JobEntity> jobInfos = new ArrayList<JobEntity>();
		
		List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
		
		for (String triggerGroupName : triggerGroupNames) {
			Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(GroupMatcher
							.triggerGroupEquals(triggerGroupName));
			for (TriggerKey triggerKey : triggerKeySet) {
				Trigger t = scheduler.getTrigger(triggerKey);
				if (t instanceof CronTrigger) {
					CronTrigger trigger = (CronTrigger) t;
					JobKey jobKey = trigger.getJobKey();
					JobDetail jd = scheduler.getJobDetail(jobKey);
					JobEntity jobInfo = new JobEntity();
					jobInfo.setJobName(jobKey.getName());
					jobInfo.setJobGroupName(jobKey.getGroup());
					jobInfo.setTriggerName(triggerKey.getName());
					jobInfo.setTriggerGroupName(triggerKey.getGroup());
					jobInfo.setCronExpr(trigger.getCronExpression());
					jobInfo.setNextFireTime(trigger.getNextFireTime());
					jobInfo.setPreviousFireTime(trigger.getPreviousFireTime());
					jobInfo.setStartTime(trigger.getStartTime());
					jobInfo.setEndTime(trigger.getEndTime());
					jobInfo.setJobClass(jd.getJobClass().getCanonicalName());
					// jobInfo.setDuration(Long.parseLong(jd.getDescription()));
					//System.out.println(jd.getDescription());
					Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
					jobInfo.setJobStatus(triggerState.toString());// NONE无,NORMAL正常,PAUSED暂停,COMPLETE完成,BLOCKED阻塞
					jobInfos.add(jobInfo);
				}
			}
		}
		JSONArray json = JSONArray.fromObject(jobInfos);
		model.addAttribute("json", json.toString());
		model.addAttribute("list", jobInfos);
		return "quartzs/tasklist";
	}
	/**
	 * Test2获取所有计划中的任务列表
	 * 
	 * @param model
	 * @return
	 * @throws SchedulerException
	 */
	@RequestMapping(value = "task/taskJobState")
	public String getAllJobTest2(Model model) throws SchedulerException {
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJob job = new ScheduleJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		model.addAttribute("list", jobList);
		return "quartzs/taskstatelist";
	}

	/**
	 * 所有正在运行的job
	 * 
	 * @return
	 * @throws SchedulerException 
	 */
	@RequestMapping("task/runjob")
	public String runningJob(Model model) throws SchedulerException {
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		model.addAttribute("list", jobList);
		return "quartzs/runjob";
	}
	
	/**
	 * 暂停任务
	 * @param jobName  
	 * @param jobGroupName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("task/pauseJob")
	public JSONObject pauseJob(@RequestParam("jobName")String jobName,
			@RequestParam("jobGroupName")String jobGroupName,HttpServletRequest request){
		JSONObject jsonObject = null;
		Result result =new Result();
		String method=request.getMethod();
		try {
			if("GET".equals(method)){
				jobName = new String(jobName.getBytes("iso8859-1"),"UTF-8");
				jobGroupName = new String(jobGroupName.getBytes("iso8859-1"),"UTF-8");
			}
			JobKey jobKey=JobKey.jobKey(jobName, jobGroupName);
			scheduler.pauseJob(jobKey);
			result =new Result(1,"暂停成功");
		} catch (SchedulerException e) {
			e.printStackTrace();
			result =new Result(0,"暂停失败"+e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result =new Result(0,"暂停失败"+e.getMessage());
		}
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	
	/**
	 * 启动任务
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("task/resumeJob")
	public JSONObject resumeJob(@RequestParam("jobName")String jobName,
			@RequestParam("jobGroupName")String jobGroupName,HttpServletRequest request){
		JSONObject jsonObject = null;
		Result result =new Result();
		String method=request.getMethod();
		
		try {
			if("GET".equals(method)){
				jobName=new String(jobName.getBytes("iso8859-1"),"UTF-8");
				jobGroupName=new String(jobGroupName.getBytes("iso8859-1"),"UTF-8");
			}
			JobKey jobKey=JobKey.jobKey(jobName, jobGroupName);
			scheduler.resumeJob(jobKey);
			result =new Result(1,"启动成功");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result =new Result(0,"启动失败"+e.getMessage());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result =new Result(0,"启动失败"+e.getMessage());
		}
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	
	/**
	 * 删除任务
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("task/deleteJob")
	public JSONObject deleteJob(@RequestParam("jobName")String jobName,
			@RequestParam("jobGroupName")String jobGroupName,
			@RequestParam("triggerName")String triggerName,
			@RequestParam("triggerGroupName")String triggerGroupName,HttpServletRequest request){
		JSONObject jsonObject = null;
		Result result =new Result();
		String method=request.getMethod();
		try {
			if("GET".equals(method)){
				jobName=new String(jobName.getBytes("iso8859-1"),"UTF-8");
				jobGroupName=new String(jobGroupName.getBytes("iso8859-1"),"UTF-8");
				triggerName=new String(triggerName.getBytes("iso8859-1"),"UTF-8");
				triggerGroupName=new String(triggerGroupName.getBytes("iso8859-1"),"UTF-8");
			}
			JobKey jobKey=JobKey.jobKey(jobName, jobGroupName);
			TriggerKey triggerKey=TriggerKey.triggerKey(triggerName, triggerGroupName);
			// 停止触发器
			scheduler.pauseTrigger(triggerKey);
			// 移除触发器
			scheduler.unscheduleJob(triggerKey);
			// 删除任务
			scheduler.deleteJob(jobKey);
			result =new Result(1,"删除成功");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result =new Result(0,"删除失败"+e.getMessage());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result =new Result(0,"删除失败"+e.getMessage());
		}
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	
	/**
	 * 跳转到编辑页面
	 * @param jobName
	 * @param jobGroupName
	 * @param model
	 * @return
	 */
	@RequestMapping("task/toEditJob")
	public String toEditJob(@RequestParam("jobName")String jobName,
			@RequestParam("jobGroupName")String jobGroupName,Model model,HttpServletRequest request){
		String method=request.getMethod();
		JobEntity jobEntity=new JobEntity();
		try {
			if("GET".equals(method)){
				jobName=new String(jobName.getBytes("ISO-8859-1"),"UTF-8");
				jobGroupName=new String(jobGroupName.getBytes("ISO-8859-1"),"UTF-8");
			}
			JobKey jobKey=JobKey.jobKey(jobName, jobGroupName);
			JobDetail jobDetail=scheduler.getJobDetail(jobKey);
			List<CronTrigger> triggers = (List<CronTrigger>) scheduler.getTriggersOfJob(jobKey);
			CronTrigger	trigger=triggers.get(0);
			TriggerKey triggerKey=trigger.getKey();
			String cron=trigger.getCronExpression();
			jobEntity.setJobName(jobKey.getName());
			jobEntity.setJobGroupName(jobKey.getGroup());
			jobEntity.setTriggerName(triggerKey.getName());
			jobEntity.setTriggerGroupName(triggerKey.getGroup());
			jobEntity.setCronExpr(cron);
			jobEntity.setClazz(jobDetail.getJobClass().getCanonicalName());
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("jobEntity", jobEntity);
		return "forward:quartzs/edit.jsp";
	}
	
	/**
	 * 编辑
	 * @param jobEntity
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("task/editJob")
	public JSONObject editJob(JobEntity jobEntity,HttpServletResponse response, HttpServletRequest request) throws IOException{
		JSONObject jsonObject = null;
		Result result =new Result();
		TriggerKey oldTriggerKey=TriggerKey.triggerKey(jobEntity.getOldtriggerName(), jobEntity.getOldtriggerGroup());
		JobKey jobKey=JobKey.jobKey(jobEntity.getOldjobName(), jobEntity.getOldjobGroupName());
		response.setContentType("text/html;charset=UTF-8");
		try {
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(oldTriggerKey);
			if(trigger!=null){
				JobDetail jobDetail=scheduler.getJobDetail(jobKey);
				// 停止触发器
				scheduler.pauseTrigger(oldTriggerKey);
				// 移除触发器
				scheduler.unscheduleJob(oldTriggerKey);
				// 删除任务
				scheduler.deleteJob(jobKey);
				quartzService.addJob(jobEntity, response, new JobController());
				result =new Result(1,"修改成功");				
			}else{
				result =new Result(0,"修改失败");
			}
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result =new Result(0,"修改失败"+e.getMessage());
		}
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	
	public void test(String msg){
		System.out.println(msg+"---------------进入controller--------------------");
	}
}
