package open.platform.service.manager;

import javax.annotation.Resource;
import java.util.List;

import open.platform.domain.PlatformUserInfo;
import open.platform.repository.mybatis.BaseModule.PlatformUserInfoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("platformUserManager")
public class PlatformUserManagerImpl implements PlatformUserManager{
	@Resource
	private PlatformUserInfoDao platformUserInfoDao;
	
	public void save(PlatformUserInfo platformUserInfo){
		platformUserInfoDao.save(platformUserInfo);
	}
	public void delete(PlatformUserInfo platformUserInfo){
		platformUserInfoDao.delete(platformUserInfo);
	}
	public void update(PlatformUserInfo platformUserInfo){
		platformUserInfoDao.update(platformUserInfo);
	}
	public List<PlatformUserInfo> query(){
		List<PlatformUserInfo> platformUserInfos = platformUserInfoDao.query();
		return platformUserInfos;
	}

}
