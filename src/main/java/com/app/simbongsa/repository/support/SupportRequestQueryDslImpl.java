package com.app.simbongsa.repository.support;

import com.app.simbongsa.search.admin.AdminSupportRequestSearch;
import com.app.simbongsa.entity.support.SupportRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.util.List;

import static com.app.simbongsa.entity.support.QSupportRequest.supportRequest;

@RequiredArgsConstructor
public class SupportRequestQueryDslImpl implements SupportRequestQueryDsl {
    private final JPAQueryFactory query;

    /* 유저아이디로 후원요청목록 페이징처리해서 불러오기 */
    @Override
    public Page<SupportRequest> findByMemberId(Pageable pageable, Long memberId) {
        List<SupportRequest> foundSupportRequest = query.select(supportRequest)
                .from(supportRequest)
                .where(supportRequest.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(supportRequest.count())
                .from(supportRequest)
                .where(supportRequest.member.id.eq(memberId))
                .fetchOne();

        return new PageImpl<>(foundSupportRequest,pageable,count);
    }

//  후원 요청 목록페이지 무한스크롤
    @Override
    public Slice<SupportRequest> findAllSupportRequest(Pageable pageable) {
        List<SupportRequest> foundSupportRequest = query.select(supportRequest)
                .from(supportRequest)
                .orderBy(supportRequest.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(foundSupportRequest, pageable, true);
//      hasNext는 현재 페이지에서 다음 페이지가 있는지 여부를 나타내는 불리언(Boolean) 값, true로 설정되면 다음 페이지가 있는 것으로 간주되고,
//      false로 설정되면 다음 페이지가 없는 것으로 간주
    }

//    후원 요청 전체 조회(페이징)
    @Override
    public Page<SupportRequest> findAllWithPaging(AdminSupportRequestSearch adminSupportRequestSearch, Pageable pageable) {
        BooleanExpression requestTypeEq = adminSupportRequestSearch.getRequestType() == null ? null : supportRequest.supportRequestStatus.eq(adminSupportRequestSearch.getRequestType());
        BooleanExpression memberEmailLike = adminSupportRequestSearch.getMemberEmail() == null ? null : supportRequest.member.memberEmail.like("%" + adminSupportRequestSearch.getMemberEmail() + "%");

        List<SupportRequest> foundSupportRequest = query.select(supportRequest)
                .from(supportRequest)
                .where(requestTypeEq, memberEmailLike)
                .orderBy(supportRequest.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(supportRequest.count())
                .from(supportRequest)
                .fetchOne();

        return new PageImpl<>(foundSupportRequest, pageable, count);
    }

    @Override
    public SupportRequest findSupportRequestDetail_QueryDSL(Long id) {
        return query.select(supportRequest)
                .from(supportRequest)
                .join(supportRequest.supportRequestFiles)
                .fetchJoin()
                .where(supportRequest.id.eq(id))
                .fetchOne();
    }

}
