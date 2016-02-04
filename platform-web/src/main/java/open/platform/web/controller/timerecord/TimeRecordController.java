package open.platform.web.controller.timerecord;


import java.util.Date;

import javax.annotation.Resource;

import open.platform.domain.TimeRecord;
import open.platform.module.utils.LoginContext;
import open.platform.module.utils.Resp;
import open.platform.service.timerecord.PlatformTimeRecordService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/kaoqin")
public class TimeRecordController {
	
	@Resource
	private PlatformTimeRecordService platformTimeRecordService;
	
	@ResponseBody
	@RequestMapping(value="/click.do")
	public Object click() {
		LoginContext loginContext = LoginContext.getLoginContext();
		String username = loginContext.getName();
		TimeRecord record = new TimeRecord();
		record.setUsername(username);	
		Date date = new Date();
		record.setOnlineDate(date);

		TimeRecord timeRecord = platformTimeRecordService.queryRecordByDate(record);
		if(null == timeRecord){ /*当天第一次打卡*/
			record.setOnlineFlag(1);
			platformTimeRecordService.save(record);
			return Resp.succ(null).setMsg("亲，早上好~");
		}else {/*当天第2，3，...次打卡*/
			timeRecord.setOfflineFlag(1);
			timeRecord.setOfflineDate(date);
			platformTimeRecordService.update(timeRecord);
			return Resp.succ(null).setMsg("辛苦啦，早点回家休息吧，么么哒~");
		}
	}

}
