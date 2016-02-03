package open.platform.repository.mybatis.BaseModule;

import java.util.List;

import open.platform.domain.Department;
import open.platform.repository.mybatis.BaseDao;
import open.platform.repository.mybatis.MyBatisRepository;

/**
 * Created with IntelliJ IDEA
 * Author: root
 * Date:2016/1/25
 */
@MyBatisRepository
public interface DepartmentDao extends BaseDao<Department> {
	public List<Department> queryAllCompany(Department department);
	
	public List<Department> queryAllDepartByCom(Department department);
	
	public List<Department> queryAllGroupByComAndDepart(Department department);
}
