package open.platform.domain;

import java.io.Serializable;
import java.util.Date;

public class PlatformLateAbsence implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userId;
	private Integer flag;
	
	private Long templateId;
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
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
}
