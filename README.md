# VAADIN YARN SHOP DEMO

Vaadin Yarn Shop Demo is a Vaadin application running in Spring Boot together with a MySql database. It shows the number of yarns in stock and also the relations between products and different types of yarn.

This Vaadin application is based on the Spring Boot and Vaadin tutorial application source code found here:
[CRM Tutorial](https://github.com/vaadin-learning-center/crm-tutorial)

The Vaadin version used is 14. Apart from standard Vaadin components used there is also a color picker component that has been included. This is based on this project:
[Color Picker Field for Flow](https://vaadin.com/directory/component/color-picker-field-for-flow)

The GUI has been extended with more functionality and with a connection to a MySql database.

Among the new functionality is links between the different options in the drawer on the lefthand side. You go to the linked object by double-clicking on the row. There is also an explicit link from Yarn to Alternative Yarn which is shown as a separate column in the table.

Both the application and the MySql database are built as docker containers. For more information on how to build the application and database parts, see the README files in their folders.

You need the following development tools to build and deploy as described in the documentation:
 * Java JDK 11 or later
 * Maven 3
 * Docker

To develop the applications you need IntelliJ IDEA or another IDE that supports Java.

Below is described how to deploy them on a server.


## DEPLOY RECEIVED DOCKER IMAGES

You can either create the docker images if build locally or you can import them from file with the following commands:
`docker load < yarn-shop-db-image.tar`
`docker load < yarn-shop-app-image.tar`
 
Verify that the images has been imported with command:

`docker images`

List docker networks with command:

`docker network ls`

If not already exist, create a network named yarn-shop:

`docker network create yarn-shop`


### DB
Start the yarn-shop-db-image image as yarn-shop-db: 

`docker container run -d -p 3306:3306 --name yarn-shop-db --network yarn-shop -v mysql-storage:/var/lib/mysql  yarn-shop-db-image`

Now we have created a database. But our database does not contain any data inside it or we need to re-create the database. For that we run the command:
`docker container exec -it yarn-shop-db bash`

When the shell is opened, type command below and then give password ("password") to delete an existing DB schema and create a new:

`mysql -uroot -p < YARN_SHOP_DDL.sql`

After previous command has been executed, run the following command to populate the DB with all Yarns and Products:

`mysql -uroot -p < YARN_SHOP_DML.sql`

The DB schema "YARN_SHOP" has now been updated. Type "exit" to quit from the shell.


### APPLICATION
Start the yarn-shop-app-image image as yarn-shop-app: 

`docker container run -d -p 8080:8080 --name yarn-shop-app --network yarn-shop  yarn-shop-app-image`

You should then be able to open the login page http://localhost:8080 as "shopowner" with password "secret_1337"

