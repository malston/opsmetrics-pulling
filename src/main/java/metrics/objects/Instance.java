package metrics.objects;


import java.util.ArrayList;
import java.util.List;

public class Instance {

    public String getIp() {

        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    String ip;

    List<Attribute> attributes = new ArrayList<Attribute>();

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
