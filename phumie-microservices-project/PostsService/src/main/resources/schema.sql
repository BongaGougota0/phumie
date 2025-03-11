CREATE TABLE Post (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  text_content TEXT,               -- Nullable
  image_url VARCHAR(255),          -- Nullable
  timestamp DATETIME NOT NULL,
  author_id BIGINT NOT NULL        -- Foreign key to UserService
);