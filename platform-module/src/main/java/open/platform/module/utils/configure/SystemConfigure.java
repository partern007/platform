package open.platform.module.utils.configure;



public class SystemConfigure extends ConfigEngine{
	
	private static String path = "../conf/system.config";
	
	private SystemConfigure(){
		super(path);
	}
	
	private static class SingletonHolder {
		public final static SystemConfigure instance = new SystemConfigure();     
	}
	
	/**
	 * 获取系统配置实例
	 * */
	public static SystemConfigure getInstance(){
		return SingletonHolder.instance;
	}

}
