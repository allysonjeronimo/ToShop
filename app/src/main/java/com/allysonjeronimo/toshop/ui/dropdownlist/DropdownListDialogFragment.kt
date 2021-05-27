package com.allysonjeronimo.toshop.ui.dropdownlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.allysonjeronimo.toshop.R
import kotlinx.android.synthetic.main.fragment_dropdown_list_dialog.*

class DropdownListDialogFragment() : DialogFragment() {

    private lateinit var listener: OnItemClickListener
    private lateinit var items:List<String>

    companion object{
        const val TAG = "DropdownListDialogFragment"

        fun newInstance(list:List<String>, listener: OnItemClickListener) : DropdownListDialogFragment {
            val instance = DropdownListDialogFragment()
            instance.items = list
            instance.listener = listener
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dropdown_list_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(items.isNotEmpty()){
            rv_items.layoutManager = LinearLayoutManager(requireContext())
            rv_items.adapter = DropdownListAdapter(items, this::onClick)
        }
        else{
            dismiss()
        }
    }

    private fun onClick(item:String){
        listener.onClick(item)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, height)
    }

    interface OnItemClickListener{
        fun onClick(item:String)
    }
}