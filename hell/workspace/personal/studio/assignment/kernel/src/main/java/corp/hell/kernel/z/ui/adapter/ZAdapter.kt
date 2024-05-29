package corp.hell.kernel.z.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import corp.hell.kernel.databinding.ZBinding
import corp.hell.kernel.network.Item
import corp.hell.kernel.parent.BaseViewHolder

/**
 * Copyright © 2022 Hell Corporation. All rights reserved.
 *
 * @author Harsh Gupta aka Lucifer 😈
 * @since February 26, 2022
 */
class ZAdapter : ListAdapter<Item, BaseViewHolder<Item>>(DiffUtilCallback) {

    private var items = listOf<Item>()
    private var onItemClickListener = fun(item: Item, position: Int, flag: String?): Unit = Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Item> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ZViewHolder(ZBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Item>, position: Int) {
        val data = items[position]
        holder.bindTo(position, data)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * Update data and adapter
     */
    fun submitData(updatedItems: List<Item>) {
        items = updatedItems
        notifyDataSetChanged()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Diff Util
    ///////////////////////////////////////////////////////////////////////////
    object DiffUtilCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }

    inner class ZViewHolder(val binding: ZBinding) : BaseViewHolder<Item>(binding.root) {
        override fun bindTo(position: Int, data: Item) {
            binding.apply {
            }
        }

    }
}