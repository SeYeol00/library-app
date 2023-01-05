package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.service.user.UserService
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest
    @Autowired
    constructor(
        // 빈 등록
        private val bookService: BookService,
        private val bookRepository: BookRepository,
        private val userRepository: UserRepository,
    )
{

    @AfterEach
    fun clean(){
        bookRepository.deleteAll()
    }


    @Test
    @DisplayName(" 책 등록이 정상 동작한다.")
    fun saveBookTest(){
        // given
        val request: BookRequest = BookRequest("이상한 나라의 엘리스")

        // when
        bookService.saveBook(request)

        // then
        val books = bookRepository.findAll()
        assertThat(books.size).isEqualTo(1)
        assertThat(books[0].name).isEqualTo("이상한 나라의 엘리스")
    }

    @Test
    @DisplayName(" 책 대출이 정상 동작한다.")
    fun loanBookTest(){
        // given
        bookRepository.save(Book("이상한 나라의 엘리스"))
        userRepository.save(User("박세열",20))
        val request: BookLoanRequest = BookLoanRequest("박세열","이상한 나라의 엘리스")

        // when
        bookService.loanBook(request)

        // then

    }

    @Test
    @DisplayName(" 책 대출이 정상 동작한다.")
    fun loanBookExceptionTest(){
        // given
        val request: BookRequest = BookRequest("이상한 나라의 엘리스")

        // when
        bookService.saveBook(request)

        // then
        val books = bookRepository.findAll()
        assertThat(books.size).isEqualTo(1)
        assertThat(books[0].name).isEqualTo("이상한 나라의 엘리스")
    }
}