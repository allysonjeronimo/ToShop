package com.allysonjeronimo.toshop.ui.itemform

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.data.legacy.entities.Item
import com.allysonjeronimo.toshop.data.legacy.entities.Category
import com.allysonjeronimo.toshop.legacy.utils.resourceId
import com.allysonjeronimo.toshop.ui.categories.CategoriesDialogFragment
import com.allysonjeronimo.toshop.ui.dropdownlist.DropdownListDialogFragment
import kotlinx.android.synthetic.main.fragment_item_form.*
import kotlinx.android.synthetic.main.fragment_item_form.iv_icon
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ItemFormFragment : Fragment(), ItemFormContract.ItemFormView {

    private val presenter: ItemFormContract.ItemFormPresenter by inject{
        parametersOf(this)
    }

    private val item: Item by lazy {
        arguments?.getParcelable<Item>(EXTRA_ITEM) as Item
    }

    companion object {
        const val TAG = "EditItemFragment"
        private const val EXTRA_ITEM = "item"

        fun newInstance(item: Item): ItemFormFragment {
            val instance = ItemFormFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_ITEM, item)
            instance.arguments = bundle
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_form, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_notes.setOnEditorActionListener { _, _, _ ->
            saveItem()
            true
        }

        bt_save.setOnClickListener {
            saveItem()
        }

        fab_category.setOnClickListener {
            showAlertDialogCategories()
        }

        et_unit.setOnTouchListener { _, motionEvent ->
            val right = 2

            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (motionEvent.rawX >= (et_unit.right - et_unit.compoundDrawables[right].bounds.width())) {
                    showAlertDialogUnits()
                }
            }
            true
        }

        presenter.loadItem(item)
    }

    private fun showAlertDialogCategories(){

        val listener = object : CategoriesDialogFragment.OnCategoryClickListener {

            override fun onClick(category: Category) {
                iv_icon.setImageResource(requireContext().resourceId(category.resourceIconName))
                item.categoryId = category.id
            }
        }

        CategoriesDialogFragment.newInstance(item.categoryId, listener)
            .show(requireActivity().supportFragmentManager, CategoriesDialogFragment.TAG)
    }

    private fun showAlertDialogUnits() {
        val listener = object : DropdownListDialogFragment.OnItemClickListener {
            override fun onClick(item: String) {
                et_unit.setText(item)
                et_unit.requestFocus()
                et_unit.selectAll()
            }
        }

        DropdownListDialogFragment.newInstance(
            resources.getStringArray(R.array.units).toList(),
            listener
        ).show(requireActivity().supportFragmentManager, DropdownListDialogFragment.TAG)
    }

    fun saveItem() {
        val description =
            if (et_description.text.toString().trim().isNotEmpty()) et_description.text.toString()
                .trim() else resources.getString(R.string.text_placeholder_item)
        val quantity = if (et_quantity.text.toString().isNotEmpty()) et_quantity.text.toString()
            .toDouble() else 1.0
        val unit = if (et_unit.text.toString().isNotEmpty())
            et_unit.text.toString() else resources.getStringArray(R.array.units)[0]
        val price =
            if (et_price.text.toString().isNotEmpty()) et_price.text.toString().toDouble() else 0.0
        val notes = et_notes.text.toString().trim()

        val item = Item(
            id = item.id,
            description = description,
            quantity = quantity,
            unit = unit,
            price = price,
            notes = notes,
            purchased = item.purchased,
            categoryId = item.categoryId,
            listId = item.listId
        )

        if (presenter.saveItem(item) && activity is OnItemSaveListener) {
            val listener = activity as OnItemSaveListener
            listener.onSave()
        }
    }

    override fun showItem(item: Item) {
        et_description.setText(item.description)
        et_quantity.setText(item.quantity.toString())
        et_unit.setText(item.unit)
        et_price.setText(item.price.toString())
        et_notes.setText(item.notes.toString())
        iv_icon.setImageResource(requireContext().resourceId(item.category?.resourceIconName ?: "ic_item_default"))
    }

    override fun showDefaultValues() {
        et_description.setText(resources.getString(R.string.text_placeholder_item))
        et_quantity.setText(1.toString())
        et_price.setText(0.00.toString())
        et_unit.setText(resources.getStringArray(R.array.units)[0])
    }

    override fun errorSaveItem() {
        Toast.makeText(requireContext(), R.string.msg_error_save, Toast.LENGTH_SHORT).show()
    }

    interface OnItemSaveListener {
        fun onSave()
    }
}