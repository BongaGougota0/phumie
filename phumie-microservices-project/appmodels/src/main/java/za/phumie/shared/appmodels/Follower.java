package za.phumie.shared.appmodels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table
public class Follower {
    @Id
    private Long id;
    private Long subjectUserId;
    private String followerUsername;
    private Long followerId;
    private PhumieUser user;
}
