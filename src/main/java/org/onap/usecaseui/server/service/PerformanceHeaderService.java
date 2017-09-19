
package org.onap.usecaseui.server.service;

import org.onap.usecaseui.server.bean.PerformanceHeader;


public interface PerformanceHeaderService {
    
    String savePerformanceHeader(PerformanceHeader performanceHeder);
    
    String updatePerformanceHeader(PerformanceHeader performanceHeder);
    
}
