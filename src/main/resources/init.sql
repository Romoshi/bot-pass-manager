CREATE TABLE IF NOT EXISTS db_passwords(
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            user_id INT(60) FOREIGN KEY,
            name_service VARCHAR(60) NOT NULL,
            login VARCHAR(60) NOT NULL,
            password VARCHAR(100) NOT NULL
            );


