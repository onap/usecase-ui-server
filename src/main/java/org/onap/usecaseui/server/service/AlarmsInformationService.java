
package org.onap.usecaseui.server.service;

import org.onap.usecaseui.server.bean.AlarmsInformation;


public interface AlarmsInformationService {
    
    String saveAlarmsInformation(AlarmsInformation alarmsInformation);
    
    String updateAlarmsInformation(AlarmsInformation alarmsInformation);
    
}
