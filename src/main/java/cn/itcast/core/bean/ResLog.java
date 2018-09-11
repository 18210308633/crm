package cn.itcast.core.bean;

import javax.sql.rowset.serial.SerialBlob;

public class ResLog {
	private String LogId;
	private String LogLevel;
	private String ClassPath;
	private String ClassName;
	private String MethodName;
	private String LineNumber;
	private String ThreadName;
	private String CurrentTime;
	private SerialBlob Message;
	public String getLogId() {
		return LogId;
	}
	public void setLogId(String logId) {
		LogId = logId;
	}
	public String getLogLevel() {
		return LogLevel;
	}
	public void setLogLevel(String logLevel) {
		LogLevel = logLevel;
	}
	public String getClassPath() {
		return ClassPath;
	}
	public void setClassPath(String classPath) {
		ClassPath = classPath;
	}
	public String getClassName() {
		return ClassName;
	}
	public void setClassName(String className) {
		ClassName = className;
	}
	public String getMethodName() {
		return MethodName;
	}
	public void setMethodName(String methodName) {
		MethodName = methodName;
	}
	public String getLineNumber() {
		return LineNumber;
	}
	public void setLineNumber(String lineNumber) {
		LineNumber = lineNumber;
	}
	public String getThreadName() {
		return ThreadName;
	}
	public void setThreadName(String threadName) {
		ThreadName = threadName;
	}
	public String getCurrentTime() {
		return CurrentTime;
	}
	public void setCurrentTime(String currentTime) {
		CurrentTime = currentTime;
	}
	public SerialBlob getMessage() {
		return Message;
	}
	public void setMessage(SerialBlob serialBlob) {
		Message = serialBlob;
	}
	
}
