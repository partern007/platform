package open.platform.repository.mybatis.BaseModule;

import open.platform.domain.PlatformUserAuth;
import open.platform.repository.mybatis.BaseDao;
import open.platform.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface UserAuthDao extends BaseDao<PlatformUserAuth> {

}
