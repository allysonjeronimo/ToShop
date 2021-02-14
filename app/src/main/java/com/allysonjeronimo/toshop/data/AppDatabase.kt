package com.allysonjeronimo.toshop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.allysonjeronimo.toshop.data.entity.*

@Database(
    entities = [
        Category::class,
        CategoryName::class,
        Item::class,
        Product::class,
        ProductName::class,
        ShoppingList::class
    ],
    version = DB_VERSION
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){

    companion object{
        @Volatile
        private var INSTANCE:AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME
                    ).build()
                }
                return instance
            }
        }
    }
}