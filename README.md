# Lyre - A development tool to mock REST services.

[![Build Status](https://travis-ci.org/groovylabs/lyre.svg?branch=master)](https://travis-ci.org/groovylabs/lyre)
[![GitHub release](https://img.shields.io/badge/download-latest-brightgreen.svg)](https://github.com/groovylabs/lyre/releases)
[![Gitter](https://badges.gitter.im/groovylabs-lyre/Lobby.svg)](https://gitter.im/groovylabs-lyre/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

<img align="right" height="256" src="https://github.com/groovylabs/lyre-logo/blob/master/logo/lyre_white_bd.png">
    
---

Lyre is a project designated to help developers as a REST services mock API. Our name references the lyrebird species, which can mimic natural and artificial sounds from their environment. Analogously, Lyre application provides multiples ways to create, edit and expose endpoints for development/test purpose.

**View complete docs on [our wiki](https://github.com/groovylabs/lyre/wiki)**.

## Getting Started

### Requirements 
* Java 8 or superior.

### Getting Lyre

You can download on github or build by yourself.

- **Download Lyre at: [available releases](https://github.com/groovylabs/lyre/releases)**   
*or*
- **Getting source code**
    ```sh
    $    git clone https://github.com/groovylabs/lyre.git
    $    cd lyre
    ```
    - **Build and use as dependency:**
        ```sh
        $   ./mvnw clean install
        ```
        
        *with maven:*
        ```
        <dependency>
            <groupId>com.github.groovylabs</groupId>
            <artifactId>lyre</artifactId>
            <version>0.0.1-RELEASE</version>
        </dependency>
        ```
        
        *with gradle:*
        ```
        compile group: 'com.github.groovylabs', name: 'lyre', version: '0.0.1-RELEASE'
        ``` 
        
    - **Build jar and import manually:**
        ```sh
        $   ./mvnw clean install -P release-jar
        ```  


### With Spring Boot

Add ```@EnableLyre``` annotation on your Spring Boot application to start it.   
 
```
@EnableLyre
@SpringBootApplication
public class YourApp {
    public static void main(String[] args) {
        SpringApplication.run(YourApp.class);
    }
}
```

### With Docker

Run command below to run Lyre:

`docker run -p <your-port>:8080 -v <your-path>:/lyre/shared -it groovylabs/lyre`

Instead of **<your-path>**, input the path if you want to share with Docker (containing endpoint files and properties to Lyre application)

If you want more information about running Lyre with Docker, see [this](https://github.com/groovylabs/lyre/wiki/Running-Docker).

### With JAR

To run application with default configurations, run:

`java -jar -Dlyre.scan-path=<path-to-lyre-files> lyre-<lyre-version>-standalone.jar`

If you want more information about running Lyre with JAR, see [this](https://github.com/groovylabs/lyre/wiki/Running-JAR).

## Usage

### Using files to create endpoints

First create a file with ```.lyre``` suffix.   
NOTE: It is possible to change which file format lyre will scan by setting ```lyre.file-format``` property.   

Lyre supports two data formats: **YAML** and **JSON**, you can choose whatever you prefer or combine them across files.

```
/dir
    - yaml_endpoints.lyre
    - json_endpoints.lyre
```   

Do not forget to set ```lyre.scan-path``` property to endpoint's directory.

### Creating a endpoint

**Hello world!**   
**YAML:** 
```
GET /hello:
    response:
        data: Hello World!
        status: 200
```
**JSON:**
```
{
    "GET /hello" : {
        "response" : {
            "data" : "Hello World!",
            "status" : "200"
        }
    }
}
```

Check our [lyre syntax](https://github.com/groovylabs/lyre/wiki/Endpoint-syntax) to learn more about supported properties.
Also check our [examples]().

## Configuring

Use our preffix: *lyre.*

### Supported configuration

Name | Property | Default value   
------------ | ------------ | -------------
Enable remote connections | enable-remote-connection | false
Enable live reload | enable-live-reload | false
Lyre application port | port | 8234
Path to scan files | scan-path | $USER.DIR
File suffix extension | file-format | .lyre
Servlet context path | context-path | *none*
Lyre Application path | application-path | api

#### Using environment variables

```
$   export LYRE_PORT=8080 
$   export LYRE_ENABLE_REMOTE_CONNECTIONS=true
```

#### Using application.properties

```
$   echo 'lyre.port=8080' >> application.properties
$   echo 'lyre.enable_remote_connection=true' >> application.properties
```

#### Using @EnableLyre interface

```
@EnableLyre(port = 8080, enableRemoteConnections = "true")
```

## Support Channels

* [Mailing list](https://groups.google.com/forum/#!forum/groovylabs-lyre) - groovylabs-lyre@googlegroups.com
* [Gitter chat](https://gitter.im/groovylabs-lyre/Lobby)

## Contributing

We welcome [contributions](https://github.com/groovylabs/lyre/graphs/contributors) of all kinds from anyone. Please take a moment to review the [guidelines for contributing](CONTRIBUTING.md).

* [Bug reports](https://github.com/groovylabs/lyre/wiki/Report-a-Bug)
* [Feature requests](CONTRIBUTING.md#features)
* [Pull requests](CONTRIBUTING.md#pull-requests)

## License

Copyright (c) 2017 Groovylabs and [other contributors](https://github.com/groovylabs/lyre/graphs/contributors)

Licensed under the MIT License
