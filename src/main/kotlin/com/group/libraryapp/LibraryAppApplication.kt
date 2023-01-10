package com.group.libraryapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LibraryAppApplication

// 클래스에 포함 안 시키는 함수는 자동으로
// 스태틱이 된다.
// 코틀린에서는 배열을 명시적으로 Array로 써줘야 한다.
fun main(args: Array<String>){
    // 스프링에서 코틀린에서도 스프링부트를 사용하기 위해
    // 새로운 함수를 만들어 놨다.
    // runApplication<>()이 SpringBootApplication의 역할을 한다.
    // 가변인자는 spread 연산자를 써주자 => *
    runApplication<LibraryAppApplication>(*args)
}