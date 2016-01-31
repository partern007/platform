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
	
	@Override
	public void save(PlatformUserInfo platformUserInfo){
		platformUserInfoDao.save(platformUserInfo);
	}
	
	@Override
	public void delete(PlatformUserInfo platformUserInfo){
		platformUserInfoDao.delete(platformUserInfo);
	}
	
	@Override
	public void update(PlatformUserInfo platformUserInfo){
		platformUserInfoDao.update(platformUserInfo);
	}
	
	@Override
	public List<PlatformUserInfo> query(){
		List<PlatformUserInfo> platformUserInfos = platformUserInfoDao.query();
		return platformUserInfos;
	}
	
	@Override
	public boolean verifyPassword(String userName, String password){
		PlatformUserInfo userInfo = platformUserInfoDao.verifyPassword(userName, password);
		if(userInfo == null){
			return false;
		}else{
			return true;
		}
	}
	
	@Override
	public PlatformUserInfo queryByUsername(String username){
		PlatformUserInfo userInfo = platformUserInfoDao.queryByUsername(username);
		return userInfo;
	}

}
