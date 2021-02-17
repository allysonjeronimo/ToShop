package com.allysonjeronimo.toshop.legacy.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View


fun View.rotateAnimation(duration:Long, rotate:Boolean) : Boolean{
    this.animate().setDuration(duration)
        .setListener(object:AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
            }
        }).rotation(if(rotate) 180f else 0f)
    return rotate
}