package open.platform.service.department;

import java.util.List;

import open.platform.domain.Department;


public interface PlatformDepartmentService {
	public List<String> queryAllCompany();
	
	public List<String> queryAllDepartmentByCompany(String company);
	
	public List<String> queryAllGroupByComAndDepar(String company,String department);
	
	public List<Department> queryByObject(Department department);
	
	public Department queryById(Long id);
	
	public void save(Department department);
	
	public void delete(Department department);
	
	public void deleteById(Long id);
	
	public void update(Department department);
}
