package com.group.libraryapp.dto.user.request

// 원래는 age가 Interger로 null이 가능했는데
// 변환 기능 사용 후 Int 즉, null이 안 들어가는 걸로 바뀌었다.
// 그래서 우리가 ?를 붙여줘야한다.
data class UserCreateRequest(
    val name: String,
    val age: Int?)