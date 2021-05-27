package com.allysonjeronimo.toshop.ui.shoppinglistform

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.extensions.tintIcon
import com.allysonjeronimo.toshop.legacy.entities.ShoppingList
import com.allysonjeronimo.toshop.ui.items.ItemsActivity
import kotlinx.android.synthetic.main.toolbar.*

class ShoppingListFormActivity : AppCompatActivity(), ShoppingListFormFragment.OnShoppingListSaveListener{

    private val shoppingList: ShoppingList by lazy{
        intent.getParcelableExtra<ShoppingList>(EXTRA_SHOPPING_LIST) as ShoppingList
    }

    private val shoppingListFormFragment:ShoppingListFormFragment by lazy{
        supportFragmentManager.findFragmentByTag(ShoppingListFormFragment.TAG) as ShoppingListFormFragment
    }

    companion object{
        const val EXTRA_SHOPPING_LIST = "shopping_list"
        fun open(context: Context, shoppingList: ShoppingList){
            val intent = Intent(context, ShoppingListFormActivity::class.java)
            intent.putExtra(EXTRA_SHOPPING_LIST, shoppingList)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list_form)

        if(savedInstanceState == null){
            showFragment(
                ShoppingListFormFragment.newInstance(shoppingList),
                ShoppingListFormFragment.TAG
            )
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(tintIcon(R.drawable.ic_close, android.R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if(shoppingList.id != 0L) shoppingList.description else resources.getString(R.string.text_new_list)
    }

    private fun showFragment(fragment: Fragment, tag:String){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, tag)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_save)
            shoppingListFormFragment.saveList()
        if(item.itemId == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
       // if (menu != null)
            //tintMenuIcon(menu.findItem(android.R.id.home), android.R.color.white)
        return true
    }

    override fun onSave(shoppingList: ShoppingList) {
        ItemsActivity.open(this, shoppingList)
    }
}