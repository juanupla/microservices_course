INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('juanupla', '$2a$10$cLlqr6wBDr1WAcuET2CFPedlRSFzAOM8nP7GvXin0VDBXj2vDpGJy', 1, 'Juan', 'Cremona', 'juan_crub@outlook.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('admin', '$2a$10$SbhoUfS5WZbzyqSw6I7EBeJI0J83PMac4OGlwsuthyqZdhMnc62Ea', 1, 'Rodri', 'Bertero', 'duchinElMata@gmail.com');
INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');
--  roles a usuarios
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES ((SELECT id FROM usuarios WHERE username = 'juanupla'), (SELECT id FROM roles WHERE nombre = 'ROLE_USER'));
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES ((SELECT id FROM usuarios WHERE username = 'juanupla'), (SELECT id FROM roles WHERE nombre = 'ROLE_ADMIN'));
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES ((SELECT id FROM usuarios WHERE username = 'admin'), (SELECT id FROM roles WHERE nombre = 'ROLE_ADMIN'));