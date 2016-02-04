package open.platform.service.timerecord;

import java.util.List;

import open.platform.domain.TimeRecord;

public interface PlatformTimeRecordService {
	public List<TimeRecord> search(TimeRecord timeRecord);
	
	public TimeRecord queryRecordByDate(TimeRecord timeRecord);
	
	public void save(TimeRecord timeRecord);
	
	public void update(TimeRecord timeRecord);
}
