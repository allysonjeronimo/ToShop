package com.allysonjeronimo.toshop.ui.shoppinglists


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.legacy.entities.ShoppingList
import com.allysonjeronimo.toshop.data.legacy.utils.deviceInfo
import com.allysonjeronimo.toshop.data.legacy.utils.openPlayStore
import com.allysonjeronimo.toshop.data.legacy.utils.sendEmail
import com.allysonjeronimo.toshop.data.legacy.utils.tintMenuIcon
import com.allysonjeronimo.toshop.ui.about.AboutActivity
import com.allysonjeronimo.toshop.ui.shoppinglistform.ShoppingListFormFragment
import com.allysonjeronimo.toshop.ui.items.ItemsActivity
import com.allysonjeronimo.toshop.ui.preferences.PreferencesActivity
import com.allysonjeronimo.toshop.ui.shoppinglistform.ShoppingListFormActivity
import kotlinx.android.synthetic.main.activity_shopping_lists.*
import kotlinx.android.synthetic.main.content_shopping_lists.*
import kotlinx.android.synthetic.main.toolbar.*

class ShoppingListsActivity : AppCompatActivity(), ShoppingListsFragment.OnShoppingListClickListener,
    MenuItem.OnActionExpandListener, SearchView.OnQueryTextListener,
    ShoppingListFormFragment.OnShoppingListSaveListener {

    private var lastSearchTerm:String = ""

    private val shoppingListsFragment: ShoppingListsFragment by lazy{
        supportFragmentManager.findFragmentByTag(ShoppingListsFragment.TAG) as ShoppingListsFragment
    }

    private val drawerToggle: ActionBarDrawerToggle by lazy{
        ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.app_name, R.string.app_name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_shopping_lists)

        if(savedInstanceState == null)
            showFragment(ShoppingListsFragment.newInstance(), ShoppingListsFragment.TAG)

        fab_add.setOnClickListener {
            showListForm()
        }

        setupToolbar()
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        drawerToggle.isDrawerIndicatorEnabled = false
        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_logo_toolbar)

        drawerToggle.setToolbarNavigationClickListener {
            if(drawer_layout.isDrawerOpen(GravityCompat.START))
                drawer_layout.closeDrawer(GravityCompat.START)
            else
                drawer_layout.openDrawer(GravityCompat.START)
        }
        navigation_view.setNavigationItemSelectedListener {
                menuItem -> selectMenuOption(menuItem)
            true
        }
    }

    private fun selectMenuOption(menuItem:MenuItem){
        if(menuItem.itemId == R.id.action_about){
            AboutActivity.open(this)
        }
        if(menuItem.itemId == R.id.action_rate){
            openStore()
        }
        if(menuItem.itemId == R.id.action_feedback){
            sendEmail()
        }
        if(menuItem.itemId == R.id.action_settings){
            openSettings()
        }
        drawer_layout.closeDrawers()
    }

    private fun openSettings() {
        PreferencesActivity.open(this)
    }

    private fun openStore() {
        openPlayStore()
    }

    private fun sendEmail() {
        this.sendEmail(
            arrayOf(getString(R.string.text_email)),
            getString(R.string.text_about),
            this.deviceInfo()
        )
    }

    private fun showListForm(shoppingList: ShoppingList = ShoppingList()){
        ShoppingListFormActivity.open(this@ShoppingListsActivity, shoppingList)
    }

    private fun showFragment(fragment:Fragment, tag:String){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, tag)
            .commit()
    }

    override fun onClick(shoppingList: ShoppingList) {
        ItemsActivity.open(this@ShoppingListsActivity, shoppingList)
    }

    override fun onLongClick(shoppingList: ShoppingList) {
        showListForm(shoppingList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lists, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.setOnActionExpandListener(this)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.hint_search_list)
        searchView.setOnQueryTextListener(this)
        if(this.lastSearchTerm.isNotEmpty()){
            Handler(Looper.getMainLooper()).post{
                val query = lastSearchTerm
                searchItem.expandActionView()
                searchView.setQuery(query, true)
                searchView.clearFocus()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if(item.itemId == R.id.action_add_list){
            showListForm()
        }
        if(item.itemId == R.id.action_delete_all){
            shoppingListsFragment.deleteAllShoppingLists()
        }
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            tintMenuIcon(menu.getItem(0), android.R.color.white)
            menu.getItem(2).isEnabled = shoppingListsFragment.getShoppingListsCount() != 0
        }
        return true
    }

    override fun onMenuItemActionExpand(p0: MenuItem?) = true

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        lastSearchTerm = ""
        shoppingListsFragment.clearSearch()
        return true
    }

    override fun onQueryTextSubmit(query: String?) = true

    override fun onQueryTextChange(newText: String?): Boolean {
        lastSearchTerm = newText ?: ""
        shoppingListsFragment.search(lastSearchTerm)
        return true
    }

    override fun onSave(shoppingList: ShoppingList) {
        shoppingListsFragment.search(lastSearchTerm)
    }

    companion object{
        fun open(context: Context){
            val intent = Intent(context, ShoppingListsActivity::class.java)
            context.startActivity(intent)
        }
    }
}