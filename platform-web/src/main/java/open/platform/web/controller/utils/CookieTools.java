package open.platform.web.controller.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import open.platform.module.common.Constants;
import open.platform.module.utils.LoginContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieTools {

	private final static Logger logger = LoggerFactory.getLogger(
			CookieTools.class.getName());

	/**
	 * 校验登陆的cookie名
	 */
	@Value("${checkLogin.cookieName}")
	private String cookieName;

	public boolean validateCookie(HttpServletRequest request) {
		try {
			if (parseDotnetTicket(request)) {
//				FormsAuthenticationTicket ticket = FormsAuthenticationTicket.getTicket();
//				LoginContext context = LoginContext.getInstance(ticket.getUsername());
//				LoginContext.setLoginContext(context);
				logger.debug("--- parseDotnetTicket succeed ---");
			} else {
				logger.debug("--- parseDotnetTicket failed ---");
				return false;
			}
		} catch (Exception e) {
			logger.error("--- parseDotnetTicket error ---", e);
			return false;
		}
		return true;
	}

	private boolean parseDotnetTicket(HttpServletRequest request) {

		Cookie loginCookie = null;
		Cookie[] cookies = request.getCookies();
		if ((cookies != null) && (cookies.length > 0)) {
			for (Cookie cookie : cookies) {
				String cliCookie = cookie.getName();
				if (cliCookie.equals(Constants.Secure.COOKIE_KEY)) {
					loginCookie = cookie;
					break;
				}
			}
		}

		if (loginCookie == null) {
			logger.debug("--- loginCookie is null ---");
			return false;
		}

		loginCookie.setMaxAge(-1);
		if (StringUtils.isNotBlank(loginCookie.getValue())) {
//			FormsAuthenticationTicket ticket = null;

			String value = "";
			try {
				value = SymmetricEncryptionUtils.decryptByAES(loginCookie.getValue(), 
						Constants.Secure.SECURITY_KEY);
//				ticket = DotnetAuthenticationUtil.getFormsAuthenticationTicket(loginCookie.getValue(),
//						Constants.Secure.SECURITY_KEY);
			} catch (Exception e) {
				logger.error("--- decrypt dotnet cookie error ---", e);
			}

//			if (ticket != null) {
//				FormsAuthenticationTicket.setTicket(ticket);
//				return true;
//			} else {
//				if (logger.isDebugEnabled()) {
//					logger.debug("--- ticket is null ---");
//				}
//			}
			if(StringUtils.isNotBlank(value)){
				LoginContext context = LoginContext.getInstance(value);
				LoginContext.setLoginContext(context);
				return true;
			}
		} else {
			logger.debug("--- loginCookie value is blank ---");
		}

		return false;
	}

	public String getCookieName() {
		return cookieName;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

}
