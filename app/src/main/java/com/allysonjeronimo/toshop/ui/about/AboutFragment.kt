package com.allysonjeronimo.toshop.ui.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.legacy.utils.deviceInfo
import com.allysonjeronimo.toshop.legacy.utils.sendEmail
import com.allysonjeronimo.toshop.legacy.utils.versionName
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment(R.layout.fragment_about) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.bt_contact.setOnClickListener {
            requireContext().sendEmail(
                arrayOf(getString(R.string.text_email)),
                requireContext().getString(R.string.text_about),
                requireContext().deviceInfo()
                )
        }

        this.tv_version_name.text = resources.getString(R.string.text_version, requireContext().versionName())
    }

    companion object {
        const val TAG = "AboutFragment"
        fun newInstance() = AboutFragment()
    }
}