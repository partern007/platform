package open.platform.web.controller.userManager;

import javax.annotation.Resource;

import open.platform.domain.PlatformUserInfo;
import open.platform.module.utils.GeneratePass;
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
			platformUserInfo.setPassword(GeneratePass.encode(platformUserInfo.getPassword()));
			platformUserManager.save(platformUserInfo);
			return Resp.succ(null);
		} catch (Exception e) {
			LOG.error("保存失败", e);
			return Resp.fail("保存失败");
		}
	}
	
	@RequestMapping(value = "/base/deleteUser", produces = "application/json;charset=UTF-8")
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
	
	@RequestMapping(value = "/base/updateUserInfo", produces = "application/json;charset=UTF-8")
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
	
	@RequestMapping(value = "/base/queryAllUserInfo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryAllUserInfo() {
		try {
			return Resp.succ(platformUserManager.query());
		} catch (Exception e) {
			LOG.error("查询", e);
			return Resp.fail("查询失败");
		}
	}

}
