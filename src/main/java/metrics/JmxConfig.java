package metrics;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jmx")
@Component
public class JmxConfig {
    String url;
    String password;
    String username;
    String file;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ConfigurationProperties(prefix="yaml")
    public String getFile() {
        return file;
    }

    @ConfigurationProperties(prefix="yaml")
    public void setFile(String file) {
        this.file = file;
    }
}
