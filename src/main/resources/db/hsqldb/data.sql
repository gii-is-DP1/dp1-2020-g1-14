-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');
-- USUARIO DE HEGOA--
INSERT INTO users(username,password,enabled) VALUES ('hegriaher','funky',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'hegriaher','owner');
-- USUARIO DE JAIME--
INSERT INTO users(username,password,enabled) VALUES ('jairamlar','erpepe',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'jairamlar','owner');
-- USUARIO DE CANDELA--
INSERT INTO users(username,password,enabled) VALUES ('canjorbus','jazzy',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'canjorbus','owner');
-- USUARIO DE JACOBO--
INSERT INTO users(username,password,enabled) VALUES ('jacgarvel','adecarry',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'jacgarvel','owner');
-- USUARIO DE JOSE LUIS--
INSERT INTO users(username,password,enabled) VALUES ('josaloroc','tiri',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'josaloroc','owner');
--USUARIO DE BADAYCO--
INSERT INTO users(username,password,enabled) VALUES ('badrijher','fos',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'badrijher','owner');

INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
INSERT INTO owners VALUES (11, 'Hegoa', 'Ria', 'Claudio Boutelou 2', 'Sevilla', '600065504', 'hegriaher');
INSERT INTO owners VALUES (12, 'Jaime', 'Ramos', 'Antonio Machado 22', 'Brenes', '674417856', 'jairamlar');
INSERT INTO owners VALUES (13, 'Candela', 'Jordano', 'Reina Mercedes 63', 'Sevilla', '605664409', 'canjorbus');
INSERT INTO owners VALUES (14, 'Jacobo', 'García', 'Camas 55', 'Sevilla', '684312254', 'jacgarvel');
INSERT INTO owners VALUES (15, 'Jose Luis', 'Alonso', 'Santa Maria Campo', 'Sevilla', '664334312', 'josaloroc');
INSERT INTO owners VALUES (16, 'Badayco', 'Rijo', 'Periodista Ramón Resa', 'Sevilla', '648675654', 'badrijher');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Lala', '2019-04-04', 1, 11);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'Nerón', '2019-04-04', 2, 12);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (16, 'Anzai', '2019-07-13', 3, 13);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (17, 'Dana', '2016-03-14', 2, 14);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (18, 'animal', '2017-04-12', 2, 15);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (19, 'Database', '2000-07-22', 2, 16);


INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO proveedor(id,name,tlf) VALUES (1,'Database','643981298');
INSERT INTO proveedor(id,name,tlf) VALUES (2,'Yinyenhonyi','649983623');
INSERT INTO proveedor(id,name,tlf) VALUES (3,'Nacho','649840037');

INSERT INTO producto(id,name,alergenos,precio) VALUES(1,'Tarta','Lácteos, Huevo y Gluten',6);
INSERT INTO producto(id,name,alergenos,precio) VALUES(2,'Ensalada','Pescado',8);
INSERT INTO producto(id,name,alergenos,precio) VALUES(3,'Mejillones','Moluscos',12.90);

INSERT INTO ingrediente(id,name,stock,medida) VALUES(1,'Nata',10,'L');
