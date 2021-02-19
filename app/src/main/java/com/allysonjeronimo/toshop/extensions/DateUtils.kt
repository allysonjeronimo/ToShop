package com.allysonjeronimo.toshop.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtils{
    companion object{

        const val DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"

        @JvmStatic
        fun stringToDate(dateStr:String, format:String = DEFAULT_DATE_PATTERN) =
            try{
                val formatter = SimpleDateFormat(format)
                formatter.parse(dateStr)
            }catch(ex:ParseException){
                throw IllegalArgumentException(ex.message)
            }

        @JvmStatic
        fun dateToString(date:Date, format:String = DEFAULT_DATE_PATTERN) =
            try {
                val formatter = SimpleDateFormat(format)
                formatter.format(date)
            }catch(ex:Exception) {
                throw IllegalArgumentException(ex.message)
            }

    }

}