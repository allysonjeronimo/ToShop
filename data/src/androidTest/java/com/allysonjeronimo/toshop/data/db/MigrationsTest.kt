package com.allysonjeronimo.toshop.data.db

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MigrationsTest {

    @get:Rule
    val helper:MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Before
    fun setupDatabase(){
        try{

        }catch(ex:Exception){
            ex.printStackTrace()
        }
    }

    @After
    fun clearDatabase(){

    }

    @Test
    @Throws(IOException::class)
    fun migrate1To2(){

    }

    companion object{
        const val TEST_DB_NAME = "ToShop-Test.db"
    }
}