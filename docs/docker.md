## Lyre - Endpoint mock tool
###1.0 Walkthrough to run Lyre application on Docker.

___
#### Pre-requirements 

* Docker (tested on v17.06.0-ce)
___  
#### Installation

* Pull our imagem from [DockerHub](https://hub.docker.com/) TODO: put our image on DockerHub
    - `docker pull groovylabs/our-image`

* You also can build the image locally.
    - `git clone https://github.com/groovylabs/lyre.git`
    - `cd lyre`
    - `docker build --tag="$USER/lyre" .`
___ 
#### Quick start

You can launch the image using the docker command line:

`docker run -P -v <host-path>:/lyre/configs -it jar-teste`

 

