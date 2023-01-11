package com.group.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom{
    // 코틀린에서는 ?가 있기 때문에 옵셔널을 쓰지 않는다.
    // Optional<User> => User?
    // 스프링이 코틀린을 호환하기 때문에 잘 활용되는 것이다.
    fun findByName(name: String): User?

    // 1+n을 해결하기 위한 join 쿼리
    // 우리가 직접 JPQL을 작성한다.
    // 하지만 JPQL의 최대 단점, 컴파일이 안된다.
    // 이를 막기위해 QueryDSL이 존재한다.

    //이 함수는 대출 목록이 없는 유저도 불러올 것이기 때문에
    // left join을 사용하자

    // DISTINCT => 칼럼의 겹치는 값 제거
    // 1+N 문제 해결 방법 => fetch join
    // fetch를 명시해야 실제로 조인 쿼리가 한 번만 나간다.

//    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.userLoanHistories")
//    fun findAllWithHistories(): List<User>
}