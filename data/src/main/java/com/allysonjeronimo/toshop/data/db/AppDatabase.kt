package com.allysonjeronimo.toshop.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.allysonjeronimo.toshop.data.db.dao.CategoryDao
import com.allysonjeronimo.toshop.data.db.dao.ItemDao
import com.allysonjeronimo.toshop.data.db.dao.ProductDao
import com.allysonjeronimo.toshop.data.db.dao.ShoppingListDao
import com.allysonjeronimo.toshop.data.db.entity.*

@Database(
    entities = [
        Category::class,
        CategoryName::class,
        Item::class,
        Product::class,
        ProductName::class,
        ShoppingList::class
    ],
    version = AppDatabase.DB_VERSION
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun categoryDao(): CategoryDao
    abstract fun productDao() : ProductDao
    abstract fun shoppingListDao() : ShoppingListDao
    abstract fun itemDao() : ItemDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        const val DB_NAME = "ToShop.db"
        const val DB_VERSION = 2

        fun getInstance(context: Context) : AppDatabase {
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