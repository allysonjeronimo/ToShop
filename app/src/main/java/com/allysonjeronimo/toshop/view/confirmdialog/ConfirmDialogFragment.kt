package com.allysonjeronimo.toshop.view.confirmdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.allysonjeronimo.toshop.R
import kotlinx.android.synthetic.main.fragment_categories_dialog.fab_close
import kotlinx.android.synthetic.main.fragment_confirm_dialog.*

class ConfirmDialogFragment : DialogFragment() {

    private lateinit var listener:OnConfirmDialogListener

    companion object{
        const val TAG = "ConfirmDialogFragment"
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_MESSAGE = "message"

        fun newInstance(title:String, message:String, listener:OnConfirmDialogListener) : ConfirmDialogFragment{
            val instance = ConfirmDialogFragment()
            instance.arguments = Bundle().apply{
                putString(EXTRA_TITLE, title)
                putString(EXTRA_MESSAGE, message)
            }
            instance.listener = listener
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_corners_white)
        return inflater.inflate(R.layout.fragment_confirm_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_close.setOnClickListener {
            dismiss()
            listener.onCancel()
        }

        bt_ok.setOnClickListener {
            dismiss()
            listener.onConfirm()
        }

        bt_cancel.setOnClickListener {
            dismiss()
            listener.onCancel()
        }

        tv_title.text = arguments?.getString(EXTRA_TITLE)
        tv_message.text = arguments?.getString(EXTRA_MESSAGE)
    }


    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    interface OnConfirmDialogListener{
        fun onConfirm()
        fun onCancel()
    }
}