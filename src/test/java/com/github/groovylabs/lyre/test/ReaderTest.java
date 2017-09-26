package com.github.groovylabs.lyre.test;

import com.github.groovylabs.lyre.engine.reader.Reader;
import com.github.groovylabs.lyre.test.configurations.LyrePropertiesConfiguration;
import com.github.groovylabs.lyre.test.configurations.ReaderConfiguration;
import com.github.groovylabs.lyre.test.configurations.ResourcesConfiguration;
import com.github.groovylabs.lyre.test.tools.Resources;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Import({
    LyrePropertiesConfiguration.class,
    ResourcesConfiguration.class,
    ReaderConfiguration.class
})
@TestPropertySource("classpath:application.properties")
public class ReaderTest {

    @Autowired
    private Resources resources;

    @Autowired
    private Reader reader;


}
