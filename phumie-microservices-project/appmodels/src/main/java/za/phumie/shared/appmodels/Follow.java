package za.phumie.shared.appmodels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Setter
@Getter
@Table
public class Follow {
    @Id
    private Long id;

    @Column
    private Long followerId;     // the person doing the following

    @Column
    private Long followingId;    // the person being followed

    private LocalDateTime createdAt;

    @CreatedDate
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
