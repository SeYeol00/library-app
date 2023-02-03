package com.group.libraryapp.repository.user

import com.group.libraryapp.domain.user.loanhistory.QUserLoanHistory.userLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

// 쿼리 dsl을 쓰는 두번째 방법
@Component
class UserLoanHistoryQuerydslRepository constructor(
    private val queryFactory: JPAQueryFactory,
) {
    // 기본 값을 null로 넣으면 기존에 쓰던 함수는 그대로 쓸 수 있다.
    // 즉 이름으로만 찾을 수 있고 아니면 status의 조건도 사용이 가능하다는 것이다.
    fun find(bookName: String, status: UserLoanStatus? = null):UserLoanHistory?{
        return queryFactory
            .select(userLoanHistory)
            .from(userLoanHistory)
            .where(userLoanHistory.bookName.eq(bookName),
                status?.let { userLoanHistory.status.eq(status) })
            .limit(1)
            // 하나만 가져오니까 fetchOne()
            .fetchOne()
    }

    fun count(status: UserLoanStatus):Long{
        return queryFactory.select(userLoanHistory.id.count())
            .from(userLoanHistory)
            .where(
                userLoanHistory.status.eq(status)
            )
            // null이 가능하니까 null이면 0을 반환
            .fetchOne() ?: 0L
    }
}