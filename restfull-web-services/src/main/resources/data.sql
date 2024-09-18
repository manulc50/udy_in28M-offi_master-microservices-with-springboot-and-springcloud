-- Datos de prueba para el entorno de Desarrollo
-- El id no hace falta especificarlo porque se auto-genera debido al uso de la estrategia IDENTITY en la generión de ids usada en las clases entidad "User" y "Post"
INSERT INTO users(name, birth_date) VALUES('AB', sysdate());
INSERT INTO users(name, birth_date) VALUES('Jill', sysdate());
INSERT INTO users(name, birth_date) VALUES('Jam', sysdate());

INSERT INTO posts(description, user_id) VALUES('My First Post', 1);
INSERT INTO posts(description, user_id) VALUES('My Second Post', 1);