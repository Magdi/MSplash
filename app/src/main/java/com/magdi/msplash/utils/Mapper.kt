package com.magdi.msplash.utils

interface Mapper<R, D> {
    fun map(response: R): D
    fun mapList(response: List<R>): List<D> {
        return response.map { map(it) }
    }
}