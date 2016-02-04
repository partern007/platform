package open.platform.web.controller.userManager;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import open.platform.domain.PlatformUserInfo;
import open.platform.module.common.Constants;
import open.platform.module.utils.GeneratePass;
import open.platform.module.utils.LoginContext;
import open.platform.module.utils.Resp;
import open.platform.service.manager.PlatformUserManager;
import open.platform.web.controller.utils.DotnetAuthenticationUtil;
import open.platform.web.controller.utils.SymmetricEncryptionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

@Controller
public class PlatformUserManagerController {
	private final static Logger LOG = LoggerFactory
			.getLogger(PlatformUserManagerController.class.getName());

	@Resource
	private PlatformUserManager platformUserManager;

	@RequestMapping(value = "/admin/administer/saveUserInfo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object saveUserInfo(
			@RequestParam(value = "userInfo", required = true) String userInfo, 
			HttpServletResponse response) {
		try {
			PlatformUserInfo platformUserInfo = JSON.parseObject(userInfo,
					new TypeReference<PlatformUserInfo>() {});
			String username = platformUserInfo.getUserName();
			PlatformUserInfo existUser = platformUserManager.queryByUsername(username);
			if(existUser != null){
				return Resp.fail("该用户名已经存在，请输入另一个用户名!");
			}else{
				platformUserInfo.setPassword(GeneratePass.encode(platformUserInfo.getPassword()));
				Date now = new Date();
				platformUserInfo.setCreatedTime(now);
				platformUserInfo.setModifiedTime(now);
				platformUserManager.save(platformUserInfo);
				addCookie(username, response);
				return Resp.succ(null);
			}
		} catch (Exception e) {
			LOG.error("注册失败", e);
			return Resp.fail("注册失败");
		}
	}
	
	@RequestMapping(value = "/administer/deleteUser", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object deleteUser(
			@RequestParam(value = "userInfo", required = true) String userInfo) {
		try {
			PlatformUserInfo platformUserInfo = JSON.parseObject(userInfo,
					new TypeReference<PlatformUserInfo>() {});
			platformUserManager.delete(platformUserInfo);
			return Resp.succ(null);
		} catch (Exception e) {
			LOG.error("删除失败", e);
			return Resp.fail("删除失败");
		}
	}
	
	@RequestMapping(value = "/administer/updateUserInfo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object updateUserInfo(
			@RequestParam(value = "userInfo", required = true) String userInfo) {
		try {
			PlatformUserInfo platformUserInfo = JSON.parseObject(userInfo,
					new TypeReference<PlatformUserInfo>() {});
			platformUserInfo.setPassword(GeneratePass.encode(platformUserInfo.getPassword()));
			platformUserManager.update(platformUserInfo);
			return Resp.succ(null);
		} catch (Exception e) {
			LOG.error("更新失败", e);
			return Resp.fail("更新失败");
		}
	}
	
	@RequestMapping(value = "/administer/queryAllUserInfo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryAllUserInfo() {
		try {
			return Resp.succ(platformUserManager.query());
		} catch (Exception e) {
			LOG.error("查询", e);
			return Resp.fail("查询失败");
		}
	}
	
	/**
	 * 登录请求，需要保存用户的登录状态只cookie
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/login", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object login(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userName = request.getParameter("userName");
			String passWord = request.getParameter("passWord");
			String password = GeneratePass.encode(passWord);
			LoginContext.getInstance(userName);

			//验证用户名和密码
			boolean validation = platformUserManager.verifyPassword(userName, password);
			if(validation){
				addCookie(userName, response);			
				return Resp.succ("登录成功");
			}else{
				//留在当前页面
				return Resp.fail("登录失败");
			}
		} catch (Exception e) {
			LOG.error("登录失败", e);
			return Resp.fail("登录失败");
		}
	}
	
	/**
	 * 用户注销登录，用户cookie失效
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/logout", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			String username = request.getParameter("userName");
			String aesEncryptStr = SymmetricEncryptionUtils.encryptByAES(
					username, Constants.Secure.SECURITY_KEY);
			Cookie cookies[] = request.getCookies();
			for(Cookie cookie : cookies){
				if(cookie.getName().equalsIgnoreCase(Constants.Secure.COOKIE_KEY)
						&& cookie.getValue().equalsIgnoreCase(aesEncryptStr)){
				    cookie.setSecure(false);  
//				    cookie.setDomain(Constants.Cookie.domain);
				    cookie.setPath(Constants.Cookie.path);
				    cookie.setMaxAge(Constants.Cookie.COOKIE_INVALID_TIME);  
				    response.addCookie(cookie);
				}
			}
			//重定向至登录界面
			return Resp.succ("注销成功");
		} catch (Exception e) {
			LOG.error("注销失败", e);
			return Resp.fail("注销失败");
		}
	}
	
	/**
	 * 首页
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String index(HttpServletResponse response) {
		processResponse(response);
		return null;
	}
	
	private void addCookie(String username, HttpServletResponse response){
		try {
			String aesEncryptStr = SymmetricEncryptionUtils.encryptByAES(
					username, Constants.Secure.SECURITY_KEY);
			Cookie cookie = new Cookie(Constants.Secure.COOKIE_KEY,aesEncryptStr);
			cookie.setMaxAge(-1);
//			cookie.setDomain(Constants.Cookie.domain);
		    cookie.setPath(Constants.Cookie.path);
			response.addCookie(cookie);
		} catch (Exception e) {
			LOG.error(e.toString());
		}
		
	}
	
	private void processResponse(HttpServletResponse response) {
		response.setHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "must-revalidate");
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
	}
}
