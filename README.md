* Small spring boot program to pull ops metrics and print the metrics to a yaml file

* To run this application

  * Compile and package
  ```
  mvn clean package
  ```
  * Set up the environment variables
  ```
export JMX_HOST=172.16.1.42
export JMX_USERNAME=jmxadmin
export JMX_PASSWORD=jmxadmin
export DUMP_FILE=metrics.csv (Default to be metrics.yml)
export DUMP_FORMAT=csv (Default is yaml)
  ```
  * Run
  ```
  java -jar target/metrics-0.0.1-SNAPSHOT.jar
  ```

* Sample produced file [metrics.yml](metrics.yml)
