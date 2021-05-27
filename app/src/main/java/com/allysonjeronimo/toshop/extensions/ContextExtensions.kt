package com.allysonjeronimo.toshop.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.allysonjeronimo.toshop.R

fun Context.hideKeyboard(view: View){
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.resourceId(name: String) : Int{
    return this.resources.getIdentifier(
        name,
        "drawable",
        this.packageName
    )
}

fun Context.openPlayStore(){
    try {
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        )
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
        )
    }
}

fun Context.sendEmail(emails: Array<String>, subject: String, text: String){

    val selectorIntent = Intent(Intent.ACTION_SENDTO)
    selectorIntent.data = Uri.parse("mailto:")

    val emailIntent = Intent(Intent.ACTION_SEND)
    emailIntent.putExtra(Intent.EXTRA_EMAIL, emails)
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
    emailIntent.putExtra(Intent.EXTRA_TEXT, text)
    emailIntent.selector = selectorIntent

    this.startActivity(Intent.createChooser(emailIntent, getString(R.string.text_send_email)))
}

fun Context.deviceInfo() : String{
    return getString(
        R.string.device_info
    )
        .format(
            "${android.os.Build.VERSION.RELEASE}",
            "${android.os.Build.BRAND}",
            "${android.os.Build.MODEL}",
            this.versionName()
        )
}

fun Context.versionName() : String{
    val packageInfo = this.packageManager.getPackageInfo(packageName, 0)
    return packageInfo.versionName
}

fun Context.tintMenuIcon(item: MenuItem, @ColorRes color: Int){
    val normalDrawable = item.icon
    val wrapDrawable = DrawableCompat.wrap(normalDrawable)
    DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(this, color))
    item.icon = wrapDrawable
}

fun Context.color(resourceColor: Int) : Int{
    return ContextCompat.getColor(this, resourceColor)
}

fun Context.drawable(resourceDrawable: Int) : Drawable?{
    return ResourcesCompat.getDrawable(resources, resourceDrawable, null)
}

fun Context.tintIcon(resourceIcon: Int, resourceColor: Int) : Drawable?{
    val icon = ResourcesCompat.getDrawable(resources, resourceIcon, null)
    if(icon != null)
        DrawableCompat.setTint(icon, ContextCompat.getColor(this, resourceColor))
    return icon
}