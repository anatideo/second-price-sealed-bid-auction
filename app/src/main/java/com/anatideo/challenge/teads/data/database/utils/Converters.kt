package com.anatideo.challenge.teads.data.database.utils

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {

    @TypeConverter
    fun fromBigDecimal(source: BigDecimal): String = source.toString()

    @TypeConverter
    fun toBigDecimal(source: String): BigDecimal = BigDecimal(source)

    // TODO there is opportunity to get it better
    @TypeConverter
    fun fromBigDecimalList(source: List<BigDecimal>): String {
        return source.joinToString(separator = SEPARATOR)
    }

    @TypeConverter
    fun toBigDecimalList(source: String): List<BigDecimal> {
        return source.split(SEPARATOR).map { BigDecimal(it) }
    }

    companion object {
        private const val SEPARATOR = "&"
    }
}