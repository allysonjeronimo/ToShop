package com.allysonjeronimo.toshop.data

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun dateFromTimestamp(value:Long?) : Date?{
        return value?.let{ Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date:Date?) : Long?{
        return date?.time?.toLong()
    }
}