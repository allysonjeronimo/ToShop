package com.allysonjeronimo.toshop.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.data.db.AppDatabase
import com.allysonjeronimo.toshop.data.repository.CategoryRepositoryImpl
import com.allysonjeronimo.toshop.legacy.entities.Category
import kotlinx.android.synthetic.main.fragment_categories_dialog.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CategoriesDialogFragment : DialogFragment() {

    private lateinit var listener: OnCategoryClickListener
    private lateinit var adapter: CategoryAdapter

    private val presenter: CategoriesDialogContract.CategoriesDialogPresenter by inject{
        parametersOf(this)
    }

    private lateinit var viewModel:CategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.dialog!!.window?.setBackgroundDrawableResource(R.drawable.shape_corners_white)
        return inflater.inflate(R.layout.fragment_categories_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel()
        observeEvents()
        createListeners()
    }

    private fun createViewModel() {
        val database = AppDatabase.getInstance(requireContext())
        val repository = CategoryRepositoryImpl(database.categoryDao())
        viewModel = ViewModelProvider(
            this,
            CategoriesViewModel.CategoriesViewModelFactory(repository)
        ).get(CategoriesViewModel::class.java)

    }

    private fun observeEvents() {
        TODO("Not yet implemented")
    }

    private fun createListeners() {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //fab_close.setOnClickListener {
        //    dismiss()
        //}

        //initRecyclerView()

        //presenter.loadCategories()
    }

    fun showCategories(categories: List<Category>) {
        adapter.setList(categories.toMutableList())
    }

    private fun initRecyclerView(){
        val selectedCategory = arguments?.getLong(EXTRA_SELECTED_CATEGORY, 0L) ?: 0L
        rv_categories.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter = CategoryAdapter(selectedCategoryId = selectedCategory, listener = this::onClick)
        rv_categories.adapter = adapter
    }

    private fun onClick(category: Category){
        listener.onClick(category)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, height)
    }

    interface OnCategoryClickListener{
        fun onClick(category: Category)
    }

    companion object{
        val TAG = CategoriesDialogFragment::class.simpleName
        private const val EXTRA_SELECTED_CATEGORY = "selected_category"

        fun newInstance(categoryId:Long, listener: OnCategoryClickListener) : CategoriesDialogFragment {
            val instance = CategoriesDialogFragment()
            instance.arguments = Bundle().apply {
                putLong(EXTRA_SELECTED_CATEGORY, categoryId)
            }
            instance.listener = listener
            return instance
        }
    }
}