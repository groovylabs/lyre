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

package com.github.groovylabs.lyre.config;

import com.github.groovylabs.lyre.EnableLyre;
import com.github.groovylabs.lyre.Lyre;
import com.github.groovylabs.lyre.utils.RunnerUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

/**
 * LyreRunner implementation of {@link ApplicationRunner} interface.
 *
 * This class actual creates a Lyre spring boot application, set the main application class and prepare eventual
 * arguments to be passed.
 */
public class LyreRunner implements ApplicationRunner {

    public final static String LYRE_PROPERTIES_PREFIX = "lyre";

    @Override
    public void run(ApplicationArguments args) throws Exception {

        SpringApplication app = new SpringApplication(Lyre.class);
        String[] arguments = RunnerUtils.buildArguments(app.getMainApplicationClass().getAnnotation(EnableLyre.class));
        app.setMainApplicationClass(Lyre.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(arguments);
    }

}
