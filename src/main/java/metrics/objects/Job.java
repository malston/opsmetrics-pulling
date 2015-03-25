package metrics.objects;

import java.util.HashMap;
import java.util.Map;

public class Job {

    Map<Integer, Instance> instances = new HashMap<Integer, Instance>();

    public Map<Integer, Instance> getInstances() {
        return instances;
    }

    public void setInstances(Map<Integer, Instance> instances) {
        this.instances = instances;
    }
}
