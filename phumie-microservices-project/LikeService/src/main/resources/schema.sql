CREATE TABLE IF NOT EXISTS `Likes` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  post_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  action user_action('LIKE', 'UNLIKE') NOT NULL,
  timestamp DATETIME NOT NULL
);

