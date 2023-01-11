package com.group.libraryapp.dto.book.response

import com.group.libraryapp.domain.book.BookType
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus

data class BookStatResponse constructor(
    val type: BookType,
    val count: Long,
) {
//    fun plusOne(){
//        this.count+=1
//    }

}