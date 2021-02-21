package com.allysonjeronimo.toshop.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        // recreate tables with new constraints

        // create temp tables
        val createTempTables = arrayOf(
            "CREATE TABLE IF NOT EXISTS `CategoryTmp` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `resource_icon_name` TEXT NOT NULL)",
            "CREATE TABLE IF NOT EXISTS `CategoryNameTmp` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `locale` TEXT NOT NULL, `name` TEXT NOT NULL, `category_id` INTEGER NOT NULL)",
            "CREATE TABLE IF NOT EXISTS `ProductTmp` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `category_id` INTEGER NOT NULL)",
            "CREATE TABLE IF NOT EXISTS `ProductNameTmp` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `locale` TEXT NOT NULL, `name` TEXT NOT NULL, `product_id` INTEGER NOT NULL)",
            "CREATE TABLE IF NOT EXISTS `ListTmp` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `description` TEXT NOT NULL, `last_update` TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP)",
            "CREATE TABLE IF NOT EXISTS `ItemTmp` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `description` TEXT NOT NULL, `quantity` REAL NOT NULL DEFAULT 1, `unit` TEXT DEFAULT 'UN', `price` REAL NOT NULL DEFAULT 0.0, `notes` TEXT, `purchased` INTEGER NOT NULL DEFAULT 0, `last_update` TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, `category_id` INTEGER NOT NULL DEFAULT 1, `list_id` INTEGER NOT NULL, FOREIGN KEY(`list_id`) REFERENCES `List`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE)"
        )

        createTempTables.forEach {
            database.execSQL(it)
        }

        // copy the data
        val insertFromOldTables = arrayOf(
            "INSERT INTO `CategoryTmp` (`id`, `resource_icon_name`) SELECT `id`, `resource_icon_name` FROM `Category`",
            "INSERT INTO `CategoryNameTmp` (`id`, `locale`, `name`, `category_id`) SELECT `id`, `locale`, `name`, `category_id` FROM `CategoryName`",
            "INSERT INTO `ProductTmp` (`id`, `category_id`) SELECT `id`, `category_id` FROM `Product`",
            "INSERT INTO `ProductNameTmp` (`id`, `locale`, `name`, `product_id`) SELECT `id`, `locale`, `name`, `product_id` FROM `ProductName`",
            "INSERT INTO `ListTmp` (`id`, `description`, `last_update`) SELECT `id`, `description`, `last_update` FROM `List`",
            "INSERT INTO `ItemTmp` (`id`, `description`, `quantity`, `unit`, `price`, `notes`, `purchased`, `last_update`, `category_id`, `list_id`) SELECT `id`, `description`, `quantity`, `unit`, `price`, `notes`, `purchased`, `last_update`, `category_id`, `list_id` FROM `Item`"
        )


        insertFromOldTables.forEach {
            database.execSQL(it)
        }

        // remove the old table
        val removeOldTables = arrayOf(
            "DROP TABLE `Item`",
            "DROP TABLE `List`",
            "DROP TABLE `ProductName`",
            "DROP TABLE `Product`",
            "DROP TABLE `CategoryName`",
            "DROP TABLE `Category`"
        )

        removeOldTables.forEach {
            database.execSQL(it)
        }

        // rename the temp table to the correct one
        val renameTempTables = arrayOf(
            "ALTER TABLE `CategoryTmp` RENAME TO `Category`",
            "ALTER TABLE `CategoryNameTmp` RENAME TO `CategoryName`",
            "ALTER TABLE `ProductTmp` RENAME TO `Product`",
            "ALTER TABLE `ProductNameTmp` RENAME TO `ProductName`",
            "ALTER TABLE `ListTmp` RENAME TO `List`",
            "ALTER TABLE `ItemTmp` RENAME TO `Item`"
        )

        renameTempTables.forEach {
            database.execSQL(it)
        }

    }
}