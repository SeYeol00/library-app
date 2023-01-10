package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User

// 개인 차이긴 하지만 dto는 취지에 맞게 데이터 클래스로 선언하는 것도 많다.
data class UserResponse(
    val id: Long,
    val name: String,
    val age: Int?
) {

    // static을 사용해서 private한 생성자에 접근하게하는 방법
    // 제준이가 많이 쓰는 방법이다.
    companion object{
        fun of(user: User): UserResponse{
            // 여기서 생성자 쓰기
            return UserResponse(
                // null값 없다고 하는 확신자 !!
                id = user.id!!, // 유저 아이디가 가장 초기에는 널이 가능하니 지정해줘야한다.
                name = user.name,
                age = user.age)
        }
    }



}