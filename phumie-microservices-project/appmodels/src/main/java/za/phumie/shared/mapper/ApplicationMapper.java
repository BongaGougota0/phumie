package za.phumie.shared.mapper;

import za.phumie.shared.appmodels.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ApplicationMapper {

    // ─────────────────────────────────────────────
    // DTOs
    // ─────────────────────────────────────────────

    public record PhumieUserDto(
            Long userId,
            String userEmail,
            String username,
            String aboutUser,
            String avatarUrl,
            String passwordHash,
            int followerCount
    ) {}

    public record PostDto(
            Long postId,
            Long authorUserId,
            String authorUsername,
            String textContent,
            String mediaType,
            String imageUrl,
            int commentCount,
            LocalDateTime createdAt
    ) {}

    public record CommentDto(
            String authorUsername,
            String imageUrl,
            String textContent,
            LocalDateTime createdAt
    ) {}

    public record PostLikeDto(
            Long postId,
            Long userId,
            boolean isFavourited,
            LocalDateTime createdAt
    ) {}

    public record FollowDto(
            Long followerId,
            Long followingId,
            LocalDateTime createdAt
    ) {}

    public record FollowerDto(
            Long subjectUserId,
            String followerUsername,
            Long followerId
    ) {}

    // ─────────────────────────────────────────────
    // Mappers
    // ─────────────────────────────────────────────

    public static PhumieUserDto toUserDto(PhumieUser user) {
        if (user == null) return null;

        return new PhumieUserDto(
                user.getUserId(),
                user.getUserEmail(),
                user.getUsername(),
                user.getAboutUser(),
                user.getAvatarUrl(),
                "",
                user.getFollowerCount()
        );
    }

    public static PostDto toPostDto(Post post) {
        if (post == null) return null;

        return new PostDto(
                post.getPostId(),
                post.getAuthorUserId(),
                post.getAuthorUsername(),
                post.getTextContent(),
                post.getMediaType() != null ? post.getMediaType() : null,
                post.getImageUrl(),
                post.getCommentCount(),
                post.getCreatedAt()
        );
    }

    public static CommentDto toCommentDto(Comment comment) {
        if (comment == null) return null;

        return new CommentDto(
                comment.getAuthorUsername(),
                comment.getImageUrl(),
                comment.getTextContent(),
                comment.getCreatedAt()
        );
    }

    public static PostLikeDto toPostLikeDto(PostLike postLike) {
        if (postLike == null) return null;

        return new PostLikeDto(
                postLike.getPostId(),
                postLike.getUserId(),
                postLike.isFavourited(),
                postLike.getCreatedAt()
        );
    }

    public static FollowDto toFollowDto(Follow follow) {
        if (follow == null) return null;

        return new FollowDto(
                follow.getFollowerId(),
                follow.getFollowingId(),
                follow.getCreatedAt()
        );
    }

    public static FollowerDto toFollowerDto(Follower follower) {
        if (follower == null) return null;

        return new FollowerDto(
                follower.getSubjectUserId(),
                follower.getFollowerUsername(),
                follower.getFollowerId()
        );
    }

    // ─────────────────────────────────────────────
    // List Mappers
    // ─────────────────────────────────────────────

    public static List<PhumieUserDto> toUserDtoList(List<PhumieUser> users) {
        if (users == null) return Collections.emptyList();
        return users.stream()
                .map(ApplicationMapper::toUserDto)
                .toList();
    }

    public static List<PostDto> toPostDtoList(List<Post> posts) {
        if (posts == null) return Collections.emptyList();
        return posts.stream()
                .map(ApplicationMapper::toPostDto)
                .toList();
    }

    public static List<CommentDto> toCommentDtoList(List<Comment> comments) {
        if (comments == null) return Collections.emptyList();
        return comments.stream()
                .filter(c -> !c.isDeleted())
                .map(ApplicationMapper::toCommentDto)
                .toList();
    }

    public static List<PostLikeDto> toPostLikeDtoList(List<PostLike> postLikes) {
        if (postLikes == null) return Collections.emptyList();
        return postLikes.stream()
                .map(ApplicationMapper::toPostLikeDto)
                .toList();
    }

    public static List<FollowDto> toFollowDtoList(List<Follow> follows) {
        if (follows == null) return Collections.emptyList();
        return follows.stream()
                .map(ApplicationMapper::toFollowDto)
                .toList();
    }

    public static List<FollowerDto> toFollowerDtoList(List<Follower> followers) {
        if (followers == null) return Collections.emptyList();
        return followers.stream()
                .map(ApplicationMapper::toFollowerDto)
                .toList();
    }

    // ─────────────────────────────────────────────
    // Dtos to entities
    // ─────────────────────────────────────────────

    public static PostLike toPostLikeEntity(PostLikeDto dto) {
        if (dto == null) return null;

        PostLike postLike = new PostLike();
        postLike.setPostId(dto.postId());
        postLike.setUserId(dto.userId());
        postLike.setFavourited(dto.isFavourited());
        // createdAt is excluded — @PrePersist handles it on save

        return postLike;
    }

    public static PhumieUser toUserEntity(PhumieUserDto dto) {
        if (dto == null) return null;

        PhumieUser user = new PhumieUser();
        user.setUserId(dto.userId());
        user.setUsername(dto.username());
        user.setUserEmail(dto.userEmail());
        user.setPasswordHash(dto.passwordHash);
        user.setAboutUser(dto.aboutUser());
        user.setAvatarUrl(dto.avatarUrl());
        user.setFollowerCount(dto.followerCount());

        return user;
    }

    public static Post toPostEntity(PostDto dto) {
        if (dto == null) return null;

        Post post = new Post();
        post.setPostId(dto.postId());
        post.setAuthorUserId(dto.authorUserId());
        post.setAuthorUsername(dto.authorUsername());
        post.setTextContent(dto.textContent());
        post.setMediaType(dto.mediaType() != null ? String.valueOf(PostMediaType.valueOf(dto.mediaType())) : null);
        post.setImageUrl(dto.imageUrl());
        post.setCommentCount(dto.commentCount());

        return post;
    }

    public static Comment toCommentEntity(CommentDto dto) {
        if (dto == null) return null;

        Comment comment = new Comment();
        comment.setAuthorUsername(dto.authorUsername());
        comment.setImageUrl(dto.imageUrl());
        comment.setTextContent(dto.textContent());
        return comment;
    }

    public static Follow toFollowEntity(FollowDto dto) {
        if (dto == null) return null;

        Follow follow = new Follow();
        follow.setFollowerId(dto.followerId());
        follow.setFollowingId(dto.followingId());
        return follow;
    }

    private ApplicationMapper() {}
}