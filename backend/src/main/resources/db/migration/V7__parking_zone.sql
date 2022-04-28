CREATE TABLE `parking_zones` (
`id` BIGINT NOT NULL AUTO_INCREMENT,
`city` BIGINT NOT NULL,
`name` VARCHAR(25) NOT NULL,
`location_lat` VARCHAR(20),
`location_lng` VARCHAR(20),
`is_active` BOOLEAN DEFAULT 0,
PRIMARY KEY (`id`),
FOREIGN KEY (`city`) REFERENCES `cities`(`id`),
UNIQUE KEY `zone_in_city_UNIQUE` (`name`, `city`)
);