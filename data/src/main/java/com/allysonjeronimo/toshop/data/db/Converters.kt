package com.allysonjeronimo.toshop.data.db

import androidx.room.TypeConverter
import com.allysonjeronimo.toshop.common.utils.DateUtils
import java.util.*

class Converters {

    @TypeConverter
    fun dateFromString(value:String) : Date{
        return DateUtils.stringToDate(value)
    }

    @TypeConverter
    fun dateToString(date:Date) : String{
        return DateUtils.dateToString(date)
    }
}