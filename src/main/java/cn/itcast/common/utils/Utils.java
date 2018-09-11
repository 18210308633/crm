package cn.itcast.common.utils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class Utils {
	//温馨提示：记得在注册中密码存入数据库前也记得加密哦，提供一个utils方法
	//进行shiro加密，返回加密后的结果
	public static String md5(String pass){
	String saltSource = "abcdefg";    
	String hashAlgorithmName = "MD5";
	Object salt = new Md5Hash(saltSource);
	int hashIterations = 0;    
	Object result = new SimpleHash(hashAlgorithmName, pass, salt, hashIterations);
	String password = result.toString();
	return password;
	}
	
	 public static String md5Pwd(String password, String salt) {
	        // TODO Auto-generated method stub
	        String md5Pwd = new Md5Hash(password, salt).toHex();
	        return md5Pwd;
	    }
	 

}
