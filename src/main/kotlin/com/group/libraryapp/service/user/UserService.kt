package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
class UserService constructor(
    private val userRepository: UserRepository
) {

    @Transactional
    fun saveUser(request: UserCreateRequest){
        // 코틀린에서는 디폴트 파라미터가 적용되기 때문에 빈 객체를 넣을 필요가 없다.
        val newUser = User(request.name, request.age)
        userRepository.save(newUser)
    }

    @Transactional(readOnly = true)// 조회용
    fun getUsers(): List<UserResponse>{
        return userRepository.findAll()
            // stream과 같은 기능
            .map { // findAll로 가져온 객체들을 람다 함수에 넣겠다, map은 iter와 같은 역할이다.
            user -> UserResponse(user)
        }
    }

    @Transactional
    fun updateUserName(request: UserUpdateRequest){ // 코틀린에서는 생성자를 부를 때 ::을 쓴다.
        val user = userRepository.findById(request.id).orElseThrow(::IllegalArgumentException)
        user.updateName(request.name)
    }

    @Transactional
    fun deleteUser(name: String){
        val user = userRepository.findByName(name).orElseThrow(::IllegalArgumentException)
        userRepository.delete(user)
    }


}