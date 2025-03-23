package za.co.phumie.LikeService.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import za.co.phumie.LikeService.model.FollowDto;
import za.co.phumie.LikeService.model.LikeDto;
import za.co.phumie.LikeService.service.IReactions;

@Component
public class ListenerController {
    private final IReactions iReactions;

    public ListenerController(IReactions iReactions) {
        this.iReactions = iReactions;
    }

    /**
     * Handles both Follow and Unfollow events.
     * @param followDto
     */
    @KafkaListener(topics = "FOLLOW", groupId = "reactions")
    public void handleFollowUnfollowEvents(FollowDto followDto) {
        iReactions.handleFollowReactions(followDto);
        System.out.println(followDto.userAction());
    }

    @KafkaListener(topics = "LIKE", groupId = "reactions")
    public void handlePosEvent(LikeDto likeDto) {
        iReactions.handlePostReactions(likeDto);
        System.out.println(likeDto.userAction());
    }
}
