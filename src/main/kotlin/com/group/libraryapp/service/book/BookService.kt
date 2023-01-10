package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class BookService constructor(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository
) {

    @Transactional
    fun saveBook(request: BookRequest){
        // 코틀린은 디폴트 파라미터가 가능하다. 즉 id를 따로 null로 초기화 안 해줘도 된다.
        val newBook = Book(request.name,request.type)
        bookRepository.save(newBook)
    }

    @Transactional
    fun loanBook(request: BookLoanRequest){
        val book = bookRepository.findByName(request.bookName)
            // :: => 생성자 콜
            // 옵셔널을 쓰지 않았기 떄문에 orElseThrow를 쓰지 못한다.
            // 대신 엘비스 연산자를 쓴다.
            ?: fail()
//            .orElseThrow(::IllegalArgumentException)
        if(userLoanHistoryRepository.findByBookNameAndStatus(request.bookName, UserLoanStatus.LOANED) != null){
            throw IllegalArgumentException("진작 대출되어 있는 책입니다.")
        }
        val user = userRepository.findByName(request.userName)
            ?: fail()
//            .orElseThrow(::IllegalArgumentException)
        user.loanBook(book)
    }

    @Transactional
    fun returnBook(request: BookReturnRequest){
        val user = userRepository.findByName(request.userName)
            ?: fail()
//            .orElseThrow(::IllegalArgumentException)
        user.returnBook(request.bookName)

    }
}