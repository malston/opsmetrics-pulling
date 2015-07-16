package metrics;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dump")
public class DumpConfig {
    String file;
    String format;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
