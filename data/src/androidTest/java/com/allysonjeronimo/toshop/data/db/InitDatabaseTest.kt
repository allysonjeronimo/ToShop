package com.allysonjeronimo.toshop.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.allysonjeronimo.toshop.data.db.dao.CategoryDao
import com.allysonjeronimo.toshop.data.db.dao.ProductDao
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
open class InitDatabaseTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    val testScope = TestCoroutineScope(testDispatcher)
    internal lateinit var db: AppDatabase

    private lateinit var categoryDao: CategoryDao
    private lateinit var productDao: ProductDao

    @Before
    fun initDatabase(){
        db = mockDatabase()
        categoryDao = db.categoryDao()
        productDao = db.productDao()
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

    @Test
    fun insert_default_categories_on_create() = testScope.runBlockingTest{
        val categoriesPtBR = categoryDao.findAll(LOCALE_PT_BR)
        val categoriesEnUS = categoryDao.findAll(LOCALE_EN_US)

        Assert.assertTrue(categoriesPtBR.isNotEmpty())
        Assert.assertTrue(categoriesEnUS.isNotEmpty())
    }

    @Test
    fun insert_default_products_on_create() = testScope.runBlockingTest{
        val productsPtBR = productDao.search("", LOCALE_PT_BR)

        Assert.assertTrue(productsPtBR.isNotEmpty())
    }

    companion object{
        const val LOCALE_PT_BR = "pt-BR"
        const val LOCALE_EN_US = "en-US"
    }
}