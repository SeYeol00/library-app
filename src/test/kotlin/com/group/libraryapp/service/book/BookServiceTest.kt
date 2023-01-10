package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.IllegalArgumentException

@SpringBootTest
class BookServiceTest
    @Autowired
    constructor(
        // 빈 등록
        private val bookService: BookService,
        private val bookRepository: BookRepository,
        private val userRepository: UserRepository,
        private val userLoanHistoryRepository: UserLoanHistoryRepository,
    )
{

    @AfterEach
    fun clean(){
        // 코든 레포지토리는 초기화 해주자
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    companion object {
        @AfterAll
        @JvmStatic
        fun afterAll(){
            println("모든 테스트 종료 후")
        }
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
        val savedUser = userRepository.save(User("박세열", 20))
        val request: BookLoanRequest = BookLoanRequest("박세열","이상한 나라의 엘리스")

        // when
        bookService.loanBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results.size).isEqualTo(1)
        assertThat(results[0].bookName).isEqualTo("이상한 나라의 엘리스")
        assertThat(results[0].user.id).isEqualTo(savedUser.id)
        assertThat(results[0].isReturn).isEqualTo(false)

    }

    @Test
    @DisplayName(" 책이 진작 대출되어 있다면, 신규 대출이 실패한다.")
    fun loanBookFailTest(){
        // given
        bookRepository.save(Book("이상한 나라의 엘리스"))
        val savedUser = userRepository.save(User("박세열", 20))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser,"이상한 나라의 엘리스",false))
        val request: BookLoanRequest = BookLoanRequest("박세열","이상한 나라의 엘리스")

        // when & then
        val message = assertThrows<IllegalArgumentException> {
            bookService.loanBook(request)
        }.message // score function
        assertThat(message).isEqualTo("진작 대출되어 있는 책입니다.")
    }

    @Test
    @DisplayName("책 반납이 정상 동작한다.")
    fun returnBookTest(){
        // given
        bookRepository.save(Book("이상한 나라의 엘리스"))
        val savedUser = userRepository.save(User("박세열", 20))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser,"이상한 나라의 엘리스",false))
        val request: BookReturnRequest = BookReturnRequest("박세열","이상한 나라의 엘리스")

        // when
        bookService.returnBook(request)

        //then
        // 대출 기록을 가져와서 값이 하나 있음을 보장하고
        // isReturn이 true인지 검증
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].isReturn).isEqualTo(true)
    }
}