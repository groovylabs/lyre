package io.groovelabs.lyre;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = LyreConfiguration.LYRE_PROPERTIES_PREFIX)
public class LyreProperties {

    private int port = 9000;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
