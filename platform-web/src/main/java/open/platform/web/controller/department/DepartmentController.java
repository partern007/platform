package open.platform.web.controller.department;

import java.util.List;

import javax.annotation.Resource;

import open.platform.module.utils.Resp;
import open.platform.service.department.PlatformDepartmentService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/department")
public class DepartmentController {
	
	@Resource
	private PlatformDepartmentService platformDepartmentService;
	
	@ResponseBody
	@RequestMapping(value="/allCompany.do", produces = "application/json;charset=UTF-8")
	public Object queryAllCompany() {
		try{
			List<String> list = platformDepartmentService.queryAllCompany();
			return Resp.succ(list);
		} catch(Exception e) {
			return Resp.fail("获取公司失败~");
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/allDepart.do",produces = "application/json;charset=UTF-8")
	public Object queryAllDepart(String company){
		try{
			List<String> list = platformDepartmentService.queryAllDepartmentByCompany(company);
			return Resp.succ(list);
		} catch(Exception e) {
			return Resp.fail("获取部门信息失败~");
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/allGroup.do",produces = "application/json;charset=UTF-8")
	public Object queryAllGroup(String company,String department) {
		try {
			List<String> list = platformDepartmentService.queryAllGroupByComAndDepar(company, department);
			return Resp.succ(list);
		} catch (Exception e) {
			return Resp.fail("获取小组信息失败~");
		}
	}
}
