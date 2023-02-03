package com.group.libraryapp.calculator

import org.assertj.core.api.Assertions
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class JUnitCalculatorTest {


    @Test
    fun addTest(){
        // given
        val calculator = Calculator(5)

        // when
        calculator.add(3)

        // then
        assertThat(calculator.number)// 확인하고 싶은 값
            .isEqualTo(8)// 단언문
    }

    @Test
    fun minusTest(){
        // given
        val calculator = Calculator(5)

        // when
        calculator.minus(3)

        // then
        assertThat(calculator.number)// 확인하고 싶은 값
            .isEqualTo(2)// 단언문
    }

    @Test
    fun multiplyTest(){
        // given
        val calculator = Calculator(5)

        // when
        calculator.multiply(3)

        // then
        assertThat(calculator.number)// 확인하고 싶은 값
            .isEqualTo(15)// 단언문
    }
    @Test
    fun divideTest(){
        // given
        val calculator = Calculator(5)

        // when
        calculator.divide(2)

        // then
        assertThat(calculator.number)// 확인하고 싶은 값
            .isEqualTo(2)// 단언문
    }

    @Test
    fun divideExceptionTest(){
        // given
        val calculator = Calculator(5)

        // when
        // then
        val message = assertThrows<IllegalArgumentException> {
            calculator.divide(0)
            // apply는 indent를 하나 넣는 것
        }.apply {
            assertThat(message).isEqualTo("0으로 나눌 수 없습니다.")
        }
    }
}