package com.github.groovylabs.lyre;

import com.github.groovylabs.lyre.config.LyreConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Documented
@Import(LyreConfiguration.class)
public @interface LyreApplication {

}
