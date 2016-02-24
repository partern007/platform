package open.platform.service.resource.impl;

import javax.annotation.Resource;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.stereotype.Service;

import open.paltform.query.ResourceQuery;
import open.platform.domain.PlatformResource;
import open.platform.repository.mybatis.BaseModule.ResourceDao;
import open.platform.service.resource.PlatformResouceService;

@Service("platformResouceService")
public class PlatformResouceServiceImpl implements PlatformResouceService {
	@Resource
	private ResourceDao resouceDao;
	@Override
	public PlatformResource getUserResource(ResourceQuery user) {
		PlatformResource platformResource = null;
		try {
			if (null != user) {
				platformResource = resouceDao.selectResourceByUser(user);
			}
		} catch (Exception e) {
			System.out.println("ddsfadsdf");
		}
		return platformResource;
	}
	
}
