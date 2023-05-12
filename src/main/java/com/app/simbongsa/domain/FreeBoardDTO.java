package com.app.simbongsa.domain;

import com.querydsl.core.annotations.QueryProjection;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
//@Component
public class FreeBoardDTO {
    private Long id;
    private String BoardTitle;
    private String BoardContent;

    private MemberDTO memberDTO;
    private FileDTO fileDTO;
    private FreeBoardReplyDTO freeBoardReplyDTO;

    @Builder

    public FreeBoardDTO(Long id, String boardTitle, String boardContent, MemberDTO memberDTO, FileDTO fileDTO, FreeBoardReplyDTO freeBoardReplyDTO) {
        this.id = id;
        this.BoardTitle = boardTitle;
        this.BoardContent = boardContent;
        this.memberDTO = memberDTO;
        this.fileDTO = fileDTO;
        this.freeBoardReplyDTO = freeBoardReplyDTO;
    }
}
