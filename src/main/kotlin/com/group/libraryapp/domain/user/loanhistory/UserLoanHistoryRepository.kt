package com.group.libraryapp.domain.user.loanhistory

import org.springframework.data.jpa.repository.JpaRepository

interface UserLoanHistoryRepository : JpaRepository<UserLoanHistory,Long> {

    // null값 반환이 가능하니 반환 타입에 ?를 붙여주자
    fun findByBookNameAndStatus(bookName: String,status: UserLoanStatus): UserLoanHistory?
}