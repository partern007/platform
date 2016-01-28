package open.platform.module.utils.configure;
import java.util.Properties;

public interface IConfig{
	
	
	/**
	 * 获取值
	 * @param key
	 * @return
	 */
	String get(String key);
	
	/**
	 * 设置键值
	 * @param key
	 * @param value
	 */
	void set(String key, String value);
	
	
	/**
	 * 获取内部Properties
	 * @return
	 */
	Properties getProperties();
	
	
	/**
	 * 持久化配置文件
	 */
	void store();
	
}
