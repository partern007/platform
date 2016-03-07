package open.platform.service.meetingsummary.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import open.platform.domain.PlatformSentEmail;
import open.platform.repository.mybatis.meetingsummary.SentEmailDao;
import open.platform.service.meetingsummary.PlatformSentEmailService;

@Service("platformSentEmailService")
public class PlatformSentEmailServiceImpl implements PlatformSentEmailService {
	
	@Resource 
	private SentEmailDao sentEmailDao;
	@Override
	public List<PlatformSentEmail> findList(PlatformSentEmail param) {
		
		try {
			if (null != param) {
				return sentEmailDao.search(param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PlatformSentEmail findById(Long id) {
		
		if (null != id) {
			return sentEmailDao.selectById(id);
		}
		return null;
	}

	@Override
	public void Save(PlatformSentEmail platformSentEmail) {
		if (null != platformSentEmail) {
			sentEmailDao.save(platformSentEmail);
		}
		
	}

}
