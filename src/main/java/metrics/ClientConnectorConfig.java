package metrics;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.support.MBeanServerConnectionFactoryBean;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ClientConnectorConfig {
    
    @Autowired
    JmxConfig config;
    
    
    
    @Bean
    public MBeanServerConnectionFactoryBean clientConnector(){
        MBeanServerConnectionFactoryBean factoryBean = new MBeanServerConnectionFactoryBean();
        try {
            factoryBean.setServiceUrl(config.getUrl());
            Map<String,Object> environmentMap = new HashMap<String, Object>();
            String[] credentials = {config.getUsername(), config.getPassword()};
            environmentMap.put("jmx.remote.credentials", credentials);
            factoryBean.setEnvironmentMap(environmentMap);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("The JMX url is not valid", e);
        }
        return factoryBean;
    }

}
