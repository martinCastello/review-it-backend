use reviewit;

CREATE TABLE `seq_review` (
	`next_val` BIGINT(20)
) ENGINE=InnoDB;

CREATE TABLE `review` (
	`id` BIGINT(20) NOT NULL,
	`created_by` VARCHAR(255) NULL,
	`created_date` DATETIME NULL,
	`last_modified_by` VARCHAR(255) NULL,
	`last_modified_date` DATETIME NULL,
	`description` VARCHAR(255) NULL,
	`points` INT(11) NOT NULL,
	`title` VARCHAR(50) NULL,
	PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB;
