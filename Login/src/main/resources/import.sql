-- TABLA USUARIO PARA LOGIN
insert into usuario (nombres, password, correo, bloqueado) values ('ADMINISTRADOR', '$2a$10$SkC3uBqo/kHIHF3QpJptt.itYzioqahNCswvYxd0./oLqpfoikKwu', 'admin@mingetal.cl', false);
insert into usuario (nombres, password, correo, bloqueado) values ('Waldo Santibañez', '$2a$10$eWIxfQFarHUMyEiIHXHmsersS98MnoXUzmgptVOAqLYzr9/tdC9H.', 'waldo@mingetal.cl', false);

--La contraseña desencriptada de admin@mingetal.cl es: admin
--La contraseña desencriptada de waldo@mingetal.cl es: waldo123
