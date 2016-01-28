package open.platform.web.controller.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import open.platform.module.utils.LoginContext;
import open.platform.web.controller.utils.CookieTools;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CheckLoginInterception extends HandlerInterceptorAdapter {
	
	private final static Logger logger = LoggerFactory
			.getLogger(CheckLoginInterception.class.getName());
	
	@Resource
	private CookieTools cookieTools;
	
	@Value("${loginUrl}")
	private String loginUrl;
	
	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if (!checkCookie(request, response)) {
			// 可以判断页面类型，有的请求
			PrintWriter out = response.getWriter();
			String accept = request.getHeader("accept");

			if (accept.matches(".*application/json.*")) {// json数据请求
				out.write("{\"status\":false,\"code\":\"101\",\"message\":\"当前会话失效，请重新登录系统!\"}");
			}
			// HTML页面
			out.write("<script type='text/javascript'>window.location.href='login.do?status=timeout';</script>");
			out.flush();
			out.close();
			return false;
		}
		/**
		 * 1：初始化cookie 2：初始化LoginContext
		 */
		initLoginContext();
		return true;
	}
	
	private void initLoginContext(){
		LoginContext context = LoginContext.getInstance();
		String name = context.getName();
		//在用户表中查到该用户，则该用户不为游客
		//给游客分发可访问的资源
		if(context.isTourist()){
			
		}else{//管理员可访问的资源
			
		}
	}
	
	/**
	 * 校验Cookie
	 */
	protected boolean checkCookie(final HttpServletRequest request, 
			final HttpServletResponse response) throws ServletException, IOException {
		if (! cookieTools.validateCookie(request)) {
			response.setHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "must-revalidate");
			response.addHeader("Cache-Control", "no-cache");
			response.addHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			
			response.sendRedirect(this.getPassportUrl(request));
			return false;
		}
		return true;
	}

	/**
	 * 获得重定向到passport的url
	 */
	protected String getPassportUrl(HttpServletRequest request) 
			throws ServletException, IOException {
		String queryString = request.getQueryString();
		StringBuffer requestUrl = request.getRequestURL().append(
				StringUtils.isBlank(queryString) ? "" : "?" + queryString);
		
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(loginUrl).append("?ReturnUrl=").append(
				URLEncoder.encode(requestUrl.toString(), "UTF-8"));
		
		return urlBuilder.toString();
	}

}
