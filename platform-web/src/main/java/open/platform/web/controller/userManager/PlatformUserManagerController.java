package open.platform.web.controller.userManager;

import java.util.Date;

import javax.annotation.Resource;

import open.platform.domain.PlatformUserInfo;
import open.platform.module.utils.Resp;
import open.platform.service.manager.PlatformUserManager;

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

	@RequestMapping(value = "/base/saveUserInfo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object saveUserInfo(
			@RequestParam(value = "userInfo", required = true) String userInfo) {
		try {
			PlatformUserInfo platformUserInfo = JSON.parseObject(userInfo,
					new TypeReference<PlatformUserInfo>() {});
			platformUserManager.save(platformUserInfo);
			return Resp.succ(null);
		} catch (Exception e) {
			LOG.error("保存失败", e);
			return Resp.fail("保存失败");
		}
	}
	
	public static void main(String[] args){
		PlatformUserInfo platformUserInfo = new PlatformUserInfo();
		platformUserInfo.setUserName("libaocang");
		platformUserInfo.setEmail("email");
		platformUserInfo.setQuestion(" ");
		platformUserInfo.setAnswer(" ");
		platformUserInfo.setCreatedTime(new Date());
		platformUserInfo.setModifiedTime(new Date());
		platformUserInfo.setPassword("");
		
		String json = JSON.toJSONString(platformUserInfo);
		System.out.println(json);
	}

}
