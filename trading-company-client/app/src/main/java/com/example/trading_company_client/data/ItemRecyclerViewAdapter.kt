package com.example.trading_company_client.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trading_company_client.R
import com.example.trading_company_client.data.model.Item
import com.example.trading_company_client.databinding.ItemItemBinding

class ItemRecyclerViewAdapter : ListAdapter<Item, ItemRecyclerViewAdapter.TableHolder>(DiffCallback()) {

    inner class TableHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val binding = ItemItemBinding.bind(item)

        fun bind(item: Item) {
            with(binding) {
                idTextView.text = item.id.toString()
                itemNameTextView.text = item.name
                quantityTextView.text = item.quantity.toString()
                addedByTextView.text = item.addedBy.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false)
        return TableHolder(view)
    }

    override fun onBindViewHolder(holder: TableHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}