package metrics.objects;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.management.MBeanInfo;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.Map;

public class MetricTest {
    Metric metric = new Metric();
    MBeanObject mbean1;
    MBeanObject mbean2;
    MBeanObject mbean3;
    MBeanObject mbean4;


    @Before
    public void setUp() throws MalformedObjectNameException {
        MBeanInfo mBeanInfo = new MBeanInfo(null, null, null, null, null, null);
        mbean1 = new MBeanObject();
        mbean1.info = mBeanInfo;
        mbean1.objectName = new ObjectName("org.cloudfoundry:deployment=untitled_dev,job=MetronAgent,index=0,ip=172.16.1.47");
        mbean2 = new MBeanObject();
        mbean2.info = mBeanInfo;
        mbean2.objectName = new ObjectName("org.cloudfoundry:deployment=untitled_dev,job=MetronAgent,index=1,ip=172.16.1.48");
        mbean3 = new MBeanObject();
        mbean3.info = mBeanInfo;
        mbean3.objectName = new ObjectName("org.cloudfoundry:deployment=untitled_dev,job=CloudController,index=0,ip=172.16.1.49");
        mbean4 = new MBeanObject();
        mbean4.info = mBeanInfo;
        mbean4.objectName = new ObjectName("org.cloudfoundry:deployment=untitled_dev,job=CloudController,index=1,ip=172.16.1.50");
    }

    @Test
    public void the_object_should_add_correct_job() throws Exception {
        metric.addJob(mbean1,null);
        metric.addJob(mbean2,null);
        metric.addJob(mbean3,null);
        metric.addJob(mbean4,null);
        Map<String, Job> jobs = metric.getJobs().get("UntitledDevMetrics");
        Job metronAgent = jobs.get("MetronAgent");
        Assert.assertNotNull(metronAgent);
        Assert.assertEquals(metronAgent.getInstances().get(0).getIp(), "172.16.1.47");
        Assert.assertEquals(metronAgent.getInstances().get(1).getIp(), "172.16.1.48");
        Job cloudController = jobs.get("CloudController");
        Assert.assertNotNull(cloudController);
        Assert.assertEquals(cloudController.getInstances().get(0).getIp(), "172.16.1.49");
        Assert.assertEquals(cloudController.getInstances().get(1).getIp(), "172.16.1.50");
    }

}
