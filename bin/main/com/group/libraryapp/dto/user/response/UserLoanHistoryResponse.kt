package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.book.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus


// Dto를 만들 때는 결국 틀을 만드는 것이니
// api 명세서를 보면서 만드는 것이 좋다.
data class UserLoanHistoryResponse constructor(
    val name: String, // 유저 이름
    val books: List<BookHistoryResponse>
) {

    // 리펙터링용 정적 팩토리 매서드
    companion object{
        fun of(user: User): UserLoanHistoryResponse{
            return UserLoanHistoryResponse(
                name = user.name,
                books = user.userLoanHistories.map(BookHistoryResponse::of)
            )
        }
    }
}

data class BookHistoryResponse constructor(
    val name: String, // 책 이름
    val isReturn: Boolean // 반납 여부
){
    // 정적 팩토리 매서드(static method)를 이용해서 리팩터링
    // 매서드 레퍼런스를 통해 리팩터링할 것이다.
    companion object{
        fun of(history: UserLoanHistory) : BookHistoryResponse{
            return BookHistoryResponse(
                name = history.bookName,
                isReturn = history.isReturn
            )
        }
    }

}