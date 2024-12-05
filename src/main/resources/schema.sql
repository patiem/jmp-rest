DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         INT         NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(20) NOT NULL,
    last_name  VARCHAR(20) NOT NULL,
    gender     VARCHAR(20) NOT NULL,
    country    VARCHAR(10) NOT NULL,

    PRIMARY KEY (id)
)
