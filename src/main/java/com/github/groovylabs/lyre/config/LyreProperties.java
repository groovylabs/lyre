/*
 * MIT License
 *
 * Copyright (c) 2017 Groovylabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.github.groovylabs.lyre.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = LyreConfiguration.LYRE_PROPERTIES_PREFIX)
public class LyreProperties {

    private Boolean enableRemoteConnections;

    private Boolean enableLiveReload;

    private int port = 8234;

    private String contextPath = "";

    private String applicationPath = "api";

    private String scanPath = System.getProperty("user.dir");

    private String fileFormat = ".lyre";

    private boolean enableSwagger = true;

    private boolean debug = false;

    public boolean isEnableRemoteConnections() {
        return enableRemoteConnections == null ? false : enableRemoteConnections;
    }

    public void setEnableRemoteConnections(Boolean enableRemoteConnections) {
        this.enableRemoteConnections = enableRemoteConnections;
    }

    public boolean isEnableLiveReload() {
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

    public boolean isEnableSwagger() {
        return enableSwagger;
    }

    public void setEnableSwagger(boolean enableSwagger) {
        this.enableSwagger = enableSwagger;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
