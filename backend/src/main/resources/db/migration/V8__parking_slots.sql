CREATE TABLE `parking_slots` (
`id` BIGINT NOT NULL AUTO_INCREMENT,
`parking_zone` BIGINT NOT NULL,
`is_handicap` BOOLEAN NOT NULL DEFAULT 0,
`is_free` BOOLEAN NOT NULL DEFAULT 1,
PRIMARY KEY (`id`),
FOREIGN KEY (`parking_zone`) REFERENCES `parking_zones`(`id`)
);