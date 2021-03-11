package com.allysonjeronimo.toshop.data.db.dao

import androidx.room.*
import com.allysonjeronimo.toshop.data.db.entity.ShoppingListEntity
import com.allysonjeronimo.toshop.data.db.entity.ShoppingListWithDetails

@Dao
internal interface ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(shoppingList: ShoppingListEntity)

    @Query("DELETE FROM List WHERE id = :shoppingListId")
    suspend fun delete(shoppingListId:Long)

    @Query("DELETE FROM List")
    suspend fun deleteAll()

    @Query("SELECT l.*, 0 as quantity_items, 0 as quantity_purchased_items, 0.0 as total FROM List l ORDER BY l.id DESC")
    suspend fun findAll() : List<ShoppingListWithDetails>

    @Query("""
        SELECT l.*,
         (SELECT Count(i.id) FROM Item i WHERE i.list_id = l.id) as quantity_items,
         (SELECT Count(i.id) FROM Item i WHERE i.list_id = l.id AND i.purchased = 1) as quantity_purchased_items,
         (SELECT Coalesce(Sum(i.quantity * i.price), 0.0) FROM Item i WHERE i.list_id = l.id AND i.purchased = 1) as total
        FROM List l
        WHERE l.description like :term
        ORDER BY l.description ASC
    """)
    suspend fun search(term:String) : List<ShoppingListWithDetails>

    @Query("SELECT Count(id) FROM List")
    suspend fun count() : Int
}