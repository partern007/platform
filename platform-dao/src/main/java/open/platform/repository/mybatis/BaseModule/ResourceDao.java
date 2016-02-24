package open.platform.repository.mybatis.BaseModule;

import open.paltform.query.ResourceQuery;
import open.platform.domain.PlatformResource;
import open.platform.repository.mybatis.BaseDao;
import open.platform.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface ResourceDao extends BaseDao<PlatformResource> {
	/**
	 * 通过用户信息获取Resouce
	 * @param user
	 * @return
	 */
	PlatformResource selectResourceByUser(ResourceQuery user);
}
