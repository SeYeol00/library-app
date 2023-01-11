package com.group.libraryapp.domain.user.loanhistory

import org.springframework.data.jpa.repository.JpaRepository

interface UserLoanHistoryRepository : JpaRepository<UserLoanHistory,Long> {


//    fun findByBookName(bookName: String): UserLoanHistory?
//
//    // null값 반환이 가능하니 반환 타입에 ?를 붙여주자
//    fun findByBookNameAndStatus(bookName: String,status: UserLoanStatus): UserLoanHistory?
//
//    // 현대 대여 중인 책들의 갯수
//    fun findAllByStatus(status: UserLoanStatus): List<UserLoanHistory>
//
//    // 카운트의 결과는 Long
//    fun countByStatus(status:UserLoanStatus): Long


}