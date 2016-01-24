package open.platform.repository.mybatis;

import java.util.List;

public interface BaseDao <T>{
	List<T> search(T entity);
	T selectById(Long id);
	void save(T entity);
	void delete(T entity);
	void deleteById(Long id);
	void update(T entity);

}
