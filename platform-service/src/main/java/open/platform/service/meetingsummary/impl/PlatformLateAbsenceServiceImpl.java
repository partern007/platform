package open.platform.service.meetingsummary.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import open.platform.domain.PlatformLateAbsence;
import open.platform.repository.mybatis.meetingsummary.LateAbsenceDao;
import open.platform.service.meetingsummary.PlatformLateAbsenceService;

@Service("platformLateAbsenceService")
public class PlatformLateAbsenceServiceImpl implements PlatformLateAbsenceService {
	
	@Resource 
	private LateAbsenceDao lateAbsenceDao;
	@Override
	public List<PlatformLateAbsence> findList(PlatformLateAbsence param) {
		
		try {
			if (null != param) {
				return lateAbsenceDao.search(param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PlatformLateAbsence findById(Long id) {
		
		if (null != id) {
			return lateAbsenceDao.selectById(id);
		}
		return null;
	}

	@Override
	public void Save(PlatformLateAbsence platformLateAbsence) {
		if (null != platformLateAbsence) {
			lateAbsenceDao.save(platformLateAbsence);
		}
		
	}

}
