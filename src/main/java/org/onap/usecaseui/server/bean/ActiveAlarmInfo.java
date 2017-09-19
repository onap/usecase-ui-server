/*
 * 文 件 名:  ActiveAlarmInfo.java
 * 版    权:  Raisecom Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  donghu
 * 修改时间:  2017年8月17日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package org.onap.usecaseui.server.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <活跃告警管理>
 * @author donghu
 * @version [版本号, 2017年8月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Entity
@Table(name = "EP_ACTIVE_ALARM")
public class ActiveAlarmInfo implements Serializable
{
	/**
	 * 主键
	 */
	@Id
	@Column(name = "id", nullable = true)
	private String id;

    /**
	 * 设备名称
	 */
	@Column(name = "devName")
	private String devName;
	
	/**
     * 设备IP
     */
    @Column(name = "devIp")
    private String devIp;
    
    /**
     * 设备序列号
     */
    @Column(name = "serialNumber")
    private String serialNumber;
    
	/**
     * 告警产生时间
     */
	@Column(name = "alarmRaisedTime")
	private String alarmRaisedTime;
	
    /**
     * 告警更新时间
     */
    @Column(name = "alarmChangedTime")
    private String alarmChangedTime;

	/**
     * 告警序列号
     */
	@Column(name = "alarmIdentifier")
	private String alarmIdentifier;
	
	/**
     * 通知类型
     */
    @Column(name = "notificationType")
    private String notificationType;

	/**
     * 告警对象实例
     */
	@Column(name = "managedObjectInstance")
	private String managedObjectInstance;

	/**
     * 告警类型
     */
	@Column(name = "eventType")
	private Integer eventType;

	 /**
     * 告警可能原因
     */
	@Column(name = "probableCause")
	private String probableCause;

	/**
     * 告警描述
     */
    @Column(name = "specificProblem")
    private String specificProblem;

	/**
     * 告警级别
     */
	@Column(name = "perceivedSeverity")
	private String perceivedSeverity;

	/**
     * 告警附加文本
     */
	@Column(name = "additionalText")
	private String additionalText;

	/**
     * 告警附加信息
     */
	@Column(name = "additionalInformation")
	private String additionalInformation;
	
	/**
     * 告警清除类型
     */
    @Column(name = "clearedManner")
    private String clearedManner;
	
    /**
     * 告警状态
     */
	@Column(name = "alarmState")
	private Integer alarmState;
	
    /**
	 * 确认时间
	 */
	@Column(name = "ackTime")
	private String ackTime;
	
	/**
	 * 确认人
	 */
	@Column(name = "ackUser")
    private String ackUser;
	
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getDevName()
    {
        return devName;
    }

    public void setDevName(String devName)
    {
        this.devName = devName;
    }

    public String getDevIp()
    {
        return devIp;
    }

    public void setDevIp(String devIp)
    {
        this.devIp = devIp;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getAlarmRaisedTime()
    {
        return alarmRaisedTime;
    }

    public void setAlarmRaisedTime(String alarmRaisedTime)
    {
        this.alarmRaisedTime = alarmRaisedTime;
    }

    public String getAlarmChangedTime()
    {
        return alarmChangedTime;
    }

    public void setAlarmChangedTime(String alarmChangedTime)
    {
        this.alarmChangedTime = alarmChangedTime;
    }

    public String getAlarmIdentifier()
    {
        return alarmIdentifier;
    }

    public void setAlarmIdentifier(String alarmIdentifier)
    {
        this.alarmIdentifier = alarmIdentifier;
    }

    public String getNotificationType()
    {
        return notificationType;
    }

    public void setNotificationType(String notificationType)
    {
        this.notificationType = notificationType;
    }

    public String getManagedObjectInstance()
    {
        return managedObjectInstance;
    }

    public void setManagedObjectInstance(String managedObjectInstance)
    {
        this.managedObjectInstance = managedObjectInstance;
    }

    public Integer getEventType()
    {
        return eventType;
    }

    public void setEventType(Integer eventType)
    {
        this.eventType = eventType;
    }

    public String getProbableCause()
    {
        return probableCause;
    }

    public void setProbableCause(String probableCause)
    {
        this.probableCause = probableCause;
    }

    public String getSpecificProblem()
    {
        return specificProblem;
    }

    public void setSpecificProblem(String specificProblem)
    {
        this.specificProblem = specificProblem;
    }

    public String getPerceivedSeverity()
    {
        return perceivedSeverity;
    }

    public void setPerceivedSeverity(String perceivedSeverity)
    {
        this.perceivedSeverity = perceivedSeverity;
    }

    public String getAdditionalText()
    {
        return additionalText;
    }

    public void setAdditionalText(String additionalText)
    {
        this.additionalText = additionalText;
    }

    public String getAdditionalInformation()
    {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation)
    {
        this.additionalInformation = additionalInformation;
    }

    public Integer getAlarmState()
    {
        return alarmState;
    }

    public void setAlarmState(Integer alarmState)
    {
        this.alarmState = alarmState;
    }
    
    public String getClearedManner()
    {
        return clearedManner;
    }

    public void setClearedManner(String clearedManner)
    {
        this.clearedManner = clearedManner;
    }

    public String getAckTime()
    {
        return ackTime;
    }

    public void setAckTime(String ackTime)
    {
        this.ackTime = ackTime;
    }

    public String getAckUser()
    {
        return ackUser;
    }

    public void setAckUser(String ackUser)
    {
        this.ackUser = ackUser;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ActiveAlarmInfo[");
        sb.append("id=");
        sb.append(id);
        sb.append(",devName=");
        sb.append(devName);
        sb.append(",devIp=");
        sb.append(devIp);
        sb.append(",serialNumber=");
        sb.append(serialNumber);
        sb.append(",alarmRaisedTime=");
        sb.append(alarmRaisedTime);
        sb.append(",alarmChangedTime=");
        sb.append(alarmChangedTime);
        sb.append(",alarmIdentifier=");
        sb.append(alarmIdentifier);
        sb.append(",notificationType=");
        sb.append(notificationType);
        sb.append(",managedObjectInstance=");
        sb.append(managedObjectInstance);
        sb.append(",eventType=");
        sb.append(eventType);
        sb.append(",probableCause=");
        sb.append(probableCause);
        sb.append(",specificProblem=");
        sb.append(specificProblem);
        sb.append(",perceivedSeverity=");
        sb.append(perceivedSeverity);
        sb.append(",additionalText=");
        sb.append(additionalText);
        sb.append(",additionalInformation=");
        sb.append(additionalInformation);
        sb.append(",clearedManner=");
        sb.append(clearedManner);
        sb.append(",alarmState=");
        sb.append(alarmState);
        sb.append(",ackTime=");
        sb.append(ackTime);
        sb.append(",ackUser=");
        sb.append(ackUser);
        sb.append("]");
        return sb.toString();
    }
}
