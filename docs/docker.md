## Lyre - Endpoint mock tool
###1.0 Walkthrough to run Lyre application on Docker.

___
#### Pre-requirements 

* Docker (tested on v17.06.0-ce)
___  
#### Installation

* Pull our image from [DockerHub](https://hub.docker.com/)
    - `docker pull groovylabs/lyre`

* You also can build the image locally.
    - `git clone https://github.com/groovylabs/lyre.git`
    - `cd lyre`
    - `mvn install -P release-jar`
    - `docker build -t groovylabs/lyre .`
___ 
#### Start with custom properties

If you want to change the default configuration with one custom, you need to put this one inside a **shared folder with the suffix .properties** and use this command:

`docker run -p <your-port>:8080 -v <host-path>:/lyre/configs -e 'SPRING_CONFIG_LOCATION=/lyre/endpoints/<custom-file>.properties' -it groovylabs/lyre`

Remember, don't change the application port neither spring active profile. We run default configurations when starts application with Docker!
___ 
#### Acceptable environment variable

You can input your custom configuration with environment variable too, like this command:

```
docker run -p <your-port>:8080 -v <host-path>:/lyre/endpoints \
    -e 'LYRE_ENABLE_LIVERELOAD=<boolean>' \
    -e 'LYRE_CONTEXT_PATH=<string>' \
    -e 'LYRE_API_PATH=<string>' \
    -e 'LYRE_FILE_FORMAT=<string>' \
    -e 'LYRE_ENABLE_SWAGGER_DOC=<boolean>' \
    -e 'LYRE_DEBUG=<boolean>' \ 
    groovylabs/lyre
```
