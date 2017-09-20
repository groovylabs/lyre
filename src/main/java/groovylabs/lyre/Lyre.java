package groovylabs.lyre;

import groovylabs.lyre.config.LyreProperties;
import groovylabs.lyre.engine.APIx.APIx;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
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
public class Lyre {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lyre.class);

    @Autowired
    private LyreProperties lyreProperties;

    public static void main(String[] args) {
        SpringApplication.run(Lyre.class, args);
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
            (StringUtils.isEmpty(lyreProperties.getLyrePath()) ? "lyre" : lyreProperties.getLyrePath())
            + "/*");
        registration.setName(APIx.class.getName());
        registration.setLoadOnStartup(1);
        return registration;

    }

}
