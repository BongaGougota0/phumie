CREATE TABLE IF NOT EXISTS `users`(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100),
    user_email VARCHAR(100),
    password VARCHAR(255),
    user_role VARCHAR(10),
    about_user VARCHAR(555),
)

CREATE TABLE IF NOT EXISTS `followers`(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subject_user_id BIGINT,
    follower_username VARCHAR(255),
    follower_id BIGINT,
    FOREIGN KEY (follower_id) REFERENCES users(user_id)
)