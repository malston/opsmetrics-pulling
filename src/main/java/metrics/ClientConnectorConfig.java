package metrics;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.support.MBeanServerConnectionFactoryBean;

import java.net.MalformedURLException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Configuration
public class ClientConnectorConfig {
    
    private final static String JMX_URL="service:jmx:rmi://%1$s:44444/jndi/rmi://%1$s:44444/jmxrmi";
    
    @Autowired
    JmxConfig config;
    
    
    
    @Bean
    public MBeanServerConnectionFactoryBean clientConnector(){
        MBeanServerConnectionFactoryBean factoryBean = new MBeanServerConnectionFactoryBean();
        try {
            factoryBean.setServiceUrl(String.format(JMX_URL, config.getHost(), config.getHost()));
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
