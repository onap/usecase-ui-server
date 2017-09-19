
package org.onap.usecaseui.server.service;

import java.util.List;

import org.onap.usecaseui.server.bean.AlarmsHeader;

public interface AlarmsHeaderService {
    
    String saveAlarmsHeader(AlarmsHeader alarmsHeader);
    
    String updateAlarmsHeader(AlarmsHeader alarmsHeader);
    
    List<AlarmsHeader> queryAlarmsHeader(AlarmsHeader alarmsHeader);
    
}
