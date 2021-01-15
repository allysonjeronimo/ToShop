package com.allysonjeronimo.toshop.view.shoppinglists

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.model.entities.ShoppingList
import com.allysonjeronimo.toshop.model.facade.ToShopFacadeImpl
import com.allysonjeronimo.toshop.view.confirmdialog.ConfirmDialogFragment
import kotlinx.android.synthetic.main.fragment_shopping_lists.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ShoppingListsFragment : Fragment(), ShoppingListsContract.ShoppingListsView {

    private val presenter: ShoppingListsContract.ShoppingListsPresenter by inject{
        parametersOf(this)
    }

    private lateinit var adapter: ShoppingListAdapter

    companion object{
        const val TAG = "ListsFragment"
        fun newInstance() = ShoppingListsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        rv_list.layoutManager = LinearLayoutManager(activity as Context)
        adapter = ShoppingListAdapter(selectListener = activity as OnShoppingListClickListener)
        rv_list.adapter = adapter
        initSwipeGesture()
    }

    override fun onStart() {
        super.onStart()
        presenter.searchShoppingLists("")
    }

    private fun initSwipeGesture(){
        val swipe = object:ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val shoppingList = adapter.shoppingLists?.get(position)
                if(shoppingList != null){

                    val listener = object:ConfirmDialogFragment.OnConfirmDialogListener{
                        override fun onConfirm() {
                            presenter.deleteShoppingList(shoppingList)
                        }

                        override fun onCancel() {
                            presenter.searchShoppingLists("")
                        }
                    }

                    ConfirmDialogFragment.newInstance(getString(R.string.text_delete), getString(R.string.msg_confirm_delete_list), listener)
                        .show(activity!!.supportFragmentManager, ConfirmDialogFragment.TAG)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(rv_list)
    }

    override fun showShoppingLists(shoppingLists: kotlin.collections.List<ShoppingList>) {
        this.adapter.setList(shoppingLists.toMutableList())
        this.rv_list.visibility = View.VISIBLE
        this.gp_empty.visibility = View.GONE
    }

    override fun showEmptyView() {
        this.rv_list.visibility = View.GONE
        this.gp_empty.visibility = View.VISIBLE
    }

    override fun deleteShoppingList(shoppingList: ShoppingList) {
        adapter.deleteShoppingList(shoppingList)
    }

    fun deleteAllShoppingLists(){
        val listener = object:ConfirmDialogFragment.OnConfirmDialogListener{
            override fun onConfirm() {
                adapter.setList(mutableListOf<ShoppingList>())
                presenter.deleteAllShoppingLists()
            }
            override fun onCancel() {}
        }

        ConfirmDialogFragment.newInstance(getString(R.string.text_delete), getString(R.string.msg_confirm_delete_lists), listener)
            .show(activity!!.supportFragmentManager, ConfirmDialogFragment.TAG)
    }

    fun getShoppingListsCount() : Int{
        return adapter.itemCount
    }

    fun search(term:String){
        presenter.searchShoppingLists(term)
    }

    fun clearSearch(){
       presenter.searchShoppingLists("")
    }

    interface OnShoppingListClickListener{
        fun onClick(shoppingList: ShoppingList)
        fun onLongClick(shoppingList: ShoppingList)
    }
}