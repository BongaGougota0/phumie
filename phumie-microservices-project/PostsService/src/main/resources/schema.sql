CREATE TABLE IF NOT EXISTS `posts`(
   post_id BIGINT PRIMARY KEY AUTO_INCREMENT,
   post_content TEXT,
   time_stamp TIMESTAMP,
   author_user_id BIGINT,
   author_username VARCHAR(255)
)

CREATE TABLE IF NOT EXISTS `comments`(
    comment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    content TEXT,
    time_stamp TIMESTAMP,
    author_user_id BIGINT,
    author_username VARCHAR(255),
    post_id BIGINT,
    FOREIGN KEY (post_id) REFERENCES posts(post_id)
)