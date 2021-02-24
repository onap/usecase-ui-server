package org.onap.usecaseui.server.service.slicingdomain.so.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TnBHSliceTaskInfo {
    private String suggestNssiId;
    private String suggestNSSIName;
    private String progress;
    private String status;
    private String statusDescription;
    private SliceProfile sliceProfile ;
    private String scriptName ;
    private Boolean enableNSSISelection ;

}
