package com.github.groovylabs.lyre;

import com.github.groovylabs.lyre.config.LyreRunner;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LyreRunner.class)
public @interface EnableLyre {

    String enableRemoteConnections() default "";

    String enableLivereload() default "";

    int port() default 0;

    String contextPath() default "";

    String apiPath() default "";

    String scanPath() default "";

    String fileFormat() default "";

    String enableSwaggerDoc() default "";

    String debug() default "";

}
