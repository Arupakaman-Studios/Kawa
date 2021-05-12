package com.flavours

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

object AdManager {

    const val BANNER_AD_KOAN_DETAIL = 1
    const val BANNER_AD_KOAN_IMAGE = 2
    const val BANNER_AD_KOAN_LISTING = 3
    const val BANNER_AD_KOAN_SEARCH = 4

    fun initialize(applicationContext: Context) {

    }

    private fun Context.getBannerAdId(bannerType:Int):String{
       return ""
    }

    fun showAd(container: View, bannerType: Int) {

    }

    fun showNativeAd(context: Context, onNativeAdFound:(List<Any>?)->Unit){

    }



    fun getNativeAdType(any:Any):Int?{
        return null
    }

    // ad for koan list

    fun getNativeAdViewHolder(parent:ViewGroup): AdViewHolder {
        return AdViewHolder.from(parent)
    }

    fun bindNativeAdData(viewHolder:RecyclerView.ViewHolder, any: Any){

    }

    class AdViewHolder(val view:View) : RecyclerView.ViewHolder(view)
    {
        companion object{
            fun from(parent:ViewGroup): AdViewHolder {
                return AdViewHolder(parent)
            }
        }
    }


    // ad for detail view holder

    fun getNativeAdViewHolderForDetail(parent:ViewGroup): AdViewHolderForDetail {
        return AdViewHolderForDetail.from(parent)
    }

    fun bindNativeAdDataForDetail(viewHolder:RecyclerView.ViewHolder, any: Any){

    }

    class AdViewHolderForDetail(view:View) : RecyclerView.ViewHolder(view){
        companion object{
            fun from(parent: ViewGroup): AdViewHolderForDetail {
                return AdViewHolderForDetail(parent)
            }
        }
    }

    fun destroyAd(bannerType:Int){
    }


}

fun Any.isAd() = false

fun List<Any>.destroyNativeAds(){

}


