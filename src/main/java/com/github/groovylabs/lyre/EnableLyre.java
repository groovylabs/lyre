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

import com.github.groovylabs.lyre.config.LyreRunner;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * EnableLyre Interface - Indicates to spring that a particular {@link org.springframework.boot.ApplicationRunner}
 * implementation needs to be instantiated and called {@code #run}.
 * <p>
 * The desired main class that is annotated with {@code @EnableLyre} together with {@code @SpringBootApplication},
 * acquires a significance meaning, it allows a bean creation of {@link LyreRunner} and a {@link Lyre}
 * instance that should run.
 * <p>
 * This interface also provides a property configuration thought it.
 * {@code enableRemoteConnections}
 * {@code enableLiveReload}
 * {@code port}
 * {@code contextPath}
 * {@code apiPath}
 * {@code scanPath}
 * {@code fileFormat}
 * {@code enableSwaggerDoc}
 * {@code debug}
 *
 * @see LyreRunner
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LyreRunner.class)
public @interface EnableLyre {

    String enableRemoteConnections() default "";

    String enableLiveReload() default "";

    int port() default 0;

    String contextPath() default "";

    String apiPath() default "";

    String scanPath() default "";

    String fileFormat() default "";

    String enableSwaggerDoc() default "";

    String debug() default "";

}
