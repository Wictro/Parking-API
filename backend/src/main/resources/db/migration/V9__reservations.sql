CREATE TABLE `reservations` (
`id` BIGINT NOT NULL AUTO_INCREMENT,
`parking_slot` BIGINT NOT NULL,
`user_id` BIGINT NOT NULL,
`time_from` DATETIME NOT NULL,
`time_to` DATETIME NOT NULL,
`price` DECIMAL(10, 2) NOT NULL,
`time_created` DATETIME NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`parking_slot`) REFERENCES `parking_slots`(`id`),
FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);