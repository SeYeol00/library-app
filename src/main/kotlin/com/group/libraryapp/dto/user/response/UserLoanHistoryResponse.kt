package com.group.libraryapp.dto.user.response


// Dto를 만들 때는 결국 틀을 만드는 것이니
// api 명세서를 보면서 만드는 것이 좋다.
data class UserLoanHistoryResponse constructor(
    val name: String, // 유저 이름
    val books: List<BookHistoryResponse>
) {
}

data class BookHistoryResponse constructor(
    val name: String, // 책 이름
    val isReturn: Boolean // 반납 여부
){

}