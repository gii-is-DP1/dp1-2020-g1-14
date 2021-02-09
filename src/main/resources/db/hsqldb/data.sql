-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled,r_Date) VALUES ('admin1','4dm1n',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled,r_Date) VALUES ('owner1','0wn3r',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled,r_Date) VALUES ('vet1','v3t',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');
-- USUARIO DE HEGOA--
INSERT INTO users(username,password,enabled,r_Date) VALUES ('hegriaher','funky',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (4,'hegriaher','admin');
-- USUARIO DE JAIME--
INSERT INTO users(username,password,enabled,r_Date) VALUES ('jairamlar','erpepe',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (5,'jairamlar','admin');
-- USUARIO DE CANDELA--
INSERT INTO users(username,password,enabled,r_Date) VALUES ('canjorbus','jazzy',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (6,'canjorbus','admin');
-- USUARIO DE JACOBO--
INSERT INTO users(username,password,enabled,r_Date) VALUES ('jacgarvel','adecarry',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (7,'jacgarvel','admin');
-- USUARIO DE JOSE LUIS--
INSERT INTO users(username,password,enabled,r_Date) VALUES ('josaloroc','tiri',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (8,'josaloroc','admin');
--USUARIO DE BADAYCO--
INSERT INTO users(username,password,enabled,r_Date) VALUES ('badrijher','fos',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (9,'badrijher','admin');

INSERT INTO proveedor(id,name,tlf,version) VALUES (1,'Database','643981298',1);
INSERT INTO proveedor(id,name,tlf,version) VALUES (2,'Yinyenhonyi','649983623',1);
INSERT INTO proveedor(id,name,tlf,version) VALUES (3,'Nacho','649840037',1);

INSERT INTO users(username,password,enabled,r_Date) VALUES ('cliente1','cliente1',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (10,'cliente1','cliente');
INSERT INTO cliente(es_Socio,num_Pedidos,tlf,monedero,username) VALUES (false,'12','954765812',300,'cliente1');
INSERT INTO users(username,password,enabled,r_Date) VALUES ('cliente2','cliente2',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (11,'cliente2','cliente');
INSERT INTO cliente(es_Socio,num_Pedidos,tlf,monedero,username) VALUES (true,'12','954357811',100,'cliente2');
INSERT INTO users(username,password,enabled,r_Date) VALUES ('cliente3','cliente3',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (12,'cliente3','cliente');
INSERT INTO cliente(es_Socio,num_Pedidos,tlf,monedero,username) VALUES (false,'11','954736516',30,'cliente3');
INSERT INTO users(username,password,enabled,r_Date) VALUES ('cliente4','cliente4',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (13,'cliente4','cliente');
INSERT INTO cliente(es_Socio,num_Pedidos,tlf,monedero,username) VALUES (false,'6','954736516',0,'cliente4');


INSERT INTO users(username,password,enabled,r_Date) VALUES ('gerente1','gerente1',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (14,'gerente1','gerente');
INSERT INTO gerente(id,name,dni,username) VALUES(1,'nombre1','12345678F','gerente1');
INSERT INTO users(username,password,enabled,r_Date) VALUES ('gerente2','gerente2',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (15,'gerente2','gerente');
INSERT INTO gerente(id,name,dni,username) VALUES(2,'nombre2','12345678G','gerente2');
INSERT INTO users(username,password,enabled,r_Date) VALUES ('gerente3','gerente3',TRUE,'2020-01-01');
INSERT INTO authorities(id,username,authority) VALUES (16,'gerente3','gerente');
INSERT INTO gerente(id,name,dni,username) VALUES(3,'nombre3','12345678G','gerente3');


INSERT INTO restaurante(id,name,tipo,localizacion,aforomax,senial,gerente_id,version) VALUES(1,'Restaurante 1','Chino','Reina Mercedes, 34',25,20,1,1);
INSERT INTO restaurante(id,name,tipo,localizacion,aforomax,senial,gerente_id,version) VALUES(2,'Restaurante 2','Italiaco','Avd. de la palmera, 100',30,13,2,1);
INSERT INTO restaurante(id,name,tipo,localizacion,aforomax,senial,gerente_id,version) VALUES(3,'Restaurante 3','Mexicano','Avd. nombe avenida, 14',20,0,3,1);

INSERT INTO proveedores(proveedor_id,restaurante_id) VALUES (1,1);
INSERT INTO proveedores(proveedor_id,restaurante_id) VALUES (2,1);
INSERT INTO proveedores(proveedor_id,restaurante_id) VALUES (2,2);

INSERT INTO producto(id,name,alergenos,precio,restaurante_id,version) VALUES(1,'Tarta','Lacteos, Huevo y Gluten',6,1,1);
INSERT INTO producto(id,name,alergenos,precio,restaurante_id,version) VALUES(2,'Ensalada','Pescado',8,1,1);
INSERT INTO producto(id,name,alergenos,precio,restaurante_id,version) VALUES(3,'Mejillones','Moluscos',12.90,2,1);
INSERT INTO producto(id,name,alergenos,precio,restaurante_id,version) VALUES(4,'Cacahuetes','Frutos secos',5,3,1);
/*Usado para pruebas en LineaPedido*/---------------------
INSERT INTO producto(id,name,alergenos,precio,restaurante_id,version) VALUES(5,'Huevos','Frutos secos',10,3,1);
---------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO reclamacion(id,fecha,descripcion,restaurante_id) VALUES(1,'2020-10-28','Mal trato por parte del camarero.',1);
INSERT INTO reclamacion(id,fecha,descripcion,restaurante_id) VALUES(2,'2020-11-15','Comida en mal estado.',2);


INSERT INTO ingrediente(id,name,stock,medida,restaurante_id,version) VALUES(1,'Nata',10,'L', 1,1);
INSERT INTO ingrediente(id,name,stock,medida,restaurante_id,version) VALUES(2,'Cajas de tomates',5,'UNIDAD', 2,1);
INSERT INTO ingrediente(id,name,stock,medida,restaurante_id,version) VALUES(3,'Harina',17,'KG', 3,1);

INSERT INTO ingredientes(proveedor_id,ingrediente_id) VALUES (1,1);
INSERT INTO ingredientes(proveedor_id,ingrediente_id) VALUES (2,1);
INSERT INTO ingredientes(proveedor_id,ingrediente_id) VALUES (2,2);

INSERT INTO productos(producto_id,ingrediente_id) VALUES (1,1);
INSERT INTO productos(producto_id,ingrediente_id) VALUES (3,2);
/*Usado para pruebas también*/ ---------------------------------------------------------------------------------------------
INSERT INTO pedido(id,adress,checkea,estado,order_date,price,cliente_id,restaurante_id,version) VALUES(1,'Calle A',true,'PROCESANDO','2020-08-13',17.3,1,1,1);
INSERT INTO linea_pedido(id,cantidad,pedido_id,producto_id) VALUES (1,2,1,1);
INSERT INTO linea_pedido(id,cantidad,pedido_id,producto_id) VALUES (5,3,1,2);
----------------------------------------------------------------------------------------------------------------------------

INSERT INTO oferta(id,descripcion,descuento,exclusivo,min_price,restaurante_id,version) VALUES(1,'Descuento 3 euros',3.0,false,13.0,1,1);
INSERT INTO oferta(id,descripcion,descuento,exclusivo,min_price,restaurante_id,version) VALUES(2,'Descuento 5 euros',5.0,false,15.0,1,1);
INSERT INTO oferta(id,descripcion,descuento,exclusivo,min_price,restaurante_id,version) VALUES(3,'Descuento 1 euro',1.0,true,11.0,1,1);
INSERT INTO oferta(id,descripcion,descuento,exclusivo,min_price,restaurante_id,version) VALUES(4,'Descuento 15 euros',15.0,true,30.0,1,1);

INSERT INTO pedido(id,adress,checkea,estado,order_date,price,cliente_id,restaurante_id,version,oferta_id) VALUES(2,'Calle B',true,'EN_REPARTO','2020-04-14',20.4,2,2,1,1);
INSERT INTO linea_pedido(id,cantidad,pedido_id,producto_id) VALUES (2,1,2,4);

INSERT INTO pedido(id,adress,checkea,estado,order_date,price,cliente_id,restaurante_id,version) VALUES(3,'Calle C',true,'RECIBIDO','2020-11-19',16.4,3,3,1);
INSERT INTO linea_pedido(id,cantidad,pedido_id,producto_id) VALUES (3,13,3,2);

INSERT INTO pedido(id,adress,checkea,estado,order_date,price,cliente_id,restaurante_id,version) VALUES(4,'Calle D',true,'PROCESANDO','2020-09-03',11.5,4,3,1);
INSERT INTO linea_pedido(id,cantidad,pedido_id,producto_id) VALUES (4,14,4,3);

INSERT INTO reserva(id,fecha,hora_inicio,hora_fin,evento,n_personas,restaurante_id,cliente_id) VALUES(1,'2000-10-22','12:00','15:00',false,5,1,1);
INSERT INTO reserva(id,fecha,hora_inicio,hora_fin,evento,n_personas,restaurante_id,cliente_id) VALUES(2,'2000-11-01','13:15','20:45',true,20,1,1);
INSERT INTO reserva(id,fecha,hora_inicio,hora_fin,evento,n_personas,restaurante_id,cliente_id) VALUES(3,'2000-09-30','20:00','22:00',false,4,2,2);