package open.platform.service.meetingsummary;

import java.util.List;

import open.platform.domain.PlatformLateAbsence;

/**
 * 
 * Created By @author cdzengjiaping 
 *
 */
public interface PlatformLateAbsenceService {
	List<PlatformLateAbsence> findList(PlatformLateAbsence param);
	
	PlatformLateAbsence findById(Long id);
	
	void Save(PlatformLateAbsence platformLateAbsence);
}
