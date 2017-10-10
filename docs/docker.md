## Lyre - Endpoint mock tool
###1.0 Walkthrough to run Lyre application on Docker.

___
#### Pre-requirements 

* Docker (tested on v17.06.0-ce)
___  
#### Installation

* Pull our image from [DockerHub](https://hub.docker.com/) TODO: put our image on DockerHub
    - `docker pull groovylabs/our-image`

* You also can build the image locally.
    - `git clone https://github.com/groovylabs/lyre.git`
    - `cd lyre`
    - `docker build --tag="$USER/lyre" .`
___ 
#### Default start

You can launch the image using the docker command line:

`docker run -p 8234:8234 -v <host-path>:/lyre/endpoints -it groovylabs/lyre`

Instead of **<host-path>**, input the path if you want to share with Docker (containing endpoint files and properties to Lyre application)
___ 
#### Start with custom properties

If you want to change the default configuration with one custom, you need to put this one inside a **shared folder with the suffix .properties** and use this command:

`docker run -p 8234:8234 -v <host-path>:/lyre/configs -e 'SPRING_CONFIG_LOCATION=/lyre/endpoints/<custom-file>.properties' -it groovylabs/lyre`

Remember, if you change the application port (default is 8234), is necessary change the **-p** argument found on command above.
___ 
#### Acceptable environment variable

You can input your custom configuration with environment variable too, like this command:

```
docker run -p 8234:8234 -v <host-path>:/lyre/endpoints \
    -e 'LYRE_ENABLE_LIVERELOAD=<boolean>' \
    -e 'LYRE_PORT=<integer>' \
    -e 'LYRE_CONTEXT_PATH=<string>' \
    -e 'LYRE_API_PATH=<string>' \
    -e 'LYRE_FILE_FORMAT=<string>' \
    -e 'LYRE_ENABLE_SWAGGER_DOC=<boolean>' \
    -e 'LYRE_DEBUG=<boolean>' \ groovylabs/lyre
```
