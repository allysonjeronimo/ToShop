package com.allysonjeronimo.toshop.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.ui.shoppinglists.ShoppingListsActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_splash)

        if(savedInstanceState == null){
            showFragment(SplashFragment.newInstance(), SplashFragment.TAG)
        }

        openShoppingListsActivity()
    }

    private fun openShoppingListsActivity() {

        Handler(Looper.getMainLooper()).postDelayed({
            ShoppingListsActivity.open(this)
            finish()
        }, 1300)
    }

    private fun showFragment(fragment: Fragment, tag:String){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, tag)
            .commit()
    }
}