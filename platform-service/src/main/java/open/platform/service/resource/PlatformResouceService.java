package open.platform.service.resource;

import open.paltform.query.ResourceQuery;
import open.platform.domain.PlatformResource;
/**
 * 
 * created by @author cdzengjiaping
 *
 */
public interface PlatformResouceService {
	/**
	 * 
	 * @param user
	 * @return
	 */
	PlatformResource getUserResource(ResourceQuery user);
}
