package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.BookHistoryResponse
import com.group.libraryapp.dto.user.response.UserLoanHistoryResponse
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.IllegalArgumentException

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
            user -> UserResponse.of(user)
        }
    }

    @Transactional
    fun updateUserName(request: UserUpdateRequest){
        // 코틀린에서는 생성자를 부를 때 ::을 쓴다.
        // 옵셔널 대신 ?를 쓰면 ?: 엘비스 연산자를 쓰자

//        val user = userRepository.findById(request.id)
//            .orElseThrow(::IllegalArgumentException)
        val user = userRepository.findByIdOrThrow(request.id)
            //                  springDataJpa에서 코틀린용으로 만든 메서드
        user.updateName(request.name)
    }

    @Transactional
    fun deleteUser(name: String){
        val user = userRepository.findByName(name)
            ?: throw IllegalArgumentException()
//            .orElseThrow(::IllegalArgumentException)
        userRepository.delete(user)
    }

    @Transactional(readOnly = true)
    fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
        return userRepository.findAllWithHistories()// 람다로 써서 풀거임
            // 리펙터링의 핵심은 dto를 만드는 역할을 dto에게 넘기는 것이다.
           .map (
               UserLoanHistoryResponse::of
           ) //user ->// user를 가져와서 쓸거임
//                UserLoanHistoryResponse(
//                    name = user.name, // 한 번 더 매핑, :: => method reference
//                    books = user.userLoanHistories.map(BookHistoryResponse::of)
//                )


    }
}