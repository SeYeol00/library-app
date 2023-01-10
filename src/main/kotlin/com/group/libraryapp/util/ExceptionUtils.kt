package com.group.libraryapp.util

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

// Nothing -> 반환 안 한다.
fun fail(): Nothing{
    throw IllegalArgumentException()
}

fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T{
    return this.findByIdOrNull(id) ?: fail()
}