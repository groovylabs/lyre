package com.github.groovylabs.lyre.test;


import com.github.groovylabs.lyre.test.configurations.LyrePropertiesConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Import({
    LyrePropertiesConfiguration.class
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class APIxTest {

    @Test
    public void apixTest() {



    }

}
