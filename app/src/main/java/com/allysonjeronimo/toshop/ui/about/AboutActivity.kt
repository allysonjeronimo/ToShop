package com.allysonjeronimo.toshop.ui.about

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.allysonjeronimo.toshop.R
import kotlinx.android.synthetic.main.toolbar.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_about)

        if(savedInstanceState == null)
            showFragment(AboutFragment(), AboutFragment.TAG)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.text_about)
    }

    private fun showFragment(fragment: Fragment, tag:String){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, tag)
            .commit()
    }

    companion object{
        fun open(context: Context){
            val intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }
    }
}