package za.co.phumie.service;

import za.co.phumie.dto.FollowerDto;
import za.co.phumie.model.Follower;
import za.co.phumie.repository.FollowerRepository;
import za.co.phumie.service.usersint.IFollower;
import java.util.List;
import java.util.Map;

public class FollowerServiceImpl implements IFollower {

    private final FollowerRepository followerRepository;

    public FollowerServiceImpl(FollowerRepository followerRepository) {
        this.followerRepository = followerRepository;
    }

    @Override
    public List<FollowerDto> getCurrentUserFollower(long subjectUserId) {
        return List.of();
    }

    @Override
    public void followUser(long subjectUserId, long followeeUserId) {

    }

    @Override
    public void unFollowUser(long subjectUserId, long followeeUserId) {

    }

    @Override
    public Map<String, Integer> getFollowersFollowingData(long subjectUserId) {
        return Map.of();
    }

    @Override
    public FollowerDto mapToDto(Follower follower) {
        return null;
    }

    @Override
    public Follower mapToEntity(long subjectId, long followeeId) {
        return null;
    }
}
