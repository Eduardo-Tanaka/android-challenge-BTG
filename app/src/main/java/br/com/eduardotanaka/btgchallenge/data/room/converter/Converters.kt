package br.com.eduardotanaka.btgchallenge.data.room.converter

import androidx.room.TypeConverter
import br.com.eduardotanaka.btgchallenge.util.DateTimeUtil.defaultFormatter
import br.com.eduardotanaka.btgchallenge.util.DateTimeUtil.localDateTimeFormatter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

class Converters {

    @TypeConverter
    fun fromOffsetDateTime(date: LocalDate?): String? {
        return date?.format(defaultFormatter)
    }

    @TypeConverter
    fun toOffsetDateTime(value: String?): LocalDate? {
        return value?.let {
            return defaultFormatter.parse(value, LocalDate::from)
        }
    }

    @TypeConverter
    fun fromOffsetLocalDateTime(date: LocalDateTime?): String? {
        return date?.format(localDateTimeFormatter)
    }

    @TypeConverter
    fun toOffsetLocalDateTime(value: String?): LocalDateTime? {
        return value?.let {
            return localDateTimeFormatter.parse(value, LocalDateTime::from)
        }
    }
}