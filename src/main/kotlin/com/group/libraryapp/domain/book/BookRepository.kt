package com.group.libraryapp.domain.book

import com.group.libraryapp.dto.book.response.BookStatResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface BookRepository : JpaRepository<Book, Long>{

    fun findByName(bookName: String): Book?

    // jpql 문법
    // 자바에서와 같이 객체 BookStatResponse로 바로 만들어 주려면
    // jpql에서 new를 사용한다.

    // jpql에서 객체 생성
    // => new 전체 패키지~객체이름()

    // 쿼리에서 count는 Long
    @Query("SELECT NEW com.group.libraryapp.dto.book.response.BookStatResponse(b.type, COUNT(b.id)) " +
            "FROM Book b GROUP BY b.type")
    fun getStats(): List<BookStatResponse>




}