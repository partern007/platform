package open.platform.module.utils;


/**
 * 保存用户登录之后的所有信息
 * @author cdlibaocang
 *
 */
public class LoginContext {
	
	public final static ThreadLocal<LoginContext> holder = new ThreadLocal<LoginContext>();
	
	private String name;
	private String role;
	private boolean isTourist = true;
	
	public boolean isTourist() {
		return isTourist;
	}
	public void setTourist(boolean isTourist) {
		this.isTourist = isTourist;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public static LoginContext getInstance(){
		return new LoginContext();
	}
	
	public static LoginContext getInstance(String userName){
		LoginContext context = getInstance();
		context.setName(userName);
		return context;
	}
	
	public static LoginContext getLoginContext(){
		return holder.get();
	}
	
	public static void setLoginContext(LoginContext loginContext){
		holder.set(loginContext);
	}
	
	public static void remove(LoginContext loginContext){
		holder.remove();
	}
	
	public long createdTime = System.currentTimeMillis();

	public long getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

}
