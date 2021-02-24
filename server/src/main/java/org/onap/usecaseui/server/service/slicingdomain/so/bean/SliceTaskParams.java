package org.onap.usecaseui.server.service.slicingdomain.so.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SliceTaskParams {
    private String serviceId;
    private String serviceName;
    private String nstId;
    private String nstName;
    private ServiceProfile serviceProfile;
    private String suggestNsiId;
    private String suggestNSIName;
    private TnBHSliceTaskInfo tnBHSliceTaskInfo;
    private TnMHSliceTaskInfo tnMHSliceTaskInfo;
    private TnFHSliceTaskInfo tnFHSliceTaskInfo;
    private CnSliceTaskInfo cnSliceTaskInfo;
    private AnSliceTaskInfo anSliceTaskInfo;
}
