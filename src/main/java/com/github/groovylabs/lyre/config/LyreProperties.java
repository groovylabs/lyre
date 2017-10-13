package com.github.groovylabs.lyre.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = LyreRunner.LYRE_PROPERTIES_PREFIX)
public class LyreProperties {

    private Boolean enableRemoteConnections;

    private Boolean enableLiveReload;

    private int port = 8234;

    private String contextPath = "";

    private String applicationPath = "api";

    private String scanPath = System.getProperty("user.dir");

    private String fileFormat = ".lyre";

    private boolean enableSwaggerDoc = true;

    private boolean debug = false;

    public boolean isEnableRemoteConnections() {
        return enableRemoteConnections == null ? false : enableRemoteConnections;
    }

    public void setEnableRemoteConnections(Boolean enableRemoteConnections) {
        this.enableRemoteConnections = enableRemoteConnections;
    }

    public boolean getEnableLiveReload() {
        return enableLiveReload == null ? false : enableLiveReload;
    }

    public void setEnableLiveReload(Boolean enableLiveReload) {
        this.enableLiveReload = enableLiveReload;
    }

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

    public String getApplicationPath() {
        return applicationPath;
    }

    public void setApplicationPath(String applicationPath) {
        this.applicationPath = applicationPath;
    }

    public String getScanPath() {
        return scanPath;
    }

    public void setScanPath(String scanPath) {
        this.scanPath = scanPath;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public boolean isEnableSwaggerDoc() {
        return enableSwaggerDoc;
    }

    public void setEnableSwaggerDoc(boolean enableSwaggerDoc) {
        this.enableSwaggerDoc = enableSwaggerDoc;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
