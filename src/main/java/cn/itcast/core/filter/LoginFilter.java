package cn.itcast.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


public class LoginFilter implements Filter{

	private Logger log = LoggerFactory.getLogger(LoginFilter.class);
	
	@Override
	public void destroy() {
		log.info("destroy LoginFilter ..."); 
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		//获取请求的url
		String url=request.getRequestURI();
		if(url.indexOf("loginSubmit.action")>=0){
			//如果要进行登录提交，放行
			//身份存在，放行
			chain.doFilter(req, resp);
		}
		//判断Session中是否有登录用户信息
        String username=(String)session.getAttribute("username");		
		if(username!=null){
			//身份存在，放行
			chain.doFilter(req, resp);
		}else{
			//若没有则，跳转到登录页面
			response.sendRedirect(request.getContextPath() + "/login.action");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.info("init LoginFilter ..."); 
	}

}
