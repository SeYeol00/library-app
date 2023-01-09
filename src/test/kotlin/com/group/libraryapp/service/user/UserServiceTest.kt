package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.Optional


@SpringBootTest
class UserServiceTest
    @Autowired
    constructor(
    // 빈 등록
    private val userService: UserService,
    private val userRepository: UserRepository
) {

   @AfterEach
   fun clear(){
       userRepository.deleteAll()
   }


    @Test
    @DisplayName("유저 저장이 정상 동작한다.")
    fun saveUserTest(){
        // given
        val request: UserCreateRequest = UserCreateRequest("박세열",25)
        
        // when
        userService.saveUser(request)

        // then
        val results: List<User> = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("박세열")
        assertThat(results[0].age).isEqualTo(25)
    }

    @Test
    @DisplayName("유저 조회가 정상 동작한다.")
    fun getUsersTest(){
        // given
        userRepository.saveAll(listOf(
            User("A",20),
            User("B",30),
            User("C",40),
        ))

        // when
        val results = userService.getUsers()

        // then
        assertThat(results).hasSize(3)// [UserResponse(), UserResponse()]
        assertThat(results).extracting("name")
            .containsExactlyInAnyOrder("A","B","C")// ["A","B"]
        assertThat(results).extracting("age")
            .containsExactlyInAnyOrder(20,30,40)// ["A","B"]
    }


    @Test
    @DisplayName("유저 업데이트가 정상 동작한다.")
    fun updateUserNameTest(){
        //given
        val savedUser: User = userRepository.save(User("A",10))
        //  User에서 id는 null이 될 수 있는데 UserUpdateRequest의 id는 null이 될 수 없다.
        // 해결 방법은 null 단언 선언이다. -> !!
        val request: UserUpdateRequest = UserUpdateRequest(savedUser.id!!,"B")

        // when
        userService.updateUserName(request)

        // then
        val user: Optional<User> = userRepository.findById(savedUser.id)
        if(user.isPresent){
            assertThat(user.get().name).isEqualTo("B")
            println("검증 완료")
        }
        else{
            println("검증 실패")
        }
    }

    @Test
    @DisplayName("유저 삭제가 정상 동작한다.")
    fun deleteUserTest(){
        // given
        userRepository.save(User("A",null))

        // when
        userService.deleteUser("A")

        // then
        assertThat(userRepository.findAll()).isEmpty()
    }
}