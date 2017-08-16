package io.groovelabs.lyre;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Documented
@Import(LyreConfiguration.class)
public @interface LyreApplication {

}
