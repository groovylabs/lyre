package io.groovelabs.lyre.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = LyreConfiguration.LYRE_PROPERTIES_PREFIX)
public class LyreProperties {

    private int port = 8234;

    private boolean enableRemoteConnections = false;

    private String contextPath = "";

    private String lyrePath = "/lyre";

    public ScannerProperties scanner;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getLyrePath() {
        return lyrePath;
    }

    public void setLyrePath(String lyrePath) {
        this.lyrePath = lyrePath;
    }

    public boolean isEnableRemoteConnections() {
        return enableRemoteConnections;
    }

    public void setEnableRemoteConnections(boolean enableRemoteConnections) {
        this.enableRemoteConnections = enableRemoteConnections;
    }

    public ScannerProperties getScanner() {
        return scanner;
    }

    public void setScanner(ScannerProperties scanner) {
        this.scanner = scanner;
    }
}
