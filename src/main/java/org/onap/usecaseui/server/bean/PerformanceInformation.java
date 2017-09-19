/**
 * 文件名:
 */
package org.onap.usecaseui.server.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author xuekui
 *
 */
@Entity
@Table(name="alarms_additionalinformation")
public class PerformanceInformation implements Serializable{
	
	@Id
	@Column(name = "name")
	private String name;
	
	@Column(name = "value")
	private String value;
	
	@Id
	@Column(name = "eventId")
	private String eventId;
	
	@Column(name = "createTime")
	private Date  createTime;
	
	@Column(name = "updateTime")
	private Date updateTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
