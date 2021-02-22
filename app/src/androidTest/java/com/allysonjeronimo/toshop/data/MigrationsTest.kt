package com.allysonjeronimo.toshop.data

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.allysonjeronimo.toshop.legacy.util.SQLiteDataHelper
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

    private lateinit var sqliteDatabaseHelper:SQLiteTestHelper

    @Before
    fun setupDatabase(){
        try{
            sqliteDatabaseHelper = SQLiteTestHelper(
                ApplicationProvider.getApplicationContext(),
                TEST_DB_NAME
            )
            val db = sqliteDatabaseHelper.writableDatabase
            SQLiteDataHelper.getInstance(
                ApplicationProvider.getApplicationContext()
            ).initDatabase(db)
            db.close()
        }catch(ex:Exception){
            ex.printStackTrace()
        }
    }

    @After
    fun clearDatabase(){
        val db = sqliteDatabaseHelper.writableDatabase
        SQLiteDataHelper.getInstance(
            ApplicationProvider.getApplicationContext()
        ).clearDatabase(db)
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun migrate1To2(){
        helper.runMigrationsAndValidate(TEST_DB_NAME, 2, true, MIGRATION_1_2)
    }

    companion object{
        const val TEST_DB_NAME = "ToShop-Test.db"
    }
}