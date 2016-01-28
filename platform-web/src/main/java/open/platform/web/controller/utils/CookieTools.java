package open.platform.web.controller.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
	/**
	 * passport登陆的校验key
	 */
	@Value("${checkLogin.authenticationKey}")
	private String authenticationKey;

	public boolean validateCookie(HttpServletRequest request) {
		try {
			if (parseDotnetTicket(request)) {
				FormsAuthenticationTicket ticket = FormsAuthenticationTicket.getTicket();
				LoginContext context = LoginContext.getInstance(ticket.getUsername());
				LoginContext.setLoginContext(context);
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
				if (cliCookie.equals(cookieName)) {
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
			FormsAuthenticationTicket ticket = null;

			try {
				ticket = DotnetAuthenticationUtil.getFormsAuthenticationTicket(loginCookie.getValue(),
						authenticationKey);
			} catch (Exception e) {
				logger.error("--- decrypt dotnet cookie error ---", e);
			}

			if (ticket != null) {
				FormsAuthenticationTicket.setTicket(ticket);
				return true;
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("--- ticket is null ---");
				}
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

	public String getAuthenticationKey() {
		return authenticationKey;
	}

	public void setAuthenticationKey(String authenticationKey) {
		this.authenticationKey = authenticationKey;
	}

}
