package com.arupakaman.kawa.ui.koans.list.ad

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arupakaman.kawa.data.database.entities.Koan
import com.arupakaman.kawa.databinding.ItemKoanBinding
import com.arupakaman.kawa.ui.koans.list.KoanClickListener
import com.flavours.AdManager

const val VIEW_TYPE_NATIVE_AD=2
const val VIEW_TYPE_KOAN=1

class KoansAdapterWithAd(private val koanClickListener:KoanClickListener) : ListAdapter<Any, RecyclerView.ViewHolder>(DiffUtilCallback) {

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return  oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is Koan && newItem is Koan)
                oldItem.id==newItem.id
            else
                false
        }
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("getItemViewType: ", "position: $position, list size: $itemCount")

        return AdManager.getNativeAdType(getItem(position))?:VIEW_TYPE_KOAN
    }

    class KoanViewHolder private constructor(private val binding: ItemKoanBinding, koanClickListener:KoanClickListener) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.koanClickListener=koanClickListener
        }

        fun bind(koan: Koan?){
            binding.koan=koan
            binding.position=bindingAdapterPosition
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup,koanClickListener: KoanClickListener):KoanViewHolder{
                return KoanViewHolder(ItemKoanBinding.inflate(LayoutInflater.from(parent.context),parent,false),koanClickListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType==VIEW_TYPE_NATIVE_AD)
            AdManager.getNativeAdViewHolder(parent)
        else
            KoanViewHolder.from(parent,koanClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position)== VIEW_TYPE_NATIVE_AD)
        {
            Log.d("onBindViewHolder: ", "position: $position, list size: $itemCount")
            AdManager.bindNativeAdData(holder,getItem(position))
        }
        else{
            Log.d("onBindViewHolder: ", "position: $position, list size: $itemCount")
            (holder as KoanViewHolder).bind(getItem(position) as Koan)
        }
    }
}
/*

class KoanClickListener(val clickListener:(view: View, koan:Koan)->Unit){
    fun onClick(view: View, koan: Koan) = clickListener(view, koan)
}*/
