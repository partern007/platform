package open.platform.repository.mybatis.BaseModule;

import java.util.List;

import open.platform.domain.TimeRecord;
import open.platform.repository.mybatis.BaseDao;
import open.platform.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface TimeRecordDao extends BaseDao<TimeRecord>{
	//打卡之前查询是否当天有打卡记录
	public List<TimeRecord> queryRecordByDate(TimeRecord record);
}
