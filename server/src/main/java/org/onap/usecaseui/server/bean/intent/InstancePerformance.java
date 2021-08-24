/*
 * Copyright (C) 2017 CTC, Inc. and others. All rights reserved.
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
import java.util.Date;

@Entity
@Table(name="instance_performance")
public class InstancePerformance implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "job_id")
    private String jobId;

    @Column(name = "resource_instance_id")
    private String resourceInstanceId;

    @Column(name = "bandwidth")
    private String bandwidth;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Column(name = "max_bandwidth")
    private String maxBandwidth;


    public InstancePerformance() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getResourceInstanceId() {
        return resourceInstanceId;
    }

    public void setResourceInstanceId(String resourceInstanceId) {
        this.resourceInstanceId = resourceInstanceId;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMaxBandwidth() {
        return maxBandwidth;
    }

    public void setMaxBandwidth(String maxBandwidth) {
        this.maxBandwidth = maxBandwidth;
    }
}