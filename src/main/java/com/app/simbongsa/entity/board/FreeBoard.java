package com.app.simbongsa.entity.board;

import com.app.simbongsa.entity.file.FreeBoardFile;
import com.app.simbongsa.entity.member.Member;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @ToString(callSuper = true, exclude = "member")
@Table(name = "TBL_FREE_BOARD")
@PrimaryKeyJoinColumn(name = "ID")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@DynamicInsert
public class FreeBoard extends Board {

    @Column(columnDefinition = "integer default 0")
    private Integer freeBoardReplyCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "freeBoard", cascade = CascadeType.REMOVE)
    private List<FreeBoardReply> freeBoardReplies = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "freeBoard", cascade = CascadeType.REMOVE)
    private List<FreeBoardFile> freeBoardFiles = new ArrayList<>();

    @Builder
    public FreeBoard(Long id, String boardTitle, String boardContent, Member member, List<FreeBoardFile> freeBoardFiles) {
        super(id, boardTitle, boardContent);
        this.member = member;
        this.freeBoardFiles = freeBoardFiles;
    }

    public void setFreeBoardFiles(List<FreeBoardFile> freeBoardFiles) {
        this.freeBoardFiles = freeBoardFiles;
    }

    public void setFreeBoardReplyCount(Integer freeBoardReplyCount) {
        this.freeBoardReplyCount = freeBoardReplyCount;
    }
}
