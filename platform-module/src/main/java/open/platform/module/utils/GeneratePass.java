package open.platform.module.utils;

import open.platform.module.utils.configure.SystemConfigure;
import open.platform.module.utils.security.Base64;
import open.platform.module.utils.security.DES;
import open.platform.module.utils.security.MD5;


/**
 * 密码加密生成器
 * 
 * @author marker
 * */
public class GeneratePass {

	private static SystemConfigure systemConfigure = SystemConfigure.getInstance();
	
	public static String encode(String password) throws Exception {
		String key = systemConfigure.get("secret_key");
		 return MD5.getMD5Code(Base64.encode(DES.encrypt(
					password.getBytes(), key))); 
	}

}
