package open.platform.service.manager;

import java.util.List;

import open.platform.domain.PlatformUserInfo;


/**
 * 用户模块
 * @author cdlibaocang
 *
 */
public interface PlatformUserManager {	
	public void save(PlatformUserInfo platformUserInfo);
	public void delete(PlatformUserInfo platformUserInfo);
	public List<PlatformUserInfo> query(PlatformUserInfo platformUserInfo);
}
