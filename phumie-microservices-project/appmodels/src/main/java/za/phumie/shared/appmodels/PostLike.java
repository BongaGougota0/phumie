package za.phumie.shared.appmodels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Table
public class PostLike {
    @Id
    private Long id;

    @Column
    private Long postId;

    @Column
    private Long userId;

    // Combining like + favourite into one row avoids a second table
    @Column
    private boolean isFavourited = false;

    @CreatedDate
    private LocalDateTime createdAt;

//    @CreatedDate
//    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}