package com.allysonjeronimo.toshop.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.allysonjeronimo.toshop.data.db.AppDatabase
import com.allysonjeronimo.toshop.data.db.DataHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
open class BaseRepositoryTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    val testScope = TestCoroutineScope(testDispatcher)
    lateinit var db: AppDatabase

    @Before
    fun initDatabase(){
        db = mockDatabase()
    }

    @After
    fun closeDatabase(){
        db.close()
    }

    private fun mockDatabase() : AppDatabase {
        return Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java)
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .addCallback(object:RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    DataHelper.getInstance( InstrumentationRegistry.getInstrumentation().targetContext).initDatabase(db)
                }
            })
            .build()
    }
}