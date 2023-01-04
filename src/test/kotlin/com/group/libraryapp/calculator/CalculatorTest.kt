package com.group.libraryapp.calculator

import lombok.extern.slf4j.Slf4j
import org.junit.jupiter.api.Test
import kotlin.math.log


@Slf4j
class CalculatorTest {

    @Test
    fun addTest(){
        // 테스트 준비
        // given
        val calculator: Calculator = Calculator(5)

        // 테스트 기능 호출
        // when
        calculator.add(3)

        // 테스트 결과 검증
        // then
        if(calculator.number != 8){
            throw IllegalStateException()
        }

    }

    @Test
    fun minusTest(){
        // 테스트 준비
        // given
        val calculator: Calculator = Calculator(5)

        // 테스트 기능 호출
        // when
        calculator.minus(3)

        // 테스트 결과 검증
        // then
        if(calculator.number != 2){
            throw IllegalStateException()
        }

    }

    @Test
    fun multiplyTest(){
        // 테스트 준비
        // given
        val calculator: Calculator = Calculator(5)

        // 테스트 기능 호출
        // when
        calculator.multiply(3)

        // 테스트 결과 검증
        // then
        if(calculator.number != 15){
            throw IllegalStateException()
        }

    }
}