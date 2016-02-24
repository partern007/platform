package open.paltform.query;

import java.io.Serializable;
/**
 * 
 * created by @author cdzengjiaping 
 *
 */
public class ResourceQuery implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户权限id
	 */
	private Long userId;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
