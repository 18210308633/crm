package cn.itcast.common.utils;

import java.io.Serializable;

public class Result implements Serializable{

	private static final long serialVersionUID = -8423112869608165255L;
	
	public Result(){}
	
	public Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public Result(int code, String msg, String other) {
		this.code = code;
		this.msg = msg;
		this.other = other;
	}
	
	private int code;
	private String msg;
	private String other;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	
}
