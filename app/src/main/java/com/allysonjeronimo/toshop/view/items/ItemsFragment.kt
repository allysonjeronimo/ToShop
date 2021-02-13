package com.allysonjeronimo.toshop.view.items

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat

import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.legacy.entities.Item
import com.allysonjeronimo.toshop.legacy.entities.Product
import com.allysonjeronimo.toshop.utils.*
import com.allysonjeronimo.toshop.view.confirmdialog.ConfirmDialogFragment
import kotlinx.android.synthetic.main.fragment_items.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ItemsFragment : Fragment(), ItemsContract.ItemsView{

    private val presenter: ItemsContract.ItemsPresenter by inject{
            parametersOf(this)
    }

    private lateinit var adapter: ItemAdapter
    private var shoppingListId: Long = 0L
    private var isShowSummaryBar = true

    companion object{

        const val TAG = "ItemsFragment"
        private const val EXTRA_SHOPPING_LIST_ID = "shopping_list_id"

        fun newInstance(shoppingListId:Long): ItemsFragment {
            val bundle = Bundle()
            bundle.putLong(EXTRA_SHOPPING_LIST_ID, shoppingListId)
            val instance = ItemsFragment()
            instance.arguments = bundle
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shoppingListId = arguments!!.getLong(EXTRA_SHOPPING_LIST_ID, 0L)
        initRecyclerView()
        initAutoCompleteTextView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initAutoCompleteTextView(){
        et_description.setAdapter(ProductSearchAdapter(requireContext(), R.layout.item_product, this::searchProducts))

        et_description.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                var iconSearch = requireContext().drawable(R.drawable.ic_search)
                //val iconSearch = requireContext().resources.getDrawable(R.drawable.ic_search)
                //DrawableCompat.setTint(iconSearch, resources.getColor(R.color.lightGray))

                requireContext().tintIcon(R.drawable.ic_search, R.color.lightGray)

                if(s.toString().trim().isNotEmpty()){
                    var iconClose = requireContext().drawable(R.drawable.ic_close)
                    requireContext().tintIcon(R.drawable.ic_close, R.color.lightGray)
                    //val iconClose = requireContext().resources.getDrawable(R.drawable.ic_close)
                    //DrawableCompat.setTint(iconClose, resources.getColor(R.color.lightGray))
                    et_description.setCompoundDrawablesWithIntrinsicBounds(iconSearch, null, iconClose, null)
                }
                else{
                    et_description.setCompoundDrawablesWithIntrinsicBounds(iconSearch, null, null, null)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        et_description.setOnTouchListener { _, event ->
            val right = 2
            if(event.action == MotionEvent.ACTION_UP && et_description.compoundDrawables[right] != null){
                if(event.rawX >= (et_description.right - et_description.compoundDrawables[right].bounds.width())){
                    clearInputDescription()
                }
            }
            false
        }

        et_description.setOnItemClickListener {
                adapterView, _, i, _ ->
            val product = adapterView.getItemAtPosition(i) as Product
            presenter.saveItem(product.getItem(shoppingListId))
        }

        et_description.setOnEditorActionListener { _, _, _ ->
            if(et_description.text.trim().isNotEmpty()){
                val item = Item(
                    description=et_description.text.toString(),
                    listId=shoppingListId
                )
                presenter.saveItem(item)
            }
            true
        }
    }

    override fun clearInputDescription(){
        et_description.text.clear()
        val iconSearch = ContextCompat.getDrawable(requireContext(), R.drawable.ic_search)
        if(iconSearch != null)
            DrawableCompat.setTint(iconSearch, ContextCompat.getColor(requireContext(), R.color.lightGray))
        et_description.setCompoundDrawablesWithIntrinsicBounds(iconSearch, null, null, null)
        requireContext().hideKeyboard(et_description)
    }

    private fun searchProducts(term:String) : List<Product>{
        return presenter.searchProducts(term)
    }

    private fun initRecyclerView(){
        rv_list.layoutManager = LinearLayoutManager(requireContext())
        adapter = ItemAdapter(
            clickListener = activity as OnItemClickListener,
            checkListener = object : OnItemCheckListener {
                override fun onCheck(item: Item) {
                    presenter.checkItem(item)
                }
            }
        )
        rv_list.adapter = adapter
        initSwipeGesture()
    }

    private fun initSwipeGesture(){
        val swipe = object:ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = adapter.items?.get(position)
                if(item != null){
                    presenter.deleteItem(item)
                }
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(rv_list)
    }

    override fun onStart() {
        super.onStart()

        adapter.isMoveItemsToBottom =
            PrefsHelper.getInstance(requireContext())
                .getBooleanValue(PrefsHelper.KEY_MOVE_ITENS)

        isShowSummaryBar = PrefsHelper.getInstance(requireContext()).getBooleanValue(PrefsHelper.KEY_SHOW_SUMMARY)
        view_summary.visibility = if(isShowSummaryBar) View.VISIBLE else View.GONE

        presenter.loadItems(shoppingListId)
    }

    fun getItemsCount() : Int{
        return adapter.itemCount
    }

    override fun showItems(items: List<Item>) {
        this.adapter.setList(items.toMutableList())
        this.rv_list.visibility = View.VISIBLE
        this.gp_empty.visibility = View.GONE
    }

    override fun showItemsSummary() {
        if(isShowSummaryBar){
            this.tv_items_count.text = "${adapter.countCheckedItems()}/${adapter.itemCount}"
            this.tv_items_total.text = "${adapter.totalCheckedItems().toCurrency()}"
            this.pb_status.setBigMax(adapter.itemCount)
            this.pb_status.animateTo(adapter.countCheckedItems(), 100)

            if(adapter.itemCount > 0 && adapter.itemCount == adapter.countCheckedItems()){
                DrawableCompat.setTint(this.pb_status.progressDrawable, ContextCompat.getColor(requireContext(), R.color.lightGreen))
            }
            else if(adapter.itemCount > 0 && adapter.itemCount != adapter.countCheckedItems()){
                DrawableCompat.setTint(this.pb_status.progressDrawable, ContextCompat.getColor(requireContext(), R.color.lightOrange))
            }
            else{
                DrawableCompat.setTint(this.pb_status.progressDrawable, ContextCompat.getColor(requireContext(), R.color.lightGray))
            }
        }
    }

    override fun showEmptyView() {
        this.rv_list.visibility = View.GONE
        this.gp_empty.visibility = View.VISIBLE
    }

    override fun errorSaveItem() {
        Toast.makeText(requireContext(), R.string.msg_error_save, Toast.LENGTH_SHORT).show()
    }

    override fun deleteItem(item: Item) {
        adapter.deleteItem(item)
    }

    override fun deleteItems() {
        adapter.deleteItems()
    }

    override fun checkItems() {
        adapter.checkItems()
    }

    override fun uncheckItems() {
        adapter.uncheckItems()
    }

    fun deleteItems(shoppingListId: Long){
        val listener = object: ConfirmDialogFragment.OnConfirmDialogListener{
            override fun onConfirm() {
                presenter.deleteAll(shoppingListId)
            }
            override fun onCancel() {
            }
        }
        ConfirmDialogFragment.newInstance(getString(R.string.text_delete), getString(R.string.msg_confirm_delete_items), listener)
            .show(activity!!.supportFragmentManager, ConfirmDialogFragment.TAG)
    }

    fun deleteShoppingList(shoppingListId:Long){
        val listener = object: ConfirmDialogFragment.OnConfirmDialogListener{
            override fun onConfirm() {
                presenter.deleteShoppingList(shoppingListId)
                if(activity is OnShoppingListDeleteListener){
                    val listener = activity as OnShoppingListDeleteListener
                    listener.onDeleted()
                }
            }
            override fun onCancel() {
            }
        }
        ConfirmDialogFragment.newInstance(getString(R.string.text_delete), getString(R.string.msg_confirm_delete_list), listener)
            .show(activity!!.supportFragmentManager, ConfirmDialogFragment.TAG)
    }

    fun checkItems(shoppingListId: Long){
        presenter.checkAll(shoppingListId)
    }

    fun uncheckItems(shoppingListId: Long){
        presenter.uncheckAll(shoppingListId)
    }

    interface OnItemClickListener{
        fun onClick(item: Item)
    }

    interface OnItemCheckListener{
        fun onCheck(item: Item)
    }

    interface OnShoppingListDeleteListener{
        fun onDeleted()
    }
}




