package com.group.libraryapp.domain.user.loanhistory

import com.group.libraryapp.domain.user.User
import javax.persistence.*

@Entity
class UserLoanHistory constructor(

    @ManyToOne
    val user: User,

    @Column
    val bookName: String,

    @Column // enum으로 바꾸는 것이 더 좋다.
    // 추가 사항에서 이분적인 요구보다 더 자세한 사항을 원하기 때문이다.
    // 또한 불린은 코드를 이해하기 어렵기 때문이다.
    // enum은 다양한 요구사항에 용이하다.
    @Enumerated(EnumType.STRING)
    var status: UserLoanStatus = UserLoanStatus.LOANED,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    ) {

    // 커스텀 게터를 써서 리펙터링 하기
    val isReturn: Boolean
        get() = this.status == UserLoanStatus.RETURNED



    fun doReturn(){
        this.status = UserLoanStatus.RETURNED
    }

    companion object{
        fun fixture(
            user: User,
            bookName: String = "이상한 나라의 엘리스",
            status: UserLoanStatus = UserLoanStatus.LOANED,
            id: Long? = null
        ) : UserLoanHistory{
            return UserLoanHistory(
                user = user,
                bookName = bookName,
                status = status,
                id = id
            )
        }
    }
}