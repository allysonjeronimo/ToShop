package com.allysonjeronimo.toshop.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.allysonjeronimo.toshop.data.entity.Item
import com.allysonjeronimo.toshop.data.entity.ItemWithCategoryIcon

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(item: Item)

    @Query("DELETE FROM Item WHERE id = :itemId")
    suspend fun delete(itemId:Long)

    @Query("DELETE FROM Item WHERE list_id = :shoppingListId")
    suspend fun deleteByShoppingList(shoppingListId:Long)

    @Query("UPDATE Item SET purchased = :purchased, last_update = datetime('now','localtime') WHERE list_id = :shoppingListId")
    suspend fun updatePurchasedByShoppingList(purchased:Boolean, shoppingListId:Long)

    @Query("""
        SELECT i.*, ct.resource_icon_name 
        FROM Item i, Category ct
        WHERE i.list_id = :shoppingListId 
        AND i.category_id = ct.id
        ORDER BY i.id DESC
    """)
    suspend fun findByShoppingList(shoppingListId: Long) : List<ItemWithCategoryIcon>

    @Query("SELECT Count(id) FROM Item WHERE list_id = :shoppingListId")
    suspend fun countByShoppingList(shoppingListId: Long) : Int

    @Query("SELECT Count(id) FROM Item")
    suspend fun count() : Int
}