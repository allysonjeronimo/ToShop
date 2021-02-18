package com.allysonjeronimo.toshop.repository

import com.allysonjeronimo.toshop.data.entity.Item
import com.allysonjeronimo.toshop.data.entity.ShoppingList
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class ShoppingListRepositoryTest : BaseRepositoryTest() {

    private lateinit var shoppingListRepository: ShoppingListRepository
    private lateinit var itemRepository: ItemRepository

    @Before
    fun setup(){
        val db = mockDatabase()
        shoppingListRepository = ShoppingListDataSource(db.shoppingListDao())
        itemRepository = ItemDataSource(db.itemDao())
    }

    private fun mockShoppingList() : ShoppingList {
        return ShoppingList(description = SHOPPING_LIST_DESCRIPTION)
    }

    private fun mockItem(purchased:Boolean = false, shoppingListId:Long = 0L) : Item {
        return Item(
            description = ITEM_DESCRIPTION,
            quantity = ITEM_QUANTITY,
            purchased = purchased,
            price = ITEM_PRICE,
            listId = shoppingListId
        )
    }

    private suspend fun saveShoppingList(){
        val shoppingList = mockShoppingList()
        shoppingListRepository.save(shoppingList)
    }

    @Test
    fun insert_empty_shopping_list() = testScope.runBlockingTest{
        saveShoppingList()

        val results = shoppingListRepository.search("")

        assertEquals(1, results.size)
        assertEquals(0, results[0].quantityItems)
        assertEquals(0, results[0].quantityPurchasedItems)
        assertEquals(0.0, results[0].total)
    }

    @Test
    fun insert_shopping_list_with_unchecked_items() = testScope.runBlockingTest {
        saveShoppingList()

        var shoppingLists = shoppingListRepository.search(SHOPPING_LIST_DESCRIPTION)

        val item = mockItem(false, shoppingLists[0].id)

        itemRepository.save(item)

        shoppingLists = shoppingListRepository.search(SHOPPING_LIST_DESCRIPTION)

        assertEquals(1, shoppingLists.size)
        assertEquals(1, itemRepository.countByShoppingList(item.listId))
        assertEquals(1, shoppingLists[0].quantityItems)
        assertEquals(0, shoppingLists[0].quantityPurchasedItems)
        assertEquals(0.0, shoppingLists[0].total)
    }

    @Test
    fun insert_shopping_list_with_checked_items() = testScope.runBlockingTest {
        saveShoppingList()

        var shoppingLists = shoppingListRepository.search(SHOPPING_LIST_DESCRIPTION)

        val item = mockItem(true, shoppingLists[0].id)

        itemRepository.save(item)

        shoppingLists = shoppingListRepository.search(SHOPPING_LIST_DESCRIPTION)

        assertEquals(1, shoppingLists.size)
        assertEquals(1, itemRepository.countByShoppingList(item.listId))
        assertEquals(1, shoppingLists[0].quantityItems)
        assertEquals(1, shoppingLists[0].quantityPurchasedItems)
        assertEquals(ITEM_PRICE, shoppingLists[0].total)
    }

    @Test
    fun delete_shopping_list() = testScope.runBlockingTest {
        saveShoppingList()

        var results = shoppingListRepository.search("")

        assertEquals(1, results.size)

        shoppingListRepository.delete(results[0].id)

        results = shoppingListRepository.search("")

        assertEquals(0, results.size)
    }

    companion object{
        const val SHOPPING_LIST_DESCRIPTION = "Shopping List 1"
        const val ITEM_DESCRIPTION = "Item 1"
        const val ITEM_PRICE = 1.99
        const val ITEM_QUANTITY = 1.0
    }
}