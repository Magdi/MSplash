package com.magdi.msplash.data.mappers

import com.magdi.msplash.utils.Mapper
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

object LocaleDateTimeMapper : Mapper<String?, LocalDateTime?> {
    private val dateFormatter by lazy {
        DateTimeFormat.forPattern("yyyy-mm-dd'T'HH:mm:ssZ")
    }


    override fun map(response: String?): LocalDateTime? {
        return try {
            LocalDateTime.parse(response, dateFormatter)
        } catch (e: Exception) {
            null
        }
    }
}