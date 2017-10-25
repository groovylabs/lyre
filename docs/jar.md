## Lyre - Endpoint mock tool
###1.0 Walkthrough to run Lyre application (JAR FILE).

___  
#### Installation

To run jar, you need clone and build Lyre project. To do this, follow this steps:
- `git clone https://github.com/groovylabs/lyre.git`
- `cd lyre`
- `mvn install -P release-jar`
___ 
#### Run jar

You can customize your configurations with jar, below is the configuration list we accept (plus [Spring properties](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)):

```
    java -jar -Dlyre.scan-path=<path-to-lyre-files> \ 
    -Dlyre.enable-remote-connections=<boolean> \
    -Dlyre.enable-live-reload=<boolean> \
    -Dlyre.port=<integer> \
    -Dlyre.context-path=<string> \
    -Dlyre.api-path=<string> \
    -Dlyre.file-format=<string> \
    -Dlyre.enable-swagger-doc=<boolean> \
    -Dlyre.debug=<boolean> \
    lyre-<lyre-version>.jar
```
