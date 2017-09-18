package groovylabs.lyre.engine.APIx.logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogFactoryConfig {

    @Bean(name = "log")
    public LogFactory logFactory() {
        LogFactory factory = new LogFactory();
        return factory;
    }
}
