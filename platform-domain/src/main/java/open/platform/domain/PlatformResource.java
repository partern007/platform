package open.platform.domain;

import java.io.Serializable;
import java.sql.Date;
/**
 * 
 * @author cdzengjiaping
 * @since 2016/01/29
 */
public class PlatformResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	/**
	 * 可访问的资源
	 */
	private String resource;
	/**
	 * 权限
	 */
	private Integer authority;
	private Date createdTime;
	private Date modifyTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getResource() {
		return resource;
	}
	
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public Integer getAuthority() {
		return authority;
	}
	public void setAuthority(Integer authority) {
		this.authority = authority;
	}
	
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	public Date getModifyTime() {
		return modifyTime;
	}
	
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
