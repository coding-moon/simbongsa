package com.app.simbongsa.entity.board;

import com.app.simbongsa.audit.Period;
import com.app.simbongsa.entity.user.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@Table(name = "TBL_REVIEW_REPLY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewReply extends Period {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull private String freeBoardReplyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REVIEW_ID")
    private Review review;

    @Builder
    public ReviewReply(String freeBoardReplyContent, User user, Review review) {
        this.freeBoardReplyContent = freeBoardReplyContent;
        this.user = user;
        this.review = review;
    }
}
