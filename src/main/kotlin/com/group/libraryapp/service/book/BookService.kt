package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookRequest
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
        val newBook = Book(request.name)
        bookRepository.save(newBook)
    }
}