CREATE DATABASE `modelo`
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_unicode_ci
;

create user 'modelo'@'%' identified by 'modelo'
;

grant all privileges on modelo.* to 'modelo'@'%' with grant option
;

-- use modelo;

-- CREATE TABLE `user` (
--   `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `user_admin` bit(1) DEFAULT NULL,
--   `user_login` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
--   `user_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
--   `user_password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
--   PRIMARY KEY (`user_id`),
--   UNIQUE KEY `UK_LOGIN` (`user_login`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- INSERT INTO `user` (`user_id`, `user_admin`, `user_login`, `user_name`, `user_password`)
-- VALUES
--   (1, true, 'admin', 'Administrador', 'admin');

