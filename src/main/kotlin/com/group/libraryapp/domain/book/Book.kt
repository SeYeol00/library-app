package com.group.libraryapp.domain.book

import lombok.NoArgsConstructor
import java.lang.IllegalArgumentException
import javax.persistence.*

@Entity
class Book constructor(

    @Column
    val name: String,

    @Id // 대문자 Long이라 null 값 허용 가능, 초깃값을 null로 둡시다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,// 디폴트 파라미터는 아래에 있는 것이 관례
){
    // 코틀린의 초기화 블록
    init {
        if(name.isBlank()){
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다.")
        }
        // 이 다음 생성자 적용
    }
}