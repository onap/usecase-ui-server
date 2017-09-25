/*
 * Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onap.usecaseui.server.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "EP_ACTIVE_ALARM")
public class ActiveAlarmInfo 
{

	@Id
	@Column(name = "id", nullable = true)
	private String id;


	@Column(name = "devName")
	private String devName;
	

    @Column(name = "devIp")
    private String devIp;
    

    @Column(name = "serialNumber")
    private String serialNumber;
    

	@Column(name = "alarmRaisedTime")
	private String alarmRaisedTime;
	

    @Column(name = "alarmChangedTime")
    private String alarmChangedTime;


	@Column(name = "alarmIdentifier")
	private String alarmIdentifier;
	

    @Column(name = "notificationType")
    private String notificationType;


	@Column(name = "managedObjectInstance")
	private String managedObjectInstance;


	@Column(name = "eventType")
	private Integer eventType;


	@Column(name = "probableCause")
	private String probableCause;


    @Column(name = "specificProblem")
    private String specificProblem;


	@Column(name = "perceivedSeverity")
	private String perceivedSeverity;


	@Column(name = "additionalText")
	private String additionalText;


	@Column(name = "additionalInformation")
	private String additionalInformation;
	

    @Column(name = "clearedManner")
    private String clearedManner;
	

	@Column(name = "alarmState")
	private Integer alarmState;
	

	@Column(name = "ackTime")
	private String ackTime;
	

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
