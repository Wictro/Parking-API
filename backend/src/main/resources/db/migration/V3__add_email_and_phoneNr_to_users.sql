ALTER TABLE `users`
    ADD COLUMN `email` VARCHAR(45) NOT NULL AFTER `password`,
    ADD COLUMN `phone_number` VARCHAR(45) NULL AFTER `email`;