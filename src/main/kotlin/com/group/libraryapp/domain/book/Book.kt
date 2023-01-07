package com.group.libraryapp.domain.book

import lombok.NoArgsConstructor
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@NoArgsConstructor
class Book constructor(
    @Id // 대문자 Long이라 null 값 허용 가능
    private val id: Long?,

    @Column
    private val name: String,
){
}