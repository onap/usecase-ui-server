package org.onap.usecaseui.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = "org.openecomp.usecaseui.server")
public class UsecaseuiServerApplication {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    
	public static void main(String[] args) {
		SpringApplication.run(UsecaseuiServerApplication.class, args);
	}
	
}
