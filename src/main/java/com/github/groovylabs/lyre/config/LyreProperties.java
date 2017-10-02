package com.github.groovylabs.lyre.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = LyreConfiguration.LYRE_PROPERTIES_PREFIX)
public class LyreProperties {

    private boolean enableRemoteConnections = false;

    private boolean enableLivereload = false;

    private int port = 8234;

    private String contextPath = "";

    private String apiPath = "api";

    private String scanPath = System.getProperty("user.dir") + "/src/main/resources";

    private String fileFormat = ".lyre";

    private boolean enableSwaggerDoc = true;

    private boolean debug = false;

    public boolean isEnableRemoteConnections() {
        return enableRemoteConnections;
    }

    public void setEnableRemoteConnections(boolean enableRemoteConnections) {
        this.enableRemoteConnections = enableRemoteConnections;
    }

    public boolean isEnableLivereload() {
        return enableLivereload;
    }

    public void setEnableLivereload(boolean enableLivereload) {
        this.enableLivereload = enableLivereload;
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

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
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