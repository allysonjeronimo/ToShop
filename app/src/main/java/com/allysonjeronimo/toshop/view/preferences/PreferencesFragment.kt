package com.allysonjeronimo.toshop.view.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.allysonjeronimo.toshop.R

class PreferencesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}