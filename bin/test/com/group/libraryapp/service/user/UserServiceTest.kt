package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
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
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

   @AfterEach
   fun clean(){
       // CLEAN 윗 부분만 실제 서비스 쿼리로 작동하는 것이다.
       println("CLEAN 시작")
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

    @Test
    @DisplayName("대출 기록이 없는 유저도 응답에 포함된다.")
    fun getUserLoanHistoriesTest1(){
        // given
        userRepository.save(User("A",null))

        // when
        val results = userService.getUserLoanHistories()

        // then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).isEmpty()

    }

    @Test
    @DisplayName("대출 기록이 많은 유저의 응답에 포함된다.")
    fun getUserLoanHistoriesTest2(){
        // given
        val savedUser = userRepository.save(User("A", null))
        userLoanHistoryRepository.saveAll(listOf(
            UserLoanHistory.fixture(savedUser,"책1",UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUser,"책2",UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUser,"책3",UserLoanStatus.RETURNED),
        ))

        // when
        val results = userService.getUserLoanHistories()

        // then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).hasSize(3)
        // 특정 필드 추출하기 => extracting
        assertThat(results[0].books).extracting("name")
            // 배열을 검증할 때는 이 함수를 쓴다.
            .containsExactlyInAnyOrder("책1","책2","책3")
        // 특정 필드 추출하기 => extracting
        assertThat(results[0].books).extracting("isReturn")
            // 배열을 검증할 때는 이 함수를 쓴다.
            .containsExactlyInAnyOrder(false,false,true)

    }

    @Test
    @DisplayName("방금 두 경우가 합쳐진 테스트")
    fun getUserLoanHistoriesTest3(){
        // given
        val savedUsers = userRepository.saveAll(listOf(User("A", null),User("B",null), User("C",null)))
        userLoanHistoryRepository.saveAll(listOf(
            UserLoanHistory.fixture(savedUsers[0],"책1",UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUsers[0],"책2",UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUsers[1],"책3",UserLoanStatus.RETURNED),
            UserLoanHistory.fixture(savedUsers[1],"책4",UserLoanStatus.LOANED),
        ))

        // when
        val results = userService.getUserLoanHistories()

        // then

        assertThat(results).hasSize(3)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).hasSize(2)
        // 특정 필드 추출하기 => extracting
        assertThat(results[0].books).extracting("name")
            // 배열을 검증할 때는 이 함수를 쓴다.
            .containsExactlyInAnyOrder("책1","책2")
        // 특정 필드 추출하기 => extracting
        assertThat(results[0].books).extracting("isReturn")
            // 배열을 검증할 때는 이 함수를 쓴다.
            .containsExactlyInAnyOrder(false,false)

        assertThat(results[1].name).isEqualTo("B")
        assertThat(results[1].books).hasSize(2)
        // 특정 필드 추출하기 => extracting
        assertThat(results[1].books).extracting("name")
            // 배열을 검증할 때는 이 함수를 쓴다.
            .containsExactlyInAnyOrder("책3","책4")
        // 특정 필드 추출하기 => extracting
        assertThat(results[1].books).extracting("isReturn")
            // 배열을 검증할 때는 이 함수를 쓴다.
            .containsExactlyInAnyOrder(true,false)

        assertThat(results[2].name).isEqualTo("C")
        assertThat(results[2].books).isEmpty()


    }

}