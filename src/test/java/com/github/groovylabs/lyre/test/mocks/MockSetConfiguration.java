package com.github.groovylabs.lyre.test.mocks;

import com.github.groovylabs.lyre.domain.factories.FactoryConfig;
import com.github.groovylabs.lyre.test.LyreTestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
@Import({
    LyreTestConfiguration.class, ReaderMockConfiguration.class,
    InterpreterMockConfiguration.class, APIxMockConfiguration.class,
    DispatcherMockConfiguration.class, ScannerMockConfiguration.class,
    FactoryConfig.class
})
public class MockSetConfiguration {
}
