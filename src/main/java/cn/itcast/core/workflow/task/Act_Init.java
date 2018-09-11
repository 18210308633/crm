package cn.itcast.core.workflow.task;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Act_Init {
	private static Logger logger = LoggerFactory.getLogger(Act_Init.class);
	private static ProcessEngine instance=null;
	 
	 private Act_Init(){};
	 
	 public static ProcessEngine getInstance() {
		 
	 if(instance==null)
		 
	 instance=Act_Init.getProcessEngine();
	 
	 return instance;
	 
	 }
	 
	 public static ProcessEngine getProcessEngine(){
			
			String strDBType = "mysql";
			//获取数据库连接参数
			String strDriver = "com.mysql.jdbc.Driver";
			String strUrl = "jdbc:mysql://localhost/crm?useUnicode=true&characterEncoding=UTF-8&useOldAliasMetadataBehavior=true";
			String strUser = "root";
			String strPassword = "root";
			//获取建表机制参数
			String strDatabaseSchemaUpdate = "true";
			
			//初始化流程引擎
			ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
			//连接数据库
			configuration.setDatabaseType(strDBType);
			configuration.setJdbcDriver(strDriver);
			configuration.setJdbcUrl(strUrl);
			configuration.setJdbcUsername(strUser);
			configuration.setJdbcPassword(strPassword);
			//如果oracle多个实例下创建activiti表，需要设置此值，否则只能创建一个activiti表
			//configuration.setDatabaseSchema("ACT");
			//建表机制，有则使用，无则创建
			configuration.setDatabaseSchemaUpdate(strDatabaseSchemaUpdate);
			//打开定时任务
			configuration.setJobExecutorActivate(true);
			configuration.setActivityFontName("宋体");
			configuration.setLabelFontName("宋体");
			 
			//创建核心对象，流程引擎
			 ProcessEngine processEngine = configuration.buildProcessEngine();
			 logger.info("——————————————————-----activiti 获取仓库服务成功------——————————————————————");
			 return processEngine;
			
		}
}
