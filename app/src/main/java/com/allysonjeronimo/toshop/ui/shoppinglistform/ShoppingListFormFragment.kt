package com.allysonjeronimo.toshop.ui.shoppinglistform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.legacy.entities.ShoppingList
import kotlinx.android.synthetic.main.fragment_shopping_list_form.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ShoppingListFormFragment() : Fragment(), ShoppingListFormContract.ShoppingListFormView {

    private val presenter: ShoppingListFormContract.ShoppingListFormPresenter by inject{
        parametersOf(this)
    }

    private val shoppingList: ShoppingList by lazy{
        arguments?.getParcelable<ShoppingList>(EXTRA_SHOPPING_LIST) as ShoppingList
    }

    companion object{
        const val TAG = "ListFormFragment"
        const val EXTRA_SHOPPING_LIST = "shopping_list"

        fun newInstance(shoppingList: ShoppingList) : ShoppingListFormFragment {
            val instance = ShoppingListFormFragment()
            instance.arguments = Bundle().apply{
                putParcelable(EXTRA_SHOPPING_LIST, shoppingList)
            }
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_list_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.bt_save.setOnClickListener {
            saveList()
        }

        this.et_description.setOnEditorActionListener { _, _, _ ->
            saveList()
            true
        }

        presenter.loadShoppingList(shoppingList)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    private fun defaultName() : String{
       return resources.getString(
                R.string.text_placeholder_shopping_list)
    }

    fun saveList(){
        val description = if(et_description.text.toString().isNotEmpty()) et_description.text.toString() else defaultName()
        val shoppingList = ShoppingList(
            shoppingList.id,
            description)

        if(presenter.saveShoppingList(shoppingList) && activity is OnShoppingListSaveListener){
            val callback = activity as OnShoppingListSaveListener
            callback.onSave(shoppingList)
        }
    }

    interface OnShoppingListSaveListener{
        fun onSave(shoppingList: ShoppingList)
    }

    override fun showShoppingList(shoppingList: ShoppingList) {
        et_description.setText(shoppingList.description)
        et_description.requestFocus()
    }

    override fun showDefaultValues() {
        et_description.setText(defaultName())
        et_description.requestFocus()
    }

    override fun errorSaveShoppingList() {
        Toast.makeText(requireContext(), R.string.msg_error_save, Toast.LENGTH_SHORT).show()
    }
}