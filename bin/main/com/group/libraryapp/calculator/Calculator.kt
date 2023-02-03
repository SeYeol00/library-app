package com.group.libraryapp.calculator

import lombok.extern.slf4j.Slf4j
import java.lang.IllegalArgumentException

// 값을 가져오는 방법은 2가지
// 1. data class 선언
// 2. 필드를 public 선언
// 3. private으로 두는 대신 코틀린의 배킹프로퍼티를 쓴다.
// 언더바 넘버로 바꾼다.
@Slf4j
class Calculator constructor(
    var number: Int
) {
//    // 게터를 커스텀으로 지정해서 가져온다.
//    val number: Int
//        get() = this._number

    fun add(operand: Int){
        this.number += operand
    }

    fun minus(operand: Int){
        this.number -= operand
    }

    fun multiply(operand: Int){
        this.number *= operand
    }

    fun divide(operand: Int){
        if (operand == 0){
            throw IllegalArgumentException("0으로 나눌 수 없습니다.")
        }
        this.number /= operand
    }
}