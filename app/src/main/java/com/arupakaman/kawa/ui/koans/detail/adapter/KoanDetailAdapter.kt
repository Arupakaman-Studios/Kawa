package com.arupakaman.kawa.ui.koans.detail.adapter

import android.graphics.Typeface
import android.opengl.ETC1.getHeight
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.arupakaman.kawa.data.database.entities.Koan
import com.arupakaman.kawa.data.pref.MyAppPref
import com.arupakaman.kawa.databinding.ItemKoanDetailBinding
import com.arupakaman.kawa.ui.koans.list.ad.VIEW_TYPE_KOAN
import com.arupakaman.kawa.ui.koans.list.ad.VIEW_TYPE_NATIVE_AD
import com.flavours.AdManager


class KoanDetailAdapter(private val listOfKoan:List<Any>, private val bottomReachListener:BottomReachListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var textSizeInDimen = MyAppPref.koanTextSize.toFloat()
    var typeface : Typeface?=null

    class KoanDetailViewHolder(val binding: ItemKoanDetailBinding, bottomReachListener:BottomReachListener?) : RecyclerView.ViewHolder(binding.root) {

        init {
            if (bottomReachListener!=null)
            {
                binding.nestedKoanDetail.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, _, _, _ ->
                    val view: View = v.getChildAt(v.childCount - 1) as View
                    val diff: Int = view.bottom - (v.height + v.scrollY)
                    if (diff == 0) {
                        bottomReachListener.onBottomReach.invoke()
                    }
                })
            }
        }

        fun bind(koan:Koan, textSizeInDimen:Float, typeface: Typeface?){
            binding.koan=koan

            binding.txtKoan.textSize = textSizeInDimen
            if (typeface!=null)
                binding.txtKoan.typeface = typeface

            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup, bottomReachListener:BottomReachListener?): KoanDetailViewHolder {
                return KoanDetailViewHolder(ItemKoanDetailBinding.inflate(LayoutInflater.from(parent.context),parent,false),bottomReachListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return AdManager.getNativeAdType(listOfKoan[position])?: VIEW_TYPE_KOAN
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType== VIEW_TYPE_NATIVE_AD)
            AdManager.getNativeAdViewHolderForDetail(parent)
        else
            KoanDetailViewHolder.from(parent,bottomReachListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("detailAdapter",position.toString())
        if (getItemViewType(position)== VIEW_TYPE_NATIVE_AD)
        {
            AdManager.bindNativeAdDataForDetail(holder, listOfKoan[position])
        }
        else{
            (holder as KoanDetailViewHolder).bind(listOfKoan[position] as Koan, textSizeInDimen, typeface)
        }
    }

    override fun getItemCount(): Int {
        return listOfKoan.size
    }
}

class BottomReachListener(val onBottomReach:()->Unit)