package open.platform.web.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import open.platform.module.utils.LoginContext;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 检查用户是否有权限查看内容
 * @author cdlibaocang
 *
 */
public class CheckAuthorityInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
		throws Exception {
		//开放访问给游客
		if(LoginContext.getLoginContext().isTourist()){
			
		}else{
			
		}
		return true;
	}

}
