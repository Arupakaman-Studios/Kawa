package com.arupakaman.kawa.ui.koans.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arupakaman.kawa.databinding.ItemKoanListBinding
import com.arupakaman.kawa.model.HighlightedKoans
import com.arupakaman.kawa.ui.koans.list.KoanClickListener

class KoansListAdapter(val koanClickListener: KoanClickListener) : ListAdapter<HighlightedKoans, KoansListAdapter.ViewHolder>(DiffUtilCallback) {

    companion object DiffUtilCallback : DiffUtil.ItemCallback<HighlightedKoans>() {

        override fun areItemsTheSame(oldItem: HighlightedKoans, newItem: HighlightedKoans): Boolean {
            return  oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: HighlightedKoans, newItem: HighlightedKoans): Boolean {
            return oldItem.koan.id==newItem.koan.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent,koanClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemKoanListBinding,koanClickListener: KoanClickListener) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.koanClickListener=koanClickListener
        }

        fun bind(highlightedKoan: HighlightedKoans?){
            binding.highlightedKoan=highlightedKoan
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup,koanClickListener: KoanClickListener):ViewHolder{
                return ViewHolder(ItemKoanListBinding.inflate(LayoutInflater.from(parent.context),parent,false),koanClickListener)
            }
        }
    }
}