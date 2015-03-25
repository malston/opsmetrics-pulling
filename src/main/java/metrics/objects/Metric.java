package metrics.objects;


import org.yaml.snakeyaml.Yaml;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanServerConnection;
import java.util.*;

public class Metric {

    public Map<String, Job> getJobs() {
        return jobs;
    }
    
    public void addJob(MBeanObject mBean, MBeanServerConnection connector) throws Exception{
        Hashtable<String, String> table = mBean.objectName.getKeyPropertyList();
        Job job = jobs.get(table.get("job"));
        if(job == null){
            job = new Job();
            jobs.put(table.get("job"), job);
        }
        Instance instance = new Instance();
        instance.setIp(table.get("ip"));
        MBeanAttributeInfo[] atrs = mBean.getInfo().getAttributes();
        List<Attribute> attributes = new ArrayList<Attribute>();
        for(MBeanAttributeInfo atr: atrs){
            Attribute attribute = new Attribute();
            attribute.setName(atr.getName());
            attribute.setDescription(atr.getDescription());
            attribute.setValue(connector.getAttribute(mBean.getObjectName(), atr.getName()).toString());
            attributes.add(attribute);
        }
        instance.setAttributes(attributes);
        job.getInstances().put(Integer.valueOf(table.get("index")), instance);
        
    }
    
    public String toYaml(){
        Yaml yaml = new Yaml(new org.yaml.snakeyaml.constructor.Constructor());
        return yaml.dump(jobs);
    }

    Map<String, Job> jobs = new HashMap<String, Job>();
}
