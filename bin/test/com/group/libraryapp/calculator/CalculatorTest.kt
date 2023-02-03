package com.group.libraryapp.calculator

import lombok.extern.slf4j.Slf4j
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException
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

    @Test
    fun divideTest(){
        // 테스트 준비
        // given
        val calculator: Calculator = Calculator(5)

        // 테스트 기능 호출
        // when
        calculator.divide(2)

        // 테스트 결과 검증
        // then
        if(calculator.number != 2){
            throw IllegalStateException()
        }

    }

    @Test
    fun divideExceptionTest(){
        // 테스트 준비
        // given
        val calculator: Calculator = Calculator(5)

        // 테스트 기능 호출
        // when

        try{
            calculator.divide(0)
        }catch(e: IllegalArgumentException){
            // 테스트 성공
            return
        } catch(e: Exception){
            throw IllegalArgumentException()
        }
        throw IllegalArgumentException("기대하는 예외가 발생하지 않았습니다.")

        // 테스트 결과 검증
        // then
//        assertThatThrownBy{calculator.divide(0)}
//            .isEqualTo(IllegalArgumentException("0으로 나눌 수 없습니다."))

    }
}