package com.allysonjeronimo.toshop.view.shoppinglists

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.legacy.entities.ShoppingList
import com.allysonjeronimo.toshop.utils.toCurrency
import kotlinx.android.synthetic.main.item_shopping_list.view.*
import kotlinx.android.synthetic.main.item_shopping_list.view.tv_description

class ShoppingListAdapter(
    var shoppingLists:MutableList<ShoppingList>? = null,
    private val selectListener: ShoppingListsFragment.OnShoppingListClickListener) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>(){

    fun setList(list:MutableList<ShoppingList>){
        this.shoppingLists = list
        notifyDataSetChanged()
    }

    fun deleteShoppingList(shoppingList: ShoppingList){
        val index = this.shoppingLists?.indexOfFirst { it.id == shoppingList.id }
        if(index != null){
            this.shoppingLists?.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shopping_list, parent, false)
        val viewHolder = ViewHolder(itemView)
        viewHolder.itemView.setOnClickListener{
            val shoppingList = shoppingLists?.get(viewHolder.adapterPosition)
            if (shoppingList != null) {
                selectListener.onClick(shoppingList)
            }
        }
        viewHolder.itemView.setOnLongClickListener {
            val shoppingList = shoppingLists?.get(viewHolder.adapterPosition)
            if (shoppingList != null) {
                selectListener.onLongClick(shoppingList)
            }
            true
        }
        return viewHolder
    }

    override fun getItemCount(): Int = shoppingLists?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var shoppingList = shoppingLists?.get(position)
        if(shoppingList != null){
            holder.tvDescription.text = shoppingList.description
            holder.tvCheckCount.text = "${shoppingList.quantityPurchasedItems}/${shoppingList.quantityItems}"
            holder.tvTotal.text = shoppingList.total.toCurrency()

            holder.pbStatus.max = shoppingList.quantityItems
            holder.pbStatus.progress = shoppingList.quantityPurchasedItems

            if(shoppingList.quantityItems > 0 && shoppingList.quantityItems == shoppingList.quantityPurchasedItems){
                holder.ivIcon.setImageResource(R.drawable.ic_shopping_list_green)
                DrawableCompat.setTint(holder.pbStatus.progressDrawable, ContextCompat.getColor(holder.itemView.context, R.color.lightGreen))
                holder.ivTotal.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.lightGreen), PorterDuff.Mode.SRC_IN)
                holder.ivCheckCount.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.lightGreen), PorterDuff.Mode.SRC_IN)
            }
            else if(shoppingList.quantityItems > 0 && shoppingList.quantityItems != shoppingList.quantityPurchasedItems){
                holder.ivIcon.setImageResource(R.drawable.ic_shopping_list_yellow)
                DrawableCompat.setTint(holder.pbStatus.progressDrawable, ContextCompat.getColor(holder.itemView.context, R.color.lightOrange))
                holder.ivTotal.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.lightOrange), PorterDuff.Mode.SRC_IN)
                holder.ivCheckCount.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.lightOrange), PorterDuff.Mode.SRC_IN)
            }
            else{
                holder.ivIcon.setImageResource(R.drawable.ic_shopping_list_gray)
                DrawableCompat.setTint(holder.pbStatus.progressDrawable, ContextCompat.getColor(holder.itemView.context, R.color.lightGray))
                holder.ivTotal.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.lightGray), PorterDuff.Mode.SRC_IN)
                holder.ivCheckCount.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.lightGray), PorterDuff.Mode.SRC_IN)
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var ivIcon: ImageView = itemView.iv_icon
        var ivTotal: ImageView = itemView.iv_total
        var ivCheckCount: ImageView = itemView.iv_check_count
        var tvDescription: TextView = itemView.tv_description
        var tvCheckCount: TextView = itemView.tv_check_count
        var tvTotal: TextView = itemView.tv_total
        var pbStatus: ProgressBar = itemView.pb_status
    }
}