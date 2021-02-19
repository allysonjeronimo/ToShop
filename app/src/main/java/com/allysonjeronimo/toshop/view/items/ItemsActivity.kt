package com.allysonjeronimo.toshop.view.items

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.core.app.NavUtils
import androidx.fragment.app.Fragment
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.legacy.entities.Item
import com.allysonjeronimo.toshop.legacy.entities.ShoppingList
import com.allysonjeronimo.toshop.legacy.utils.PrefsHelper
import com.allysonjeronimo.toshop.view.itemform.ItemFormActivity
import kotlinx.android.synthetic.main.toolbar.*

class ItemsActivity : AppCompatActivity(), ItemsFragment.OnItemClickListener, ItemsFragment.OnShoppingListDeleteListener {

    private val shoppingList: ShoppingList by lazy{
        intent.getParcelableExtra<ShoppingList>(EXTRA_SHOPPING_LIST) as ShoppingList
    }

    private val itemsFragment: ItemsFragment by lazy{
        supportFragmentManager.findFragmentByTag(ItemsFragment.TAG) as ItemsFragment
    }

    companion object{
        const val EXTRA_SHOPPING_LIST = "shopping_list"

        fun open(context: Context, shoppingList: ShoppingList){
            val intent = Intent(context, ItemsActivity::class.java)
            intent.putExtra(EXTRA_SHOPPING_LIST, shoppingList)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        if(savedInstanceState == null){
            showFragment(ItemsFragment.newInstance(shoppingList.id), ItemsFragment.TAG)
        }
        else{
            finish()
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = shoppingList.description
    }

    override fun onStart() {
        super.onStart()

        if(PrefsHelper.getInstance(this).getBooleanValue(PrefsHelper.KEY_SCREEN_AWAKE))
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        else
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
    
    private fun showItemForm(item: Item = Item(listId = shoppingList.id)){
        ItemFormActivity.open(this, item)
    }

    private fun showFragment(fragment: Fragment, tag:String){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, tag)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_delete)
            itemsFragment.deleteShoppingList(shoppingList.id)
        if(item.itemId == R.id.action_delete_all)
            itemsFragment.deleteItems(shoppingList.id)
        if(item.itemId == R.id.action_check_all)
            itemsFragment.checkItems(shoppingList.id)
        if(item.itemId == R.id.action_uncheck_all)
            itemsFragment.uncheckItems(shoppingList.id)
        if(item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        NavUtils.navigateUpFromSameTask(this)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            if(itemsFragment.getItemsCount() == 0){
                for(i in 1..3){
                    menu.getItem(i).isEnabled = false
                }
            }
            else{
                for(i in 1..3){
                    menu.getItem(i).isEnabled = true
                }
        }}
        return true
    }

    override fun onClick(item: Item) {
        showItemForm(item)
    }

    override fun onDeleted() {
        finish()
    }

}