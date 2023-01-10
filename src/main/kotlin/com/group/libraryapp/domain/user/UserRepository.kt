package com.group.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long>{
    // 코틀린에서는 ?가 있기 때문에 옵셔널을 쓰지 않는다.
    // Optional<User> => User?
    // 스프링이 코틀린을 호환하기 때문에 잘 활용되는 것이다.
    fun findByName(name: String): User?
}