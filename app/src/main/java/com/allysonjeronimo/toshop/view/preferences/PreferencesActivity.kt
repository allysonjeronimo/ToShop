package com.allysonjeronimo.toshop.view.preferences

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.allysonjeronimo.toshop.R
import kotlinx.android.synthetic.main.toolbar.*

class PreferencesActivity : AppCompatActivity(){

    companion object{
        fun open(context: Context){
            val intent = Intent(context, PreferencesActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_preferences)

        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, PreferencesFragment())
                .commit()
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.action_settings)
    }

}