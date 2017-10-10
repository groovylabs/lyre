package com.github.groovylabs.lyre.utils;

import com.github.groovylabs.lyre.EnableLyre;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RunnerUtils {

    public static String[] buildArguments(EnableLyre enableLyre) {

        List<String> arguments = new ArrayList<>();

        if (enableLyre != null) {

            //check if enable remote connection is set
            if (!StringUtils.isEmpty(enableLyre.enableRemoteConnections())
                && (enableLyre.enableRemoteConnections().equals("true")
                || enableLyre.enableRemoteConnections().equals("false")))
                arguments.add("--lyre.enable_remote_connections=" + enableLyre.enableRemoteConnections());

            //check if enable livereload is set
            if (!StringUtils.isEmpty(enableLyre.enableLivereload())
                && (enableLyre.enableLivereload().equals("true")
                || enableLyre.enableLivereload().equals("false")))
                arguments.add("--lyre.enable_livereload=" + enableLyre.enableLivereload());

            //check if port is set
            if (enableLyre.port() > 0)
                arguments.add("--lyre.port=" + enableLyre.port());

            //check if context path is set
            if (!StringUtils.isEmpty(enableLyre.contextPath())
                && (enableLyre.contextPath().equals("true")
                || enableLyre.contextPath().equals("false")))
                arguments.add("--lyre.context_path=" + enableLyre.contextPath());

            //check if api path is set
            if (!StringUtils.isEmpty(enableLyre.apiPath())
                && (enableLyre.apiPath().equals("true")
                || enableLyre.apiPath().equals("false")))
                arguments.add("--lyre.api_path=" + enableLyre.apiPath());

            //check if scan path is set
            if (!StringUtils.isEmpty(enableLyre.scanPath())
                && (enableLyre.scanPath().equals("true")
                || enableLyre.scanPath().equals("false")))
                arguments.add("--lyre.scan_path=" + enableLyre.scanPath());

            //check if file format is set
            if (!StringUtils.isEmpty(enableLyre.fileFormat())
                && (enableLyre.fileFormat().equals("true")
                || enableLyre.fileFormat().equals("false")))
                arguments.add("--lyre.file_format=" + enableLyre.fileFormat());

            //check if enable swagger doc is set
            if (!StringUtils.isEmpty(enableLyre.enableSwaggerDoc())
                && (enableLyre.enableSwaggerDoc().equals("true")
                || enableLyre.enableSwaggerDoc().equals("false")))
                arguments.add("--lyre.enable_swagger_doc=" + enableLyre.enableSwaggerDoc());

            //check if debug is set
            if (!StringUtils.isEmpty(enableLyre.debug())
                && (enableLyre.debug().equals("true")
                || enableLyre.debug().equals("false")))
                arguments.add("--lyre.debug=" + enableLyre.debug());
        }

        return arguments.toArray(new String[arguments.size()]);
    }

}
