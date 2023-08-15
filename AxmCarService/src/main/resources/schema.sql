USE axm_car_service;
--DROP TABLE IF EXISTS two_factor_verification;
--DROP TABLE IF EXISTS reset_pass_verifications;
--DROP TABLE IF EXISTS account_verifications;
--DROP TABLE IF EXISTS user_events;
--DROP TABLE IF EXISTS events;
--DROP TABLE IF EXISTS user_roles;
--DROP TABLE IF EXISTS roles;
--DROP TABLE IF EXISTS users;

CREATE TABLE users(
user_id BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT PRIMARY KEY,
first_name                VARCHAR(50) NOT NULL,
last_name                 VARCHAR(50) NOT NULL,
email                     VARCHAR(50) NOT NULL,
password                  VARCHAR(255) DEFAULT NULL,
address                   VARCHAR(255) DEFAULT NULL,
phone                     VARCHAR(30) DEFAULT NULL,
title                     VARCHAR(50) DEFAULT NULL,
bio                       VARCHAR(255) DEFAULT NULL,
enable BOOLEAN            DEFAULT FALSE,
non_locked BOOLEAN        DEFAULT TRUE,
using_mfa BOOLEAN         DEFAULT FALSE,
created_date DATETIME     DEFAULT CURRENT_TIMESTAMP,
image_url VARCHAR(255)    DEFAULT NULL,
CONSTRAINT UQ_Users_Email UNIQUE(email)

);




CREATE TABLE roles(
role_id BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT PRIMARY KEY,
role_name                VARCHAR(50) NOT NULL,
permission                 VARCHAR(255) NOT NULL,

CONSTRAINT UQ_Users_Email UNIQUE(role_name)

);



CREATE TABLE user_roles(
ur_id    BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT PRIMARY KEY,
ur_user_id         BIGINT UNSIGNED   NOT NULL ,
ur_role_id         BIGINT UNSIGNED   NOT NULL ,
FOREIGN KEY (ur_user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (ur_role_id) REFERENCES Roles(role_id) ON DELETE CASCADE ON UPDATE CASCADE,

CONSTRAINT UQ_UserRoles_User_Id UNIQUE(ur_user_id)

);


CREATE TABLE events(
event_id                 BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT PRIMARY KEY,
event_type               VARCHAR(50) NOT NULL CHECK(event_type IN ('LOGIN_ATTEMPT','LOGIN_ATTEMPT_FAILURE')),
event_description        VARCHAR(255) NOT NULL,

CONSTRAINT UQ_Events_Type UNIQUE(event_type)

);




CREATE TABLE user_events(
ue_id      BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT PRIMARY KEY,
ue_user_id  BIGINT UNSIGNED   NOT NULL,
ue_event_id  BIGINT UNSIGNED  NOT NULL,
device VARCHAR(100) DEFAULT NULL,
ip_address VARCHAR(100) DEFAULT NULL,
created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (ue_event_id) REFERENCES events(event_id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (ue_user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE

);




CREATE TABLE account_verifications(
av_id      BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT PRIMARY KEY,
av_user_id  BIGINT UNSIGNED   NOT NULL,
url VARCHAR(255)  DEFAULT NULL,
--date DATETIME NOT NULL,
FOREIGN KEY (av_user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT UQ_AccountVeriffication_User_Id UNIQUE (av_user_id),
CONSTRAINT UQ_AccountVeriffication_Url UNIQUE (url)

);



CREATE TABLE reset_pass_verifications(
rpv_id      BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT PRIMARY KEY,
rpv_user_id  BIGINT UNSIGNED   NOT NULL,
url VARCHAR(255)  DEFAULT NULL,
expirtation_date DATETIME DEFAULT NULL,
--date DATETIME NOT NULL,
FOREIGN KEY (rpv_user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT UQ_ResetPassVeriffication_User_Id UNIQUE (rpv_user_id),
CONSTRAINT UQ_ResetPassVeriffication_Url UNIQUE (url)

);



CREATE TABLE two_factor_verifications(
tfv_id      BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT PRIMARY KEY,
tfv_user_id  BIGINT UNSIGNED   NOT NULL,
code VARCHAR(255)  DEFAULT NULL,
expirtatio_date DATETIME DEFAULT NULL,
--date DATETIME NOT NULL,
FOREIGN KEY (tfv_user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT UQ_TwoFactorVeriffication_User_Id UNIQUE (tfv_user_id),
CONSTRAINT UQ_TwoFactorVeriffication_Code UNIQUE (code)

);