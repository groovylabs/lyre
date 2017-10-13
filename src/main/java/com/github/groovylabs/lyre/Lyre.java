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

package com.github.groovylabs.lyre;

import com.github.groovylabs.lyre.config.LyreBanner;
import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.engine.APIx.APIx;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

@SpringBootApplication
@EnableConfigurationProperties(LyreProperties.class)
@EnableAutoConfiguration
public class Lyre {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lyre.class);

    @Autowired
    private LyreProperties lyreProperties;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Lyre.class);
        app.setMainApplicationClass(Lyre.class);
        app.setBanner(new LyreBanner());
        app.run(args);
    }

    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return (container -> {

            container.setContextPath(lyreProperties.getContextPath());

            if (!lyreProperties.isEnableRemoteConnections()) {
                try {
                    InetAddress inetAddress = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
                    container.setAddress(inetAddress);
                } catch (UnknownHostException e) {
                    //supressed exception
                }
            }

            container.setPort(lyreProperties.getPort());
        });
    }

    @Bean
    public ServletRegistrationBean jerseyServletRegistration(
        JerseyProperties jerseyProperties, ResourceConfig config) {

        ServletRegistrationBean registration = new ServletRegistrationBean(
            new ServletContainer(config));

        for (Map.Entry<String, String> entry : jerseyProperties.getInit().entrySet()) {
            registration.addInitParameter(entry.getKey(), entry.getValue());
        }

        registration.addUrlMappings("/" +
            (StringUtils.isEmpty(lyreProperties.getApplicationPath()) ? "api" : lyreProperties.getApplicationPath())
            + "/*");
        registration.setName(APIx.class.getName());
        registration.setLoadOnStartup(1);
        return registration;

    }

}
