package open.platform.domain;

import java.io.Serializable;
import java.util.Date;

public class PlatformSentEmail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userId;
	private Integer flag;
	
	private Date sentTime;
	private Long departmentId;
	private Date createdTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Date getSentTime() {
		return sentTime;
	}
	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
}
