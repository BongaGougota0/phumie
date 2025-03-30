CREATE TABLE IF NOT EXISTS `Likes` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  post_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  user_action NOT NULL,
  time_stamp DATETIME NOT NULL
);

-- @Query(nativeQuery = true, value = "")
-- String removeFollowing(@Param("userId") long userId, @Param("followerId") long followerId);
--
-- @Query(nativeQuery = true, value = "")
-- String followUserAction(@Param("userId") long userId, @Param("followerId") long followerId);

