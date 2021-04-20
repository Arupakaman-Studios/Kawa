package com.arupakaman.kawa.utils

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arupakaman.kawa.R
import com.arupakaman.kawa.data.database.entities.Koan
import com.arupakaman.kawa.model.HighlightedKoans
import com.arupakaman.kawa.ui.koans.list.KoansAdapter
import com.arupakaman.kawa.ui.koans.search.KoansListAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@BindingAdapter("cardData")
fun RecyclerView.bindRecyclerView(list: List<Koan>?){
    list?.let {
        Log.d("bindRecyclerView",list.size.toString())
        if (adapter is KoansAdapter)
            (adapter as KoansAdapter).submitList(list)

        applyAnimation(R.anim.layout_animation_slide_from_bottom)
    }
}


@BindingAdapter("listData")
fun RecyclerView.bindSearchResult(list: List<HighlightedKoans>?){
    list?.let {
        Log.d("bindRecyclerView",list.size.toString())
        if (adapter is KoansListAdapter)
            (adapter as KoansListAdapter).submitList(list)

        applyAnimation(R.anim.layout_animation_slide_from_bottom)
    }
}

@BindingAdapter("htmlString")
fun TextView.setHtmlString(string: String?){
    GlobalScope.launch(Dispatchers.Main) {
        if (string==null)
            return@launch

        text = withContext(Dispatchers.Default){ HtmlCompat.fromHtml(string,HtmlCompat.FROM_HTML_MODE_LEGACY) }
    }
}

@BindingAdapter("imageResource")
fun ImageView.imageResource(@DrawableRes imgResource:Int)
{
    Glide.with(this).load(imgResource).into(this)
}

/*
@BindingAdapter("koanNoResult")
fun LinearLayout.setKoanResult(noKoans: Boolean){
    Log.d("setKoanResult","noKoans: $noKoans")
    visibility = if (noKoans) View.VISIBLE else View.GONE
    Log.d("setKoanResult","isVisible: $isVisible")
}*/
