SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema georgiy_kovalenko_contact_list
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema georgiy_kovalenko_contact_list
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `georgiy_kovalenko_contact_list` DEFAULT CHARACTER SET utf8 ;
USE `georgiy_kovalenko_contact_list`;

-- -----------------------------------------------------
-- Table `georgiy_kovalenko_contact_list`.`marital_statuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `georgiy_kovalenko_contact_list`.`marital_statuses` (
  `id_marital_status` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id_marital_status`))
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;


-- -----------------------------------------------------
-- Table `georgiy_kovalenko_contact_list`.`contacts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `georgiy_kovalenko_contact_list`.`contacts` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(30) NOT NULL,
  `last_name` VARCHAR(30) NOT NULL,
  `middle_name` VARCHAR(30) NULL,
  `birth_date` DATE NULL,
  `nationality` VARCHAR(45) NULL,
  `gender` VARCHAR(1) NULL,
  `marital_status` TINYINT UNSIGNED NULL,
  `website` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `job` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  INDEX `marital_status_key_idx` (`marital_status` ASC),
  CONSTRAINT `marital_status_key`
    FOREIGN KEY (`marital_status`)
    REFERENCES `georgiy_kovalenko_contact_list`.`marital_statuses` (`id_marital_status`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;


-- -----------------------------------------------------
-- Table `georgiy_kovalenko_contact_list`.`addresses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `georgiy_kovalenko_contact_list`.`addresses` (
  `contact_id` INT UNSIGNED NOT NULL,
  `country` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `street` VARCHAR(45) NULL,
  `house_number` VARCHAR(10) NULL,
  `flat_number` VARCHAR(10) NULL,
  `postcode` VARCHAR(10) NULL,
  PRIMARY KEY (`contact_id`),
  CONSTRAINT `contact_id_addr_key`
    FOREIGN KEY (`contact_id`)
    REFERENCES `georgiy_kovalenko_contact_list`.`contacts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;


-- -----------------------------------------------------
-- Table `georgiy_kovalenko_contact_list`.`phone_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `georgiy_kovalenko_contact_list`.`phone_type` (
  `id_phone_type` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id_phone_type`))
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;


-- -----------------------------------------------------
-- Table `georgiy_kovalenko_contact_list`.`phones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `georgiy_kovalenko_contact_list`.`phones` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `country_code` VARCHAR(5) NULL,
  `operator_code` VARCHAR(5) NULL,
  `number` VARCHAR(7) NULL,
  `phone_type` TINYINT UNSIGNED NULL,
  `comment` VARCHAR(255) NULL,
  `contact_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `contact_phone_id_key_idx` (`contact_id` ASC),
  INDEX `phone_type_id_key_idx` (`phone_type` ASC),
  CONSTRAINT `phone_type_id_key`
    FOREIGN KEY (`phone_type`)
    REFERENCES `georgiy_kovalenko_contact_list`.`phone_type` (`id_phone_type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `contact_phone_id_key`
    FOREIGN KEY (`contact_id`)
    REFERENCES `georgiy_kovalenko_contact_list`.`contacts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;


-- -----------------------------------------------------
-- Table `georgiy_kovalenko_contact_list`.`attachments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `georgiy_kovalenko_contact_list`.`attachments` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `file_name` VARCHAR(255) NOT NULL,
  `upload_date` DATE NULL,
  `comment` VARCHAR(255) NULL,
  `contact_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `contact_attachment_id_key_idx` (`contact_id` ASC),
  CONSTRAINT `contact_attachment_id_key`
    FOREIGN KEY (`contact_id`)
    REFERENCES `georgiy_kovalenko_contact_list`.`contacts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `georgiy_kovalenko_contact_list`.`marital_statuses`
-- -----------------------------------------------------
START TRANSACTION;
USE `georgiy_kovalenko_contact_list`;
INSERT INTO `georgiy_kovalenko_contact_list`.`marital_statuses` (`id_marital_status`, `name`) VALUES ('1', 'Холост/Не замужем');
INSERT INTO `georgiy_kovalenko_contact_list`.`marital_statuses` (`id_marital_status`, `name`) VALUES ('2', 'Женат/Замужем');
INSERT INTO `georgiy_kovalenko_contact_list`.`marital_statuses` (`id_marital_status`, `name`) VALUES ('3', 'Гражданский брак');
COMMIT;

-- -----------------------------------------------------
-- Data for table `georgiy_kovalenko_contact_list`.`phone_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `georgiy_kovalenko_contact_list`;
INSERT INTO `georgiy_kovalenko_contact_list`.`phone_type` (`id_phone_type`, `name`) VALUES ('1', 'Домашний');
INSERT INTO `georgiy_kovalenko_contact_list`.`phone_type` (`id_phone_type`, `name`) VALUES ('2', 'Мобильный');
COMMIT;

