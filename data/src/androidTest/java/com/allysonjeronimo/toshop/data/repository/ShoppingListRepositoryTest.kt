package com.allysonjeronimo.toshop.data.repository

import com.allysonjeronimo.toshop.data.db.InitDatabaseTest
import com.allysonjeronimo.toshop.data.mapper.ItemEntityToModelMapper
import com.allysonjeronimo.toshop.data.mapper.ItemModelToEntityMapper
import com.allysonjeronimo.toshop.data.mapper.ShoppingListEntityToModelMapper
import com.allysonjeronimo.toshop.data.mapper.ShoppingListModelToEntityMapper
import com.allysonjeronimo.toshop.domain.entity.Item
import com.allysonjeronimo.toshop.domain.entity.ShoppingList
import com.allysonjeronimo.toshop.domain.repository.ItemRepository
import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class ShoppingListRepositoryTest : InitDatabaseTest() {

    private lateinit var shoppingListRepository: ShoppingListRepository
    private lateinit var itemRepository: ItemRepository

    @Before
    fun setup(){
        shoppingListRepository = ShoppingListRepositoryImpl(
            db.shoppingListDao(),
            ShoppingListEntityToModelMapper(),
            ShoppingListModelToEntityMapper()
        )

        itemRepository = ItemRepositoryImpl(
            db.itemDao(),
            ItemEntityToModelMapper(),
            ItemModelToEntityMapper()
        )
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
    fun delete_empty_shopping_list() = testScope.runBlockingTest {
        saveShoppingList()

        var results = shoppingListRepository.search("")

        assertEquals(1, results.size)

        shoppingListRepository.delete(results[0].id)

        results = shoppingListRepository.search("")

        assertEquals(0, results.size)
    }

    @Test
    fun delete_shopping_list_with_items() = testScope.runBlockingTest {
        saveShoppingList()

        var results = shoppingListRepository.search("")

        assertEquals(1, results.size)

        val item = mockItem(false, results[0].id)

        itemRepository.save(item)

        assertEquals(1, itemRepository.countByShoppingList(item.listId))

        shoppingListRepository.delete(results[0].id)

        results = shoppingListRepository.search("")

        assertEquals(0, results.size)
        assertEquals(0, itemRepository.countByShoppingList(item.listId))
    }

    companion object{
        const val SHOPPING_LIST_DESCRIPTION = "Shopping List 1"
        const val ITEM_DESCRIPTION = "Item 1"
        const val ITEM_PRICE = 1.99
        const val ITEM_QUANTITY = 1.0
    }
}