package com.allysonjeronimo.toshop.ui.items

import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.data.legacy.entities.Item
import com.allysonjeronimo.toshop.data.legacy.utils.resourceId
import kotlinx.android.synthetic.main.item_shopping_list_item.view.*

class ItemAdapter(
    var items:MutableList<Item>? = null,
    private val clickListener: ItemsFragment.OnItemClickListener,
    private val checkListener: ItemsFragment.OnItemCheckListener
    ) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private var uncheckedItems:MutableList<Item>? = null
    private var checkedItems:MutableList<Item>? = null
    var isMoveItemsToBottom = false
    private var recyclerView:RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun setList(list:MutableList<Item>){
        if(isMoveItemsToBottom){
            fillStatusLists(list)
            fillMainListFromStatusLists()
        }
        else{
            this.items = list.sortedBy { it.description }.toMutableList()
        }
        notifyDataSetChanged()
    }

    private fun fillMainListFromStatusLists(){
        this.items = mutableListOf<Item>()
        this.items?.addAll(uncheckedItems!!)
        this.items?.addAll(checkedItems!!)
    }

    private fun fillStatusLists(list:MutableList<Item>){
        this.uncheckedItems = list
            .filter { !it.purchased }
            .sortedBy { it.description }
            .toMutableList()
        this.checkedItems = list
            .filter{it.purchased}
            .sortedBy { it.description }
            .toMutableList()
    }

    private fun clearStatusLists(){
        this.uncheckedItems?.clear()
        this.checkedItems?.clear()
    }

    fun deleteItems(){
        if(itemCount > 0){
            if(isMoveItemsToBottom){
                clearStatusLists()
                fillMainListFromStatusLists()
            }
            else{
                this.items?.clear()
            }
        }
    }

    fun checkItems(){
        if(itemCount > 0){
            this.items?.forEach{ item ->
                if(!item.purchased){
                    item.purchased = true

                    if(isMoveItemsToBottom){
                        this.uncheckedItems?.remove(item)
                        this.checkedItems?.add(item)
                        this.checkedItems?.sortBy { it.description }
                    }
                }
            }

            if(isMoveItemsToBottom)
                fillMainListFromStatusLists()

            notifyItemRangeChanged(0, itemCount)
        }
    }

    fun uncheckItems(){
        if(itemCount > 0){
            this.items?.forEach { item ->
                if(item.purchased){
                    item.purchased = false

                    if(isMoveItemsToBottom){
                        this.checkedItems?.remove(item)
                        this.uncheckedItems?.add(item)
                        this.uncheckedItems?.sortBy { it.description }
                    }

                }
            }

            if(isMoveItemsToBottom)
                fillMainListFromStatusLists()

            notifyItemRangeChanged(0, itemCount)
        }
    }

    private fun removeItemFromStatusLists(item: Item){
        if(item.purchased){
            this.checkedItems?.remove(item)
        }
        else{
            this.uncheckedItems?.remove(item)
        }
        fillMainListFromStatusLists()
    }

    fun deleteItem(item: Item){
        val index = this.items?.indexOfFirst { it.id == item.id }
        if(index != null){
            if(isMoveItemsToBottom){
                removeItemFromStatusLists(item)
            }
            else{
                this.items?.remove(item)
            }
            notifyItemRemoved(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping_list_item, parent, false)

        val viewHolder = ViewHolder(itemView)

        viewHolder.itemView.setOnClickListener {
            val item = items?.get(viewHolder.adapterPosition)
            if (item != null) {
                clickListener.onClick(item)
            }
        }

        viewHolder.cbPurchased.setOnClickListener{
            val isChecked = (it as CheckBox).isChecked
            updateItemView(viewHolder, isChecked)
            var item = items?.get(viewHolder.adapterPosition)
            if(item != null){
                if(isMoveItemsToBottom){
                    movePosition(item, viewHolder.adapterPosition, isChecked)
                }

                checkListener.onCheck(item)
            }
        }

        return viewHolder
    }

    private fun movePosition(item: Item, currentPosition:Int, isChecked:Boolean){
        if(isChecked)
            moveToBottom(item, currentPosition)
        else
            moveToTop(item, currentPosition)
    }

    private fun moveToTop(item: Item, currentPosition: Int){
        this.checkedItems?.remove(item)
        this.uncheckedItems?.add(item)
        this.uncheckedItems?.sortBy { it.description }
        val toIndex = this.uncheckedItems?.indexOf(item)!!
        fillMainListFromStatusLists()
        notifyItemMoved(currentPosition, toIndex)
        this.recyclerView?.scrollToPosition(currentPosition)
    }

    private fun moveToBottom(item: Item, currentPosition:Int){
        this.uncheckedItems?.remove(item)
        this.checkedItems?.add(item)
        this.checkedItems?.sortBy { it.description }
        val uncheckedSize = this.uncheckedItems?.size ?: 0
        val toIndex = uncheckedSize + this.checkedItems?.indexOf(item)!!
        fillMainListFromStatusLists()
        notifyItemMoved(currentPosition, toIndex)
        this.recyclerView?.scrollToPosition(currentPosition)
    }

    public fun countCheckedItems() : Int{
        return if(isMoveItemsToBottom)
            this.checkedItems?.size ?: 0
        else
            this.items?.count{it.purchased} ?: 0
    }

    public fun totalCheckedItems() : Double{
        return if(isMoveItemsToBottom)
            this.checkedItems?.map{it.quantity * it.price}?.sum() ?: 0.0
        else
            this.items?.filter { it.purchased }?.map { it.quantity * it.price }?.sum() ?: 0.0
    }

    private fun updateItemView(viewHolder: ViewHolder, purchased:Boolean){
        val context = viewHolder.itemView.context
        if(purchased){
            viewHolder.vwColorState.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGreen))
            viewHolder.tvDescription.paintFlags = viewHolder.tvDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            viewHolder.tvDescription.setTypeface(viewHolder.tvDescription.typeface, Typeface.BOLD)
        }
        else{
            viewHolder.vwColorState.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGray))
            viewHolder.tvDescription.paintFlags = viewHolder.tvDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            viewHolder.tvDescription.setTypeface(null, Typeface.NORMAL)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        if(item != null){
            holder.tvDescription.text = item.description
            holder.cbPurchased.isChecked = item.purchased
            holder.tvDetails.text = item.details

            holder.ivIcon.setImageResource(
                if(item.category != null) {
                    holder.itemView.context.resourceId(item.category!!.resourceIconName)
                }
                else{
                    R.drawable.ic_item_default
                }
            )

            updateItemView(holder, item.purchased)
        }
    }

    override fun getItemCount(): Int = items?.size ?: 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvDescription: TextView = itemView.tv_description
        var vwColorState: View = itemView.view_color_state
        var cbPurchased: CheckBox = itemView.cb_purchased
        var tvDetails: TextView = itemView.tv_details
        var ivIcon: ImageView = itemView.iv_icon
    }
}