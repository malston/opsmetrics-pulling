package metrics;

import metrics.objects.MBeanObject;
import metrics.objects.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties
public class MetricsApplication implements CommandLineRunner{

    @Autowired
    MBeanServerConnection clientConnector;
    
    public static void main(String[] args) {
        SpringApplication.run(MetricsApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        Set<ObjectInstance> objectInstances =  clientConnector.queryMBeans(new ObjectName("org.cloudfoundry:deployment=untitled_dev,*"), null);
        Metric metric = new Metric();
        for(ObjectInstance instance: objectInstances) {
            MBeanInfo beanInfo = clientConnector.getMBeanInfo(instance.getObjectName());
            MBeanObject object = new MBeanObject();
            object.setObjectName(instance.getObjectName());
            object.setInfo(beanInfo);
            metric.addJob(object, clientConnector);
            System.out.println(metric.toYaml());
        }
    }
}
