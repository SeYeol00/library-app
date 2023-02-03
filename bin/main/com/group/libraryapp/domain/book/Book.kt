package com.group.libraryapp.domain.book

import lombok.NoArgsConstructor
import java.lang.IllegalArgumentException
import javax.persistence.*

@Entity
class Book constructor(

    @Column
    val name: String,

    @Column
    @Enumerated(EnumType.STRING)// ENUM 타입을 칼럼으로 사용할 때 달아줘야한다.
    // 이게 없으면 enum이 숫자로 디비에 가게 된다.
    // JPA에서 enum을 해석하는데 있어 조건을 넣게 된다.
    val type: BookType,

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

    // 정적 팩토리로 생성자를 생성하면 엔티티의 코드가 바뀌어도 테스트는 영향이 가지 않는다.
    // 왜냐면 디폴트 파라미터가 있기 때문에 인수에 누락이 되더라도 디폴트 파라미터가 해결해준다.
    // 이런 디자인 패턴을 오브젝트 마더 패턴이라고 한다.
    companion object{
        fun fixture(
            // 디폴트 파라미터
            name: String = "책 이름",
            type: BookType = BookType.COMPUTER,
            id: Long? = null
        ): Book{
        return Book(
            name = name,
            type = type,
            id = id)
        }
    }
}
