
## BUILD DOCKER IMAGE 

In the database folder, write:

`docker image build -t yarn-shop-db-image .`

A docker image of the database has now been created.


## RUN DOCKER CONTAINER

First lets create a network named yarn-shop. List docker networks with command:

`docker network ls`

If not already exist, create a network named yarn-shop:

`docker network create yarn-shop`

Removing the existing container if it exist:

`docker stop yarn-shop-db`
`docker rm yarn-shop-db`

Use the image that has been created/imported to run as container. 

`docker container run -d -p 3306:3306 --name yarn-shop-db --network yarn-shop -v mysql-storage:/var/lib/mysql  yarn-shop-db-image`

Create or update the database by running run the command:

`docker container exec -it yarn-shop-db bash`

### If installing for first time

When the shell is opened, type command below and then give password ("password") to delete an existing DB schema and create a new:

`mysql -uroot -p < YARN_SHOP_DDL.sql`

After previous command has been executed, run the following command to populate the DB with some Yarns and Products:

`mysql -uroot -p < YARN_SHOP_DML.sql`


The DB schema "YARN_SHOP" has now been created and filled with data. Type "exit" to quit from the shell.


-----------

To see running containers:

`docker ps`

To tail the log file for a container:

`docker logs --follow <ContainerID>`

-----------


## SAVE DOCKER IMAGE AS FILE

When ready, the docker image can be sent to a file:

`docker save yarn-shop-db-image > yarn-shop-db-image.tar`

This file can be sent to a receiver.

