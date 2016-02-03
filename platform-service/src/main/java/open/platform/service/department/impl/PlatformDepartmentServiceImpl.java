package open.platform.service.department.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import open.platform.domain.Department;
import open.platform.repository.mybatis.BaseModule.DepartmentDao;
import open.platform.service.department.PlatformDepartmentService;

@Service("platformDepartmentService")
public class PlatformDepartmentServiceImpl implements PlatformDepartmentService{
	
	@Resource
	private DepartmentDao departmentDao;
	
	@Override
	public List<String> queryAllCompany() {
		Department depart = new Department();
		List<Department> departments = departmentDao.queryAllCompany(depart);
		
		List<String> list = new ArrayList<String>();
		for(Department department : departments) {
			if(!StringUtils.isEmpty(department.getCompanyName() ) ) {
				list.add(StringUtils.trim(department.getCompanyName()));
			}
		}
		return list;
	}

	@Override
	public List<String> queryAllDepartmentByCompany(String company) {
		Department department = new Department();
		department.setCompanyName(company);
		List<Department> departments = departmentDao.queryAllDepartByCom(department);
		
		List<String> list = new ArrayList<String>();
		for(Department depart : departments) {
			if( !StringUtils.isEmpty(depart.getDepartName() ) ) {
				list.add(StringUtils.trim(depart.getDepartName()));
			}
		}
		
		return list;
	}

	@Override
	public List<String> queryAllGroupByComAndDepar(String company, String department) {
		Department depart = new Department();
		depart.setDepartName(company);
		depart.setDepartName(department);		
		List<Department> departments = departmentDao.queryAllGroupByComAndDepart(depart);
		
		List<String> list = new ArrayList<String>();
		for(Department department2 : departments) {
			if( !StringUtils.isEmpty(department2.getGroupName())) {
				list.add(StringUtils.trim(department2.getGroupName()));
			}
		}
		return list;
	}

	@Override
	public List<Department> queryByObject(Department department) {
		List<Department> list = departmentDao.search(department);
		return list;
	}

	@Override
	public Department queryById(Long id) {
		Department department = null;
		
		department = departmentDao.selectById(id);
		return department;
	}

	@Override
	public void save(Department department) {
		departmentDao.save(department);	
	}

	@Override
	public void delete(Department department) {
		departmentDao.delete(department);		
	}

	@Override
	public void deleteById(Long id) {
		departmentDao.deleteById(id);		
	}

	@Override
	public void update(Department department) {
		departmentDao.update(department);		
	}

}
