
USE `YARN_SHOP`;
SET NAMES 'utf8';


INSERT INTO BoxInStudio (name) VALUES ("A");
INSERT INTO BoxInStudio (name) VALUES ("B");
INSERT INTO BoxInStudio (name) VALUES ("C");
INSERT INTO BoxInStudio (name) VALUES ("D");
INSERT INTO BoxInStudio (name) VALUES ("E");

INSERT INTO BoxInStore (name) VALUES ("A");
INSERT INTO BoxInStore (name) VALUES ("B");
INSERT INTO BoxInStore (name) VALUES ("C");
INSERT INTO BoxInStore (name) VALUES ("D");
INSERT INTO BoxInStore (name) VALUES ("E");


-- Yarn 
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1000,'678EB8');
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1002,'80C299');
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1004,'C2C280');
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1005,'C4754D');
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1006,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1007,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1008,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1013,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1016,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1017,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1019,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1020,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1021,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1023,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1024,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1025,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1028,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1029,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1031,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1033,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1036,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1038,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1040,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1041,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1042,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1043,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1045,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1047,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1049,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1050,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1052,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1053,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1054,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1055,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1056,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1059,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1060,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1067,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1068,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1069,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1070,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1084,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1087,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1100,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1123,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1138,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1140,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1141,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1143,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1146,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1148,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1149,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1150,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1167,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1170,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1171,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1172,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1175,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1176,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1178,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1181,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1182,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1401,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1411,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1701,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1717,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1718,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1719,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1720,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1730,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1733,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1748,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1751,NULL);
INSERT INTO Yarn(yarn_no,yarn_color) VALUES (1752,NULL);


-- A Studio
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'A'), numbers_in_studio =1 WHERE Yarn_no=1036;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'A'), numbers_in_studio =1 WHERE Yarn_no=1047;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'A'), numbers_in_studio =1 WHERE Yarn_no=1053;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'A'), numbers_in_studio =1 WHERE Yarn_no=1054;

-- B Studio
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'B'), numbers_in_studio =2 WHERE Yarn_no=1004;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'B'), numbers_in_studio =2 WHERE Yarn_no=1005;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'B'), numbers_in_studio =2 WHERE Yarn_no=1006;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'B'), numbers_in_studio =2 WHERE Yarn_no=1016;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'B'), numbers_in_studio =2 WHERE Yarn_no=1017;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'B'), numbers_in_studio =2 WHERE Yarn_no=1025;

-- C Studio
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =1 WHERE Yarn_no=1002;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =1 WHERE Yarn_no=1019;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =1 WHERE Yarn_no=1021;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =1 WHERE Yarn_no=1023;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =1 WHERE Yarn_no=1028;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =1 WHERE Yarn_no=1060;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =1 WHERE Yarn_no=1069;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =1 WHERE Yarn_no=1070;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =1 WHERE Yarn_no=1087;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =1 WHERE Yarn_no=1100;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =1 WHERE Yarn_no=1148;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =1 WHERE Yarn_no=1411;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =1 WHERE Yarn_no=1720;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1029;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1038;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1040;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1041;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1045;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1049;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1050;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1052;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1055;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1056;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1123;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1138;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1140;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1141;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1149;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1167;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1171;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1175;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1176;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1181;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'C'), numbers_in_studio =2 WHERE Yarn_no=1182;


-- D Studio
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'D'), numbers_in_studio =2 WHERE Yarn_no=1401;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'D'), numbers_in_studio =2 WHERE Yarn_no=1701;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'D'), numbers_in_studio =2 WHERE Yarn_no=1730;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'D'), numbers_in_studio =2 WHERE Yarn_no=1733;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'D'), numbers_in_studio =2 WHERE Yarn_no=1748;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'D'), numbers_in_studio =3 WHERE Yarn_no=1043;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'D'), numbers_in_studio =3 WHERE Yarn_no=1059;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'D'), numbers_in_studio =3 WHERE Yarn_no=1068;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'D'), numbers_in_studio =3 WHERE Yarn_no=1146;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'D'), numbers_in_studio =3 WHERE Yarn_no=1178;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'D'), numbers_in_studio =3 WHERE Yarn_no=1717;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'D'), numbers_in_studio =3 WHERE Yarn_no=1719;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'D'), numbers_in_studio =4 WHERE Yarn_no=1172;


-- E Studio
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'E'), numbers_in_studio =1 WHERE Yarn_no=1013;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'E'), numbers_in_studio =2 WHERE Yarn_no=1024;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'E'), numbers_in_studio =2 WHERE Yarn_no=1042;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'E'), numbers_in_studio =3 WHERE Yarn_no=1000;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'E'), numbers_in_studio =3 WHERE Yarn_no=1008;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'E'), numbers_in_studio =3 WHERE Yarn_no=1143;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'E'), numbers_in_studio =3 WHERE Yarn_no=1752;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'E'), numbers_in_studio =4 WHERE Yarn_no=1718;
UPDATE Yarn set box_in_studio_id = (select id from BoxInStudio where name = 'E'), numbers_in_studio =5 WHERE Yarn_no=1020;


