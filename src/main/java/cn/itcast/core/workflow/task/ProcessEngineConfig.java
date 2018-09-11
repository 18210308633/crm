package cn.itcast.core.workflow.task;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.activiti.engine.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.itcast.core.web.controller.UserController;

public class ProcessEngineConfig implements ServletContextListener {
	private Logger logger = LoggerFactory.getLogger(ProcessEngineConfig.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		//获取流程引擎对象
		ProcessEngine  processEngine = Act_Init.getInstance();
		if(null != processEngine){
		//部署流程  classpath下部署
		 processEngine.getRepositoryService().createDeployment()
		.name("My process")
		.addClasspathResource("cn/itcast/core/workflow/dragrans/Ceshi_Process.bpmn")
		.addClasspathResource("cn/itcast/core/workflow/dragrans/Ceshi_Process.png")
		.deploy(); 
		//部署流程  ZIP包部署
		/*InputStream in = this.getClass().getClassLoader().getResourceAsStream("com/hxbx/workflow/resources/dragrans/workflow.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                        .createDeployment()//创建一个部署对象
                        .name("流程定义")//添加部署名称
                        .addZipInputStream(zipInputStream)//完成zip文件的部署
                        .deploy();//完成部署
*/
		logger.info("——————————————————-----activiti 流程部署成功-----——————————————————————"); 
	}else{
		logger.error("——————————————————-----activiti 获取服务失败-----——————————————————————"); 
	}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		logger.info("关闭流程---------------------------------");      
	}

}
