package za.phumie.shared.appmodels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Table
public class CommentLike {
    @Id
    private Long id;

    private Long commentId;
    private Long userId;
    private LocalDateTime createdAt;

    @CreatedDate
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
