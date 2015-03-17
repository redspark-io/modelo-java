-- Create syntax for TABLE 'city'
CREATE TABLE `city` (
  `city_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city_country` varchar(100) NOT NULL,
  `city_name` varchar(200) NOT NULL,
  `city_state` varchar(100) NOT NULL,
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'hotel'
CREATE TABLE `hotel` (
  `hote_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hote_address` varchar(255) NOT NULL,
  `hote_tipo` varchar(255) NOT NULL,
  `hote_zip` varchar(255) NOT NULL,
  `city_id` bigint(20) NOT NULL,
  PRIMARY KEY (`hote_id`),
  KEY `FK_CITY_HOTEL` (`city_id`),
  CONSTRAINT `FK_CITY_HOTEL` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'user'
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_admin` bit(1) DEFAULT NULL,
  `user_login` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_LOGIN` (`user_login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;