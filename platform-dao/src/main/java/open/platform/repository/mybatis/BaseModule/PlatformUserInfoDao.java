package open.platform.repository.mybatis.BaseModule;

import java.util.List;

import open.platform.domain.PlatformUserInfo;
import open.platform.repository.mybatis.BaseDao;
import open.platform.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface PlatformUserInfoDao extends BaseDao<PlatformUserInfo>{
	public List<PlatformUserInfo> query();

}
