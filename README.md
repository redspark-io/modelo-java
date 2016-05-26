Projeto Modelo redspark
========
O projeto modelo tem a ideal de mostrar diversas práticas para ajudar o desenvolvedor na tomada de decisão.
Esse projeto também servirá como base para os demais projetos da redspark.

[![Circle CI](https://circleci.com/gh/redspark-io/modelo-java.svg?style=shield)](https://circleci.com/gh/redspark-io/modelo-java)

[![Coverage Status](https://coveralls.io/repos/github/redspark-io/modelo-java/badge.svg?branch=master)](https://coveralls.io/github/redspark-io/modelo-java?branch=master)

## Note for the Developers
We use Github issues as reference for the project development. So every single commit must reference a GITHUB ISSUE.
To make sure you don't forget to reference any issue, run the command below so git will warn you if you are trying to commit anything without a issue reference.
```curl -o .git/hooks/prepare-commit-msg https://s3.amazonaws.com/holmes-provision/prepare-commit-msg | chmod +x .git/hooks/prepare-commit-msg```


## Required

### Docker
[Link to Installation Section](https://www.docker.com/)

#### Mac OSX
[Link to Installation Section on Mac](https://docs.docker.com/installation/mac/)

Run this once after install docker in your console to add the hostname "http://docker" to you hosts:

### boot2docker
```
IP=$(boot2docker ssh ip addr show eth1 |sed -nEe 's/^[ \t]*inet[ \t]*([0-9.]+)\/.*$/\1/p'); sudo bash -c "echo '' >> /etc/hosts; echo $IP docker >> /etc/hosts"

````
### docker-machine
```
IP=$(docker-machine ip default); sudo bash -c "echo '' >> /etc/hosts; echo $IP docker >> /etc/hosts"

```
### Fig
[Link to Installation Section](http://www.fig.sh/install.html)

## How to Run

| Command   |      Description      |
|----------|:-------------:|
fig up | It will download the images and start the containers necessary to start the application
fig stop | It will stop de containers but will keep the files on your system
fig rm | It will remove the containers files from your system but will keep the images

***
* If you want to run:
```bash
$ boot2docker up
$ fig up
```

* If you want to stop, to do some change in your code or free some memory, run `fig stop`.
* If you want to start fresh, with a clean enviroment, run:
```bash
$ fig stop
$ fig rm
$ fig up
```

# Build

## Requisits
Maven | [http://maven.apache.org/](http://maven.apache.org/)

JDK8 Oracle/Sun | [http://www.oracle.com/technetwork/java/javase/downloads/index.html](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

Lombok | [http://projectlombok.org/](http://projectlombok.org/)

Lombok install:
Run this:
```
java -jar ~/.m2/repository/org/projectlombok/lombok/1.16.2/lombok-1.16.2.jar
```
After run, it's necessary to specify your current Eclipse installation, attention to select de link 'eclipse' in Eclipse installation folder and click Install.

## Front-End

## Back-End
mvn clean install -f server/pom

## Artifacts
server/target/modelo.jar
