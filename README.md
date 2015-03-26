* Small spring boot program to pull ops metrics and print the metrics to a yaml file

* To run this application

  * Compile and package
  ```
  mvn clean package
  ```
  * Set up the environment variables
  ```
    export JMX_URL=service:jmx:rmi://172.16.1.42:44444/jndi/rmi://172.16.1.42:44444/jmxrmi
export JMX_USERNAME=jmxadmin
export JMX_PASSWORD=jmxadmin
export DUMP_FILE=metrics.yml (Default to be metrics.yml)
  ```
  * Run
  ```
  java -jar target/metrics-0.0.1-SNAPSHOT.jar
  ```

* Sample snippet

  As below. UAA is the job name. It may have multiple instances (VMs). Each VM has the following attributes (JMX MBean attributes) to provide the visibility

  ```
  uaa: !!metrics.objects.Job
   instances:
     0:
       attributes:
       - name: healthy
         value: '1.0'
       ip: 172.16.1.52
  ```
