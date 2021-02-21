package com.allysonjeronimo.toshop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.allysonjeronimo.toshop.data.dao.CategoryDao
import com.allysonjeronimo.toshop.data.dao.ItemDao
import com.allysonjeronimo.toshop.data.dao.ProductDao
import com.allysonjeronimo.toshop.data.dao.ShoppingListDao
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

    abstract fun categoryDao(): CategoryDao
    abstract fun productDao() : ProductDao
    abstract fun shoppingListDao() : ShoppingListDao
    abstract fun itemDao() : ItemDao

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
                    ).addMigrations(MIGRATION_1_2)
                        .addCallback(object:Callback(){
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                DataHelper.getInstance(context).initDatabase(db)
                            }
                        })
                        .build()
                }
                return instance
            }
        }
    }


}