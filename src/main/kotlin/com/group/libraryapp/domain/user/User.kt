package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import java.lang.IllegalArgumentException
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class User constructor(

    var name: String,

    val age: Int?,

    // 1+n 문제의 딜레마

    // 1+n이 발생하는 이유는 map으로 연관된 객체들을 계속해서 가짜로 불러오기 때문
    // 여기서는 User를 가져오고 그 다음 거기에 연관된 UserLoanHistory를 또 가져온다.
    // 이를 list형태로 map을 통해 돌리기 때문에 돌리는 만큼 쿼리가 나간다.
    // 즉 불필요한 쿼리 횟수를 반복하는 문제이다.
    // 이는 fetch type이 lazy기 때문이다.
    // 하지만 eager로 바뀌면 쿼리의 갯수는 줄어드는 대신 불필요한 데이터들이 한꺼번에 들어온다.
    // 결국 이 둘 사이에서 줄타기하는 것이 핵심이다.
    // join으로 필요한 데이터만 한 번에 가져오자

    // 이를 해결하기 위해 join쿼리를 짜면 된다. -> JPQL or QueryDSL
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    init{
        if(name.isBlank()){
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다.")
        }
    }

    fun updateName(name: String){
        this.name = name
    }

    fun loanBook(book: Book){
        this.userLoanHistories.add(UserLoanHistory.fixture(this, book.name))
    }

    fun returnBook(bookName: String){
        //                      람다를 넣을 수 있다. 람다식이 트루인 값의 첫번째를 가져옴
        //                      first가 필터 역할을 한 거임
        //                      filter + findFirst
        this.userLoanHistories.first {history -> history.bookName == bookName}
            .doReturn()
    }
}