package metrics.objects;


import javax.management.MBeanInfo;
import javax.management.ObjectName;

public class MBeanObject {
    public MBeanInfo getInfo() {
        return info;
    }

    public void setInfo(MBeanInfo info) {
        this.info = info;
    }

    public ObjectName getObjectName() {
        return objectName;
    }

    public void setObjectName(ObjectName objectName) {
        this.objectName = objectName;
    }

    MBeanInfo info;
    ObjectName objectName;
    
}