-- Kallare A
UPDATE Yarn set box_in_store_id = (select id from BoxInStore where name = 'A'), numbers_in_store =8 WHERE Yarn_no=1008;

-- Kallare B
UPDATE Yarn set box_in_store_id = (select id from BoxInStore where name = 'B'), numbers_in_store =2 WHERE Yarn_no=1067;
UPDATE Yarn set box_in_store_id = (select id from BoxInStore where name = 'B'), numbers_in_store =5 WHERE Yarn_no=1000;

-- Kallare C
UPDATE Yarn set box_in_store_id = (select id from BoxInStore where name = 'C'), numbers_in_store =5 WHERE Yarn_no=1007;
UPDATE Yarn set box_in_store_id = (select id from BoxInStore where name = 'C'), numbers_in_store =5 WHERE Yarn_no=1023;

-- Kallare D
UPDATE Yarn set box_in_store_id = (select id from BoxInStore where name = 'D'), numbers_in_store =5 WHERE Yarn_no=1024;
UPDATE Yarn set box_in_store_id = (select id from BoxInStore where name = 'D'), numbers_in_store =5 WHERE Yarn_no=1084;

-- Kallare E
UPDATE Yarn set box_in_store_id = (select id from BoxInStore where name = 'E'), numbers_in_store =5 WHERE Yarn_no=1143;
UPDATE Yarn set box_in_store_id = (select id from BoxInStore where name = 'E'), numbers_in_store =5 WHERE Yarn_no=1751;
UPDATE Yarn set box_in_store_id = (select id from BoxInStore where name = 'E'), numbers_in_store =15 WHERE Yarn_no=1013;


-- Product
INSERT INTO Product (product_no, name) VALUES (1578, 'Rose, champagne');
INSERT INTO Product (product_no, name) VALUES (2184, 'Amaryllis, red');

-- Product Yarn
INSERT INTO Product_Yarn (product_id, yarn_id, numbers_used) VALUES ((Select id from Product where product_no=1578), (Select id from Yarn where yarn_no=1000), 5);
INSERT INTO Product_Yarn (product_id, yarn_id, numbers_used) VALUES ((Select id from Product where product_no=1578), (Select id from Yarn where yarn_no=1006), 10);
INSERT INTO Product_Yarn (product_id, yarn_id, numbers_used) VALUES ((Select id from Product where product_no=1578), (Select id from Yarn where yarn_no=1020), 5);
INSERT INTO Product_Yarn (product_id, yarn_id, numbers_used) VALUES ((Select id from Product where product_no=1578), (Select id from Yarn where yarn_no=1033), 5);
INSERT INTO Product_Yarn (product_id, yarn_id, numbers_used) VALUES ((Select id from Product where product_no=1578), (Select id from Yarn where yarn_no=1050), 2);

INSERT INTO Product_Yarn (product_id, yarn_id, numbers_used) VALUES ((Select id from Product where product_no=2184), (Select id from Yarn where yarn_no=1000), 5);
INSERT INTO Product_Yarn (product_id, yarn_id, numbers_used) VALUES ((Select id from Product where product_no=2184), (Select id from Yarn where yarn_no=1033), 5);
INSERT INTO Product_Yarn (product_id, yarn_id, numbers_used) VALUES ((Select id from Product where product_no=2184), (Select id from Yarn where yarn_no=1167), 10);
INSERT INTO Product_Yarn (product_id, yarn_id, numbers_used) VALUES ((Select id from Product where product_no=2184), (Select id from Yarn where yarn_no=1171), 5);

INSERT INTO Supplier (name) VALUES ("Alice handycraft");
INSERT INTO Supplier (name) VALUES ("Bob artisan");
INSERT INTO Supplier (name) VALUES ("Foobar Yarns Ltd");

INSERT INTO Yarn_Alternative (yarn_id, supplier_id, yarn_alt_id, description) VALUES ((Select id from Yarn where yarn_no=1000), (Select id from Supplier where name='Alice handycraft'), 'BV-123', 'Test');
INSERT INTO Yarn_Alternative (yarn_id, supplier_id, yarn_alt_id, description) VALUES ((Select id from Yarn where yarn_no=1033), (Select id from Supplier where name='Bob artisan'), 'K-1234', 'Antother test');
INSERT INTO Yarn_Alternative (yarn_id, supplier_id, yarn_alt_id, description) VALUES ((Select id from Yarn where yarn_no=1019), (Select id from Supplier where name='Foobar Yarns Ltd'), 'FB-1234', 'Second supplier for more information call ...');

