package open.platform.repository.mybatis.BaseModule;

import open.platform.domain.PlatformResource;
import open.platform.repository.mybatis.BaseDao;
import open.platform.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface ResourceDao extends BaseDao<PlatformResource> {
	
}
