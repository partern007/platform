package open.platform.web.controller.department;

import java.util.List;

import javax.annotation.Resource;

import open.platform.domain.Department;
import open.platform.module.utils.Resp;
import open.platform.repository.mybatis.BaseModule.DepartmentDao;
import open.platform.service.department.PlatformDepartmentService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

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
	
	/*
	* 管理员拥有add部门信息权限
	*/
	@ResponseBody
	@RequestMapping(value="/add")
	public void add(String depart) {
		Department department = JSON.parseObject(depart, new TypeReference<Department>(){});
		department.setCompanyName(StringUtils.trim(department.getCompanyName()));
		department.setDepartName(StringUtils.trim(department.getDepartName()));
		department.setGroupName(StringUtils.trim(department.getGroupName()));
		platformDepartmentService.save(department);		
	}
	
	/*
	 * 管理员拥有delete部门信息权限
	 */
	@ResponseBody
	@RequestMapping(value="/del")
	public void delete(String depart) {
		Department department = JSON.parseObject(depart,new TypeReference<Department>(){});
		platformDepartmentService.delete(department);
	}
	/*
	 * 管理员拥有update部门信息的权利
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public void update(String depart){
		Department department = JSON.parseObject(depart,new TypeReference<Department>(){});
		platformDepartmentService.update(department);
	}
}
