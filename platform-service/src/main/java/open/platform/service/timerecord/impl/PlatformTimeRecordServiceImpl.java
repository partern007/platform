package open.platform.service.timerecord.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import open.platform.domain.TimeRecord;
import open.platform.repository.mybatis.BaseModule.TimeRecordDao;
import open.platform.service.timerecord.PlatformTimeRecordService;

@Service("platformTimeRecordService")
public class PlatformTimeRecordServiceImpl implements PlatformTimeRecordService{
	
	@Resource
	private TimeRecordDao timeRecordDao;
	
	@Override
	public List<TimeRecord> search(TimeRecord timeRecord) {
		List<TimeRecord> list = timeRecordDao.search(timeRecord);
		return list;
	}

	//进行打卡操作时，确认当天是否有过打卡记录，如果有则返回改记录，如果没有返回null，-后期insert
	@Override
	public TimeRecord queryRecordByDate(TimeRecord timeRecord) {
		List<TimeRecord> list = timeRecordDao.queryRecordByDate(timeRecord);
		TimeRecord record = null;
		if( null != list && list.size() > 0) {
			record = list.get(0);
		}
		return record;
	}

	@Override
	public void save(TimeRecord timeRecord) {
		timeRecordDao.save(timeRecord);
	}

	@Override
	public void update(TimeRecord timeRecord) {
		timeRecordDao.update(timeRecord);
	}

}
