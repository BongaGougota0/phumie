CREATE TABLE IF NOT EXSITS `users`(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100),
    user_email VARCHAR(100),
    password VARCHAR(255),
    user_role VARCHAR(10),
    about_user VARCHAR(555),
)