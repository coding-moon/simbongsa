package com.app.simbongsa.entity.board;

import com.app.simbongsa.entity.file.FreeBoardFile;
import com.app.simbongsa.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @ToString(callSuper = true)
@Table(name = "TBL_FREE_BOARD")
@PrimaryKeyJoinColumn(name = "ID")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@DynamicInsert
public class FreeBoard extends Board {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "freeBoard")
    List<FreeBoardReply> freeBoardReplies;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "freeBoard")
    private List<FreeBoardFile> freeBoardFiles;

    public FreeBoard(String boardTitle, String boardContent, User user) {
        super(boardTitle, boardContent);
        this.user = user;
    }
}
