package com.arupakaman.kawa.ui.koans.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arupakaman.kawa.data.database.entities.Koan
import com.arupakaman.kawa.databinding.ItemKoanBinding

class KoansAdapter(private val koanClickListener:KoanClickListener) : ListAdapter<Koan, KoansAdapter.ViewHolder>(DiffUtilCallback) {

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Koan>() {
        override fun areItemsTheSame(oldItem: Koan, newItem: Koan): Boolean {
            return  oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: Koan, newItem: Koan): Boolean {
            return oldItem.id==newItem.id
        }
    }

    class ViewHolder private constructor(private val binding: ItemKoanBinding, koanClickListener:KoanClickListener) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.koanClickListener=koanClickListener
        }

        fun bind(koan: Koan?){
            binding.koan=koan
            binding.position=bindingAdapterPosition
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup,koanClickListener:KoanClickListener):ViewHolder{
                return ViewHolder(ItemKoanBinding.inflate(LayoutInflater.from(parent.context),parent,false),koanClickListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent,koanClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class KoanClickListener(val clickListener:(view: View, position:Int, koan:Koan)->Unit){
    fun onClick(view: View, position:Int, koan: Koan) = clickListener(view, position, koan)
}