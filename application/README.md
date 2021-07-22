# YARN SHOP APPLICATION

This application is based on the Spring Boot and Vaadin tutorial application source code found here:
[CRM Tutorial](https://github.com/vaadin-learning-center/crm-tutorial)

Apart from some standard Vaadin component there is also a color picker component that has been included:
[Color Picker Field for Flow](https://vaadin.com/directory/component/color-picker-field-for-flow)

The Vaadin GUI has been extended with more functionality and with a connection to a MySql database.

The application runs in a docker container.

The database runs in a separate docker container.

## BUILD DOCKER IMAGE 

To build a production version that can be packaged into a Docker container, run
the following command in this folder:

`mvn -C clean package -Pproduction`

After having run the maven (mvn clean package) command above, 
create a docker image of the application by writing:

`docker image build -t yarn-shop-app-image .`


## CREATE DOCKER CONTAINER

List docker networks with command:

`docker network ls`

If not already exist, create a network named brodera-mera:

`docker network create yarn-shop`

Removing the existing container if it exist:

`docker stop yarn-shop-app`
`docker rm yarn-shop-app`

Note that before running the application as described below, the database docker container must 
have been started first.

Run the new application image on a container:

`docker container run -d -p 8080:8080 --name yarn-shop-app --network yarn-shop  yarn-shop-app-image`

Get a shell:

`docker container exec -it yarn-shop-app bash`

-----------

To see running containers:

`docker ps`

To tail the log file for a container:

`docker logs --follow <ContainerID>`

-----------


## SAVE DOCKER IMAGE TO FILE

When ready, save the docker image created above:

`docker save yarn-shop-app-image > yarn-shop-app-image.tar`

Send them to a receiver.

