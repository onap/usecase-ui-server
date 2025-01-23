package org.onap.usecaseui.server.service.csmf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "uui-server.slicing")
public class SlicingProperties {
  String serviceInvariantUuid;
  String serviceUuid;
  String globalSubscriberId;
  String serviceType;
}
