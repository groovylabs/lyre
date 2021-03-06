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

package com.github.groovylabs.lyre.engine.APIx.swagger.resources;

import io.swagger.models.*;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LyreSwagger extends Swagger {

    public LyreSwagger(String contextPath, String applicationPath) {
        this(null, contextPath, applicationPath);
    }

    public LyreSwagger(String subtitle, String contextPath, String applicationPath) {

        Info info = new Info();

        info.version("1.0");
        info.title("Lyre");
        info.setDescription("**[A development tool to mock REST services.](https://github.com/groovylabs/lyre)**" +
            " <br> " + (StringUtils.isEmpty(subtitle) ? "" : subtitle) +
            " <br> <br> View complete documentation and examples on [our wiki](https://github.com/groovylabs/lyre/wiki)." +
            " <br> [Bug?](https://github.com/groovylabs/lyre/issues)");
        info.setContact(new Contact()
            .name("Groovylabs"));

        info.license(new License()
            .name("MIT").url("https://github.com/groovylabs/lyre/blob/master/LICENSE"));

        this.setInfo(info);
        this.setBasePath(contextPath + "/" + applicationPath);
        this.setSchemes(Stream.of(Scheme.HTTP).collect(Collectors.toList()));
        this.setConsumes(Stream.of("application/json").collect(Collectors.toList()));
        this.setProduces(Stream.of("application/json").collect(Collectors.toList()));
        this.setTags(new ArrayList<>());
        this.getTags().add(new Tag().name("API"));
    }

}
