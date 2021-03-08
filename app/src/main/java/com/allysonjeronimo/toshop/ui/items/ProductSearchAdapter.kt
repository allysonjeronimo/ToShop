package com.allysonjeronimo.toshop.ui.items

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.data.legacy.entities.Product
import com.allysonjeronimo.toshop.data.legacy.utils.color
import kotlinx.android.synthetic.main.item_product.view.*

class ProductSearchAdapter(
    context: Context,
    private val layout:Int,
    private val searchListener: (String) -> List<Product>
) : ArrayAdapter<Product>(context, layout), Filterable
{

    private var results = mutableListOf<Product>()
    private val productFilter:Filter = ProductFilter()

    override fun getCount(): Int = results.size

    override fun getItem(position: Int): Product? {
        return if (results.isNotEmpty() && position < results.size) {
            results[position]
        } else {
            null
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view:View
        if(convertView == null){
            view = LayoutInflater.from(parent.context)
                .inflate(layout, parent, false)
        }
        else{
            view = convertView
        }

        val product = getItem(position)
        val tvName = view.tv_name as TextView
        val ivIcon:ImageView = view.iv_icon as ImageView
        tvName.text = product.toString()

        if (product != null && product.isNew) {
           // ivIcon.setImageResource(R.drawable.ic_add)
            ivIcon.setColorFilter(ContextCompat.getColor(parent.context, R.color.lightGreen), android.graphics.PorterDuff.Mode.SRC_IN)
            //tvName.setTextColor(parent.context.resources.getColor(R.color.darkGray))
            tvName.setTextColor(parent.context.color(R.color.darkGray))
        }
        else{
            //ivIcon.setImageResource(context.resourceId(product!!.category!!.resourceIconName))
            ivIcon.setColorFilter(ContextCompat.getColor(parent.context, R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN)
            //tvName.setTextColor(parent.context.resources.getColor(R.color.gray))
            tvName.setTextColor(parent.context.color(R.color.gray))
        }

        return view
    }

    override fun getFilter(): Filter = productFilter

    private inner class ProductFilter : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = Filter.FilterResults()
            var temp = mutableListOf<Product>()
            if(constraint != null){
                val term = constraint.toString().trim()
                temp = searchListener(term).toMutableList()
                val index = temp.indexOfFirst { it.name?.toLowerCase().equals(term.toLowerCase()) }
                if(index < 0){
                    val newProduct = Product(name=term, categoryId = 0L)
                    newProduct.isNew = true
                    temp.add(0, newProduct)
                }

            }
            filterResults.values = temp
            filterResults.count = temp.size
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            if (filterResults != null) {
                @Suppress("UNCHECKED_CAST")
                results = filterResults.values as MutableList<Product>
                if(results.isNotEmpty())
                    notifyDataSetChanged()
                else
                    notifyDataSetInvalidated()
            }
        }
    }

}