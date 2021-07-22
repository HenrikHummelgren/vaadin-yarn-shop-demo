
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";
DROP DATABASE IF EXISTS `YARN_SHOP`;
CREATE DATABASE IF NOT EXISTS `YARN_SHOP` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `YARN_SHOP`;
SET NAMES 'utf8';

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS Product_Yarn;
DROP TABLE IF EXISTS Product;
-- ALTER TABLE `Yarn_Alternative` DROP FOREIGN KEY `Yarn_alt_fk1`;
-- ALTER TABLE `Yarn_Alternative` DROP FOREIGN KEY `Yarn_alt_fk2`;
DROP TABLE IF EXISTS Yarn_Alternative; 
-- ALTER TABLE `Yarn` DROP FOREIGN KEY `Yarn_fk1`;
-- ALTER TABLE `Yarn` DROP FOREIGN KEY `Yarn_fk2`;
DROP TABLE IF EXISTS Yarn;
DROP TABLE IF EXISTS Supplier;
DROP TABLE IF EXISTS BoxInStore;
DROP TABLE IF EXISTS BoxInStudio;
SET FOREIGN_KEY_CHECKS=1;

-- BoxInStore
CREATE TABLE BoxInStore (id int NOT NULL AUTO_INCREMENT, name varchar(30) NOT NULL, description varchar(200), PRIMARY KEY (Id), UNIQUE KEY `box_in_store_name_uq` (`name`)) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_unicode_ci;

-- BoxInStudio
CREATE TABLE BoxInStudio (id int NOT NULL AUTO_INCREMENT, name varchar(30) NOT NULL, description varchar(200), PRIMARY KEY (Id), UNIQUE KEY `box_in_studio_name_uq` (`name`)) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_unicode_ci;

-- Supplier
CREATE TABLE Supplier (id int NOT NULL AUTO_INCREMENT, name varchar(30) NOT NULL, description varchar(200), PRIMARY KEY (Id), UNIQUE KEY `supplier_name_uq` (`name`)) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_unicode_ci;

-- Yarn
CREATE TABLE Yarn (id int NOT NULL AUTO_INCREMENT, yarn_no int NOT NULL UNIQUE, box_in_store_id int, numbers_in_store int,
box_in_studio_id int, numbers_in_studio int, replacement_no int, replacement_company varchar(30), yarn_color varchar(10),
PRIMARY KEY (id)) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_unicode_ci;
ALTER TABLE `Yarn` ADD CONSTRAINT Yarn_fk1 FOREIGN KEY (`box_in_store_id`) REFERENCES `BoxInStore` (`id`);
ALTER TABLE `Yarn` ADD CONSTRAINT Yarn_fk2 FOREIGN KEY (`box_in_studio_id`) REFERENCES `BoxInStudio` (`id`);

-- Yarn alternative
CREATE TABLE Yarn_Alternative (id INT NOT NULL AUTO_INCREMENT, yarn_id INT NOT NULL, supplier_id INT NOT NULL, 
yarn_alt_id VARCHAR(20) NOT NULL, description VARCHAR(200), PRIMARY KEY (id), UNIQUE KEY `yarn_alter_uq` (`yarn_id`,`supplier_id`)) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_unicode_ci;
ALTER TABLE `Yarn_Alternative` ADD CONSTRAINT Yarn_alt_fk1 FOREIGN KEY (`yarn_id`) REFERENCES `Yarn` (`id`);
ALTER TABLE `Yarn_Alternative` ADD CONSTRAINT Yarn_alt_fk2 FOREIGN KEY (`supplier_id`) REFERENCES `Supplier` (`id`);


-- Product
CREATE TABLE Product (id int NOT NULL AUTO_INCREMENT, product_no int NOT NULL UNIQUE, name varchar(30), PRIMARY KEY (id)) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_unicode_ci;

-- Product_Yarn
CREATE TABLE Product_Yarn (id int NOT NULL AUTO_INCREMENT, product_id int NOT NULL, yarn_id int NOT NULL, numbers_used int NOT NULL, PRIMARY KEY (Id), UNIQUE KEY `product_yarn_uq` (`product_id`,`yarn_id`)) 
ENGINE=InnoDB
DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_unicode_ci;
ALTER TABLE `Product_Yarn` ADD CONSTRAINT Product_Yarn_fk1 FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`);
ALTER TABLE `Product_Yarn` ADD CONSTRAINT Product_Yarn_fk2 FOREIGN KEY (`yarn_id`) REFERENCES `Yarn` (`id`);


