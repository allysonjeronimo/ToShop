package com.allysonjeronimo.toshop.extensions

import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar

fun ProgressBar.setBigMax(max: Int) {
    this.max = max * 1000
}

fun ProgressBar.animateTo(progressTo: Int, startDelay: Long) {
    val animation = ObjectAnimator.ofInt(
        this,
        "progress",
        this.progress,
        progressTo * 1000
    )
    animation.duration = 500
    animation.interpolator = DecelerateInterpolator()
    animation.startDelay = startDelay
    animation.start()
}