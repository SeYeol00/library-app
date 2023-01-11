package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.repository.book.BookQuerydslRepository
import com.group.libraryapp.repository.user.UserLoanHistoryQuerydslRepository
import com.group.libraryapp.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class BookService constructor(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
    private val bookQuerydslRepository: BookQuerydslRepository,
    private val userLoanHistoryQuerydslRepository: UserLoanHistoryQuerydslRepository,
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
        if(userLoanHistoryQuerydslRepository.find(request.bookName, UserLoanStatus.LOANED) != null){
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
    @Transactional(readOnly = true)
    fun countLoanedBook(): Int {
        // 대여 중인 책들의 갯수, findAllByStatus가 list니 뒤에 size 붙이면 된다.
        return userLoanHistoryQuerydslRepository.count(UserLoanStatus.LOANED).toInt()
    }


    @Transactional(readOnly = true)
    fun getBookStatistics(): List<BookStatResponse> {
        return bookQuerydslRepository.getStats()
//        return bookRepository.findAll() // List<Book>
//            .groupBy { book -> book.type }// Map<BookType, List<Book>>
//            // <타입, 북스(리스트)>의 맵을 또 람다함수(생성자)에 map으로 돌린 것
//            .map { (type, books) -> BookStatResponse(type,books.size) } //List<BookStatResponse>
//        // 응답용 리스트
//        val results = mutableListOf<BookStatResponse>()
//        val books = bookRepository.findAll()
//        for(book in books){
//            // 처음이면 null값일 수도 있다.
//            results.firstOrNull{ dto -> book.type == dto.type}
//            // null이 아니면 plusOne(),
//                ?.plusOne()
//            // 그 다음 엘비스 연산자로 존재하지 않으면, 즉 null이면 results.add
//                ?: results.add(BookStatResponse(book.type, 1))
//        }
//        return results
    }


}