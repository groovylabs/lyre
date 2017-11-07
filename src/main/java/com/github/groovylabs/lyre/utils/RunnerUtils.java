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

package com.github.groovylabs.lyre.utils;

import com.github.groovylabs.lyre.EnableLyre;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RunnerUtils {

    private RunnerUtils() {

    }

    public static String[] buildArguments(EnableLyre enableLyre) {

        if (enableLyre == null)
            return new String[]{};

        List<String> arguments = new ArrayList<>();

        if (checkEnableRemoteConnections(enableLyre))
            arguments.add("--lyre.enable_remote_connections=" + enableLyre.enableRemoteConnections().toLowerCase());

        if (checkEnableLiveReload(enableLyre))
            arguments.add("--lyre.enable_live_reload=" + enableLyre.enableLiveReload().toLowerCase());

        //check if port is set
        if (enableLyre.port() > 0)
            arguments.add("--lyre.port=" + enableLyre.port());

        if (checkContextPath(enableLyre))
            arguments.add("--lyre.context_path=" + enableLyre.contextPath().toLowerCase());

        if (checkApplicationPath(enableLyre))
            arguments.add("--lyre.api_path=" + enableLyre.applicationPath().toLowerCase());

        if (checkScanPath(enableLyre))
            arguments.add("--lyre.scan_path=" + enableLyre.scanPath().toLowerCase());

        if (checkFileFormat(enableLyre))
            arguments.add("--lyre.file_format=" + enableLyre.fileFormat().toLowerCase());

        if (checkSwagger(enableLyre))
            arguments.add("--lyre.enable_swagger=" + enableLyre.enableSwagger().toLowerCase());

        if (checkDebug(enableLyre))
            arguments.add("--lyre.debug=" + enableLyre.debug().toLowerCase());

        return arguments.toArray(new String[arguments.size()]);
    }

    //check if enable remote connections is set
    private static boolean checkEnableRemoteConnections(EnableLyre enableLyre) {
        return (!StringUtils.isEmpty(enableLyre.enableRemoteConnections())
            && (enableLyre.enableRemoteConnections().equalsIgnoreCase(Boolean.TRUE.toString())
            || enableLyre.enableRemoteConnections().equalsIgnoreCase(Boolean.FALSE.toString())));
    }

    //check if enable live reload is set
    private static boolean checkEnableLiveReload(EnableLyre enableLyre) {
        return (!StringUtils.isEmpty(enableLyre.enableLiveReload())
            && (enableLyre.enableLiveReload().equalsIgnoreCase(Boolean.TRUE.toString())
            || enableLyre.enableLiveReload().equalsIgnoreCase(Boolean.FALSE.toString())));
    }

    //check if context path is set
    private static boolean checkContextPath(EnableLyre enableLyre) {
        return (!StringUtils.isEmpty(enableLyre.contextPath())
            && (enableLyre.contextPath().equalsIgnoreCase(Boolean.TRUE.toString())
            || enableLyre.contextPath().equalsIgnoreCase(Boolean.FALSE.toString())));
    }

    //check if application path is set
    private static boolean checkApplicationPath(EnableLyre enableLyre) {
        return (!StringUtils.isEmpty(enableLyre.applicationPath())
            && (enableLyre.applicationPath().equalsIgnoreCase(Boolean.TRUE.toString())
            || enableLyre.applicationPath().equalsIgnoreCase(Boolean.FALSE.toString())));
    }

    //check if scan path is set
    private static boolean checkScanPath(EnableLyre enableLyre) {
        return (!StringUtils.isEmpty(enableLyre.scanPath())
            && (enableLyre.scanPath().equalsIgnoreCase(Boolean.TRUE.toString())
            || enableLyre.scanPath().equalsIgnoreCase(Boolean.FALSE.toString())));
    }

    //check if file format is set
    private static boolean checkFileFormat(EnableLyre enableLyre) {
        return (!StringUtils.isEmpty(enableLyre.fileFormat())
            && (enableLyre.fileFormat().equalsIgnoreCase(Boolean.TRUE.toString())
            || enableLyre.fileFormat().equalsIgnoreCase(Boolean.FALSE.toString())));
    }

    //check if enable swagger doc is set
    private static boolean checkSwagger(EnableLyre enableLyre) {
        return (!StringUtils.isEmpty(enableLyre.enableSwagger())
            && (enableLyre.enableSwagger().equalsIgnoreCase(Boolean.TRUE.toString())
            || enableLyre.enableSwagger().equalsIgnoreCase(Boolean.FALSE.toString())));
    }

    //check if debug is set
    private static boolean checkDebug(EnableLyre enableLyre) {
        return (!StringUtils.isEmpty(enableLyre.debug())
            && (enableLyre.debug().equalsIgnoreCase(Boolean.TRUE.toString())
            || enableLyre.debug().equalsIgnoreCase(Boolean.FALSE.toString())));

    }

}
