package com.allysonjeronimo.toshop.ui.itemform

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.extensions.tintIcon
import com.allysonjeronimo.toshop.legacy.entities.Item
import kotlinx.android.synthetic.main.toolbar.*

class ItemFormActivity : AppCompatActivity(), ItemFormFragment.OnItemSaveListener {

    private val item: Item by lazy{
        intent.getParcelableExtra<Item>(EXTRA_ITEM) as Item
    }

    private val itemFormFragment: ItemFormFragment by lazy{
        supportFragmentManager.findFragmentByTag(ItemFormFragment.TAG) as ItemFormFragment
    }

    companion object{
        const val EXTRA_ITEM = "item"
        fun open(context: Context, item: Item){
            val intent = Intent(context, ItemFormActivity::class.java)
            intent.putExtra(EXTRA_ITEM, item)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_form)

        if(savedInstanceState == null) {
            showFragment(
                ItemFormFragment.newInstance(item),
                ItemFormFragment.TAG
            )
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(tintIcon(R.drawable.ic_close, android.R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = item.description ?: resources.getString(R.string.text_new_item)
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
        if(item.itemId == R.id.action_save){
            itemFormFragment.saveItem()
        }
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSave() {
        finish()
    }
}