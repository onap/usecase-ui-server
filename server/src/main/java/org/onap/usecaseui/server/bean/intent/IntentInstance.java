/*
 * Copyright (C) 2021 CTC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.bean.intent;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="intent_instance")
public class IntentInstance implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "instance_id")
    private String instanceId;

    @Column(name = "job_id")
    private String jobId;

    @Column(name = "progress")
    private int progress;

    @Column(name = "status")
    private String status;

    @Column(name = "resource_instance_id")
    private String resourceInstanceId;

    @Column(name = "name")
    private String name;

    @Column(name = "cloud_point_name")
    private String cloudPointName;

    @Column(name = "access_point_one_name")
    private String accessPointOneName;

    @Column(name = "access_point_one_band_width")
    private int accessPointOneBandWidth;

    @Column(name = "line_num")
    private String lineNum;

    @Column(name = "delete_state")
    private int deleteState;

    public IntentInstance() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResourceInstanceId() {
        return resourceInstanceId;
    }

    public void setResourceInstanceId(String resourceInstanceId) {
        this.resourceInstanceId = resourceInstanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCloudPointName() {
        return cloudPointName;
    }

    public void setCloudPointName(String cloudPointName) {
        this.cloudPointName = cloudPointName;
    }

    public String getAccessPointOneName() {
        return accessPointOneName;
    }

    public void setAccessPointOneName(String accessPointOneName) {
        this.accessPointOneName = accessPointOneName;
    }

    public int getAccessPointOneBandWidth() {
        return accessPointOneBandWidth;
    }

    public void setAccessPointOneBandWidth(int accessPointOneBandWidth) {
        this.accessPointOneBandWidth = accessPointOneBandWidth;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public int getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(int deleteState) {
        this.deleteState = deleteState;
    }
}