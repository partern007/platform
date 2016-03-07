package open.platform.service.meetingsummary;

import java.util.List;

import open.platform.domain.PlatformSentEmail;

/**
 * 
 * Created By @author cdzengjiaping 
 *
 */
public interface PlatformSentEmailService {
	List<PlatformSentEmail> findList(PlatformSentEmail param);
	
	PlatformSentEmail findById(Long id);
	
	void Save(PlatformSentEmail platformSentEmail);
}
