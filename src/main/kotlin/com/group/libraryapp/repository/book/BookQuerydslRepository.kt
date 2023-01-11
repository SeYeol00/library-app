package com.group.libraryapp.repository.book

import com.group.libraryapp.domain.book.QBook.book
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component


// 쿼리 dsl을 쓰는 두번째 방법
@Component
class BookQuerydslRepository constructor(
    private val queryFactory:JPAQueryFactory,
) {
    fun getStats(): List<BookStatResponse>{
        // 두번 째 방법, DTO를 바로 가져오기
        return queryFactory
            .select(
            // 생성자를 이용하는 것이다.
            // DTO의 생성자를 호출하겠다.
            // Projection => 데이터의 특정 칼럼만 가져오겠다.
            Projections.constructor(
                // 첫번쨰 인수는 만들 클래스
            BookStatResponse::class.java,
                // 다음 인수 부터는 생성자에 들어갈 파라미터
            book.type,
            book.id.count()
        ))
            .from(book)
            .groupBy(book.type)
            .fetch()
    }
}