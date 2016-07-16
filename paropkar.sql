
CREATE SCHEMA IF NOT EXISTS `paropkar` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Table `paropkar`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paropkar`.`user` (
  `username` VARCHAR(32) NOT NULL,
  `email` VARCHAR(64) NULL,
  `password` VARCHAR(32) NOT NULL,
  `aadhaar_number` VARCHAR(16) NULL,
  `city` VARCHAR(16) NULL,
  `address` TEXT(256) NULL,
  `phone_number` VARCHAR(16) NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id` BIGINT(16) NOT NULL,
  `twitter_handle` VARCHAR(64) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `aadhaar_number_UNIQUE` (`aadhaar_number` ASC));


-- -----------------------------------------------------
-- Table `paropkar`.`follower`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paropkar`.`follower` (
  `user_id` BIGINT(16),
  `follower_id` BIGINT(16),
  PRIMARY KEY (`user_id`, `follower_id`));


-- -----------------------------------------------------
-- Table `paropkar`.`complaint`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paropkar`.`complaint` (
  `id` INT NOT NULL DEFAULT 0,
  `title` VARCHAR(64) NOT NULL DEFAULT '',
  `content` VARCHAR(512) NOT NULL,
  `city` VARCHAR(32) NOT NULL,
  `department` VARCHAR(32) NOT NULL,
  `type` VARCHAR(32) NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `user_id` BIGINT(16) NOT NULL,
  `status` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`, `department`, `city`),
  INDEX `user_id_idx` (`user_id` ASC),
  INDEX `index3` (`status` ASC),
  CONSTRAINT `user_id_idx`
    FOREIGN KEY (`user_id`)
    REFERENCES `paropkar`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `paropkar`.`notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paropkar`.`notification` (
  `id` INT(20) NOT NULL AUTO_INCREMENT,
  `content` TEXT(512) NOT NULL,
  `user_id` BIGINT(16) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_index` (`user_id` ASC),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `paropkar`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `paropkar`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paropkar`.`transaction` (
  `txn_id` VARCHAR(64) NOT NULL,
  `aadhaar_number` VARCHAR(16) NOT NULL,
  `txn_type` VARCHAR(64) NOT NULL,
  `txn_request` TEXT NULL DEFAULT NULL,
  `txn_response` TEXT NULL DEFAULT NULL,
  `status` VARCHAR(64) NULL DEFAULT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_at` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`txn_id`),
  INDEX `aadhaar_idx` (`aadhaar_number` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Trigger `complaint`.`insertion`
-- -----------------------------------------------------

CREATE TRIGGER `trigger_complaint_insert` AFTER INSERT ON `complaint` FOR EACH ROW
begin
  DECLARE message VARCHAR(64);
  DECLARE username_holder CURSOR FOR
  select CONCAT(username, ' has filed a new complaint \'', new.title, '\'') from user where id=new.user_id;
  OPEN username_holder;
  FETCH username_holder INTO message;
  insert into notification (user_id, content) select follower_id, message
  from follower where user_id=new.user_id;
  CLOSE username_holder;
end;

-- -----------------------------------------------------
-- Trigger `complaint`.`update`
-- -----------------------------------------------------

CREATE TRIGGER `trigger_complaint_update` AFTER UPDATE ON `complaint` FOR EACH ROW
begin
  if(new.status!=old.status) then
  insert into notification (user_id, content) values (new.user_id, CONCAT('Your complaint: \'', new.title,
  '\' has an updated status'));
  end if;
end;