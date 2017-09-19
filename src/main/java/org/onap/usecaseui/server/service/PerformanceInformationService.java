
package org.onap.usecaseui.server.service;

import org.onap.usecaseui.server.bean.PerformanceInformation;


public interface PerformanceInformationService {
    
    String savePerformanceInformation(PerformanceInformation performanceInformation);
    
    String updatePerformanceInformation(PerformanceInformation performanceInformation);
    
}
