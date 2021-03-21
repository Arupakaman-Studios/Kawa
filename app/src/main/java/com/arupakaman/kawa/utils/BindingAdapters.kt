package com.arupakaman.kawa.utils

import android.util.Log
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arupakaman.kawa.database.entities.Koan
import com.arupakaman.kawa.model.HighlightedKoans
import com.arupakaman.kawa.ui.koans.list.KoansAdapter
import com.arupakaman.kawa.ui.koans.search.KoansListAdapter


@BindingAdapter("cardData")
fun RecyclerView.bindRecyclerView(list: List<Koan>?){
    list?.let {
        Log.d("bindRecyclerView",list.size.toString())
        (adapter as KoansAdapter).submitList(list)
    }
}

@BindingAdapter("listData")
fun RecyclerView.bindSearchResult(list: List<HighlightedKoans>?){
    list?.let {
        Log.d("bindRecyclerView",list.size.toString())
        (adapter as KoansListAdapter).submitList(list)
    }
}

@BindingAdapter("htmlString")
fun TextView.setHtmlString(string: String){
    text = HtmlCompat.fromHtml(string,HtmlCompat.FROM_HTML_MODE_LEGACY)
}