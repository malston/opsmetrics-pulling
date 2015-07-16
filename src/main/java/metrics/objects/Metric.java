package metrics.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanServerConnection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Metric {

	public Map<String, Map<String, Job>> jobs;

	public Map<String, Map<String, Job>> getJobs() {
		return jobs;
	}

	public void setJobs(Map<String, Map<String, Job>> jobs) {
		this.jobs = jobs;
	}

	public Metric() {
		jobs = new HashMap<String, Map<String, Job>>();
		jobs.put("VMSysMetrics", new HashMap<String, Job>());
		jobs.put("UntitledDevMetrics", new HashMap<String, Job>());

	}

	private static Logger logger = LoggerFactory.getLogger(Metric.class);

	public void addJob(MBeanObject mBean, MBeanServerConnection connector) throws Exception {
		Hashtable<String, String> table = mBean.objectName.getKeyPropertyList();
		if ("untitled_dev".equals(table.get("deployment"))) {
			addJob(jobs.get("UntitledDevMetrics"), table, mBean, connector);
		} else {
			addJob(jobs.get("VMSysMetrics"), table, mBean, connector);
		}
	}

	public void toYaml(File file) throws IOException {
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		Yaml yaml = new Yaml(options);
		logger.info("Dump the metrics to " + file.getName());
		yaml.dump(this, new FileWriter(file));
	}

	private void addJob(Map<String, Job> jobs, Hashtable<String, String> table, MBeanObject mBean,
			MBeanServerConnection connector) throws Exception {
		Job job = jobs.get(table.get("job"));
		if (job == null) {
			job = new Job();
			jobs.put(table.get("job"), job);
		}
		Instance instance = new Instance();
		instance.setIp(table.get("ip"));
		MBeanAttributeInfo[] atrs = mBean.getInfo().getAttributes();
		List<Attribute> attributes = new ArrayList<Attribute>();
		for (MBeanAttributeInfo atr : atrs) {
			Attribute attribute = new Attribute();
			attribute.setName(atr.getName());
			attribute.setValue(connector.getAttribute(mBean.getObjectName(), atr.getName()).toString());
			attributes.add(attribute);
		}
		instance.setAttributes(attributes);
		job.getInstances().put(Integer.valueOf(table.get("index")), instance);
	}

	public void toCsv(File file) throws IOException {
		FileWriter writer = new FileWriter(file.getName());

		writer.append("Component");
		writer.append(',');
		writer.append("Objectname");
		writer.append(',');
		writer.append("instance");
		writer.append(',');
		writer.append("ip");
		writer.append(',');
		writer.append("Metric Name");
		writer.append(',');
		writer.append("Metric Value");
		writer.append('\n');
		for (String component : jobs.keySet()) {
			Map<String, Job> jobMap = jobs.get(component);
			for (String objectName : jobMap.keySet()) {
				Job job = jobMap.get(objectName);
				Map<Integer, Instance> instanceMap = job.getInstances();
				for (Integer instanceName : instanceMap.keySet()) {
					Instance instance = instanceMap.get(instanceName);
					for (Attribute attribute : instance.getAttributes()) {
						writer.append(component.replace(',', ':')).append(',');
						writer.append(objectName.replace(',', ':')).append(',');
						writer.append(instanceName + "").append(',');
						writer.append(instance.getIp()).append(',');
						writer.append(attribute.getName().replace(',', ':'));
						writer.append(',');
						writer.append(attribute.getValue().replace(',', ':'));
						writer.append('\n');
					}
				}
			}
		}
		writer.flush();
		writer.close();
	}
}
