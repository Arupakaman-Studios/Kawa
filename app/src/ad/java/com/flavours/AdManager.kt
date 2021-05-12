package com.flavours

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arupakaman.kawa.R
import com.arupakaman.kawa.databinding.ItemNativeAdBinding
import com.arupakaman.kawa.databinding.ItemNativeAdForDetailBinding
import com.arupakaman.kawa.ui.koans.list.ad.VIEW_TYPE_NATIVE_AD
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions

object AdManager {

    const val BANNER_AD_KOAN_DETAIL = 1
    const val BANNER_AD_KOAN_IMAGE = 2
    const val BANNER_AD_KOAN_LISTING = 3
    const val BANNER_AD_KOAN_SEARCH = 4

    var pairBannerAdKoanDetail:Pair<AdView,ViewGroup>?=null
    var pairBannerAdKoanImage:Pair<AdView,ViewGroup>?=null
    var pairBannerAdKoanListing:Pair<AdView,ViewGroup>?=null
    var pairBannerAdKoanSearch:Pair<AdView,ViewGroup>?=null

    fun initialize(applicationContext: Context) {
        MobileAds.initialize(applicationContext)

        val testDeviceIds = listOf("D4082B2EDF14B1A5864B0F7F3F92A21A")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)
    }

    private fun Context.getBannerAdId(bannerType:Int):String{
        return when(bannerType){
            BANNER_AD_KOAN_DETAIL-> getString(R.string.ad_banner_koan_detail)
            BANNER_AD_KOAN_IMAGE-> getString(R.string.ad_banner_koan_image)
            BANNER_AD_KOAN_LISTING-> getString(R.string.ad_banner_koan_listing)
            else-> getString(R.string.ad_banner_koan_search)
        }
    }

    fun showAd(container: View, bannerType: Int) {
        if (container is ViewGroup) {
            val adView = AdView(container.context)
            adView.adSize = AdSize.BANNER
            adView.adUnitId = container.context.getBannerAdId(bannerType)//"ca-app-pub-3940256099942544/6300978111"
            container.addView(adView)
            val adRequest = AdRequest.Builder().build()

            adView.loadAd(adRequest)
            setAdVariable(adView, container, bannerType)
        }
    }

    private fun setAdVariable(adView: AdView, container: ViewGroup, bannerType: Int){
        when(bannerType){
            BANNER_AD_KOAN_DETAIL-> {
                pairBannerAdKoanDetail?.destroyAd()
                pairBannerAdKoanDetail=adView to container
            }
            BANNER_AD_KOAN_IMAGE-> {
                pairBannerAdKoanImage?.destroyAd()
                pairBannerAdKoanImage=adView to container
            }
            BANNER_AD_KOAN_LISTING-> {
                pairBannerAdKoanListing?.destroyAd()
                pairBannerAdKoanListing=adView to container
            }
            else-> {
                pairBannerAdKoanSearch?.destroyAd()
                pairBannerAdKoanSearch=adView to container
            }
        }
    }

    fun showNativeAd(context: Context, onNativeAdFound:(List<Any>?)->Unit){

        val listOfNativeAd= mutableListOf<Any>()
        var adLoader: AdLoader?=null
        adLoader = AdLoader.Builder(context, context.getString(R.string.ad_native))
                .forNativeAd{ad : NativeAd ->
                    // Show the ad.
                    Log.d("showNativeAd, body",ad.body?:"")
                    Log.d("showNativeAd, headline",ad.headline?:"")
                    Log.d("showNativeAd, price",ad.price?:"")
                    Log.d("showNativeAd, advtiser",ad.advertiser?:"")
                    Log.d("showNativeAd, store",ad.store?:"")
                    Log.d("showNativeAd, icon",ad.icon?.toString()?:"")
                    Log.d("showNativeAd, mdContent",ad.mediaContent?.toString()?:"")

                    if (adLoader?.isLoading == true){
                        Log.d("showNativeAd, added",ad.headline?:"")
                        listOfNativeAd.add(ad)
                    }
                    else {
                        Log.d("showNativeAd, passed",ad.headline?:"")
                        onNativeAdFound(listOfNativeAd)
                    }

                }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the failure by logging, altering the UI, and so on.
                    Log.d("ad","failed to load ad")
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                // Methods in the NativeAdOptions.Builder class can be
                // used here to specify individual options settings.
                .build())
            .build()

        /*val onNativeAdLoaded = NativeAd.OnNativeAdLoadedListener {ad : NativeAd ->


        }*/

        adLoader.loadAds(AdRequest.Builder().build(),5)
    }



    fun getNativeAdType(any:Any):Int?{
        if (any is NativeAd)
        {
            Log.d("getNativeAdType","returned VIEW_TYPE_NATIVE_AD")
            return VIEW_TYPE_NATIVE_AD
        }
        Log.d("getNativeAdType","returned null")
        return null
    }

    // ad for koan list

    fun getNativeAdViewHolder(parent:ViewGroup): AdViewHolder {
        return AdViewHolder.from(parent)
    }

    fun bindNativeAdData(viewHolder:RecyclerView.ViewHolder, any: Any){
        if (viewHolder is AdViewHolder && any is NativeAd){
            viewHolder.populateNativeAdView(any)
        }
    }

    class AdViewHolder(val binding: ItemNativeAdBinding) : RecyclerView.ViewHolder(binding.root)
    {
        companion object{
            fun from(parent:ViewGroup): AdViewHolder {
                return AdViewHolder(ItemNativeAdBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }

        fun populateNativeAdView(nativeAd: NativeAd){

            binding.nativeAdView.run {
                headlineView = binding.txtHeadLine
                bodyView = binding.txtAdBody
                iconView = binding.imgIcon
                callToActionView= binding.txtCallToAction
                priceView = binding.txtPrice
                advertiserView= binding.txtAdAdvertiser
                starRatingView = binding.ratingAdStars
                //storeView = binding.txtAdStore

                // The headline and media content are guaranteed to be in every UnifiedNativeAd.
                (headlineView as TextView).text = nativeAd.headline
                //adView.mediaView.setMediaContent(nativeAd.mediaContent)

                // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
                // check before trying to display them.
                if (nativeAd.body == null) {
                    bodyView?.visibility = View.INVISIBLE
                } else {
                    bodyView?.visibility = View.VISIBLE
                    (bodyView as TextView).text = nativeAd.body
                }

                if (nativeAd.callToAction == null) {
                    callToActionView?.visibility = View.INVISIBLE
                } else {
                    callToActionView?.visibility = View.VISIBLE
                    (callToActionView as TextView).text = nativeAd.callToAction
                }

                if (nativeAd.icon == null) {
                    iconView?.visibility = View.GONE
                } else {
                    (iconView as ImageView).setImageDrawable(
                        nativeAd.icon?.drawable
                    )
                    iconView?.visibility = View.VISIBLE
                }

                if (nativeAd.price == null) {
                    priceView?.visibility = View.INVISIBLE
                } else {
                    priceView?.visibility = View.VISIBLE
                    (priceView as TextView).text = nativeAd.price
                }

              /*  if (nativeAd.store == null) {
                    storeView?.visibility = View.INVISIBLE
                } else {
                    storeView?.visibility = View.VISIBLE
                    (storeView as TextView).text = nativeAd.store
                }*/

                if (nativeAd.starRating == null) {
                    starRatingView?.visibility = View.INVISIBLE
                } else {
                    (starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
                    starRatingView?.visibility = View.VISIBLE
                }

                if (nativeAd.advertiser == null) {
                    advertiserView?.visibility = View.GONE
                } else {
                    (advertiserView as TextView).text = nativeAd.advertiser
                    advertiserView?.visibility = View.VISIBLE
                }

                // This method tells the Google Mobile Ads SDK that you have finished populating your
                // native ad view with this native ad.
                setNativeAd(nativeAd)
            }
        }
    }


    // ad for detail view holder

    fun getNativeAdViewHolderForDetail(parent:ViewGroup): AdViewHolderForDetail {
        return AdViewHolderForDetail.from(parent)
    }

    fun bindNativeAdDataForDetail(viewHolder:RecyclerView.ViewHolder, any: Any){
        if (viewHolder is AdViewHolderForDetail && any is NativeAd){
            viewHolder.populateNativeAdView(any)
        }
    }

    class AdViewHolderForDetail(val binding:ItemNativeAdForDetailBinding) : RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent: ViewGroup): AdViewHolderForDetail {
                return AdViewHolderForDetail(ItemNativeAdForDetailBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }

        fun populateNativeAdView(nativeAd: NativeAd){

            binding.nativeAdView.run {
                mediaView = binding.imgNativeAd
                headlineView = binding.txtAdTitle
                bodyView = binding.txtAdDescription
                iconView = binding.imgAppIcon
                callToActionView= binding.btnCallToAction
                priceView = binding.txtAdPrice
                advertiserView= binding.txtAdAdvertiser
                starRatingView = binding.ratingAdStars
                storeView = binding.txtAdStore

                // The headline and media content are guaranteed to be in every UnifiedNativeAd.
                (headlineView as TextView).text = nativeAd.headline
                nativeAd.mediaContent?.let {mediaContent->
                    mediaView?.setMediaContent(mediaContent)
                }


                // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
                // check before trying to display them.
                if (nativeAd.body == null) {
                    bodyView?.visibility = View.INVISIBLE
                } else {
                    bodyView?.visibility = View.VISIBLE
                    (bodyView as TextView).text = nativeAd.body
                }

                if (nativeAd.callToAction == null) {
                    callToActionView?.visibility = View.INVISIBLE
                } else {
                    callToActionView?.visibility = View.VISIBLE
                    (callToActionView as TextView).text = nativeAd.callToAction
                }

                if (nativeAd.icon == null) {
                    iconView?.visibility = View.GONE
                } else {
                    (iconView as ImageView).setImageDrawable(
                        nativeAd.icon?.drawable
                    )
                    iconView?.visibility = View.VISIBLE
                }

                if (nativeAd.price == null) {
                    priceView?.visibility = View.INVISIBLE
                } else {
                    priceView?.visibility = View.VISIBLE
                    (priceView as TextView).text = nativeAd.price
                }

                  if (nativeAd.store == null) {
                      storeView?.visibility = View.INVISIBLE
                  } else {
                      storeView?.visibility = View.VISIBLE
                      (storeView as TextView).text = nativeAd.store
                  }

                if (nativeAd.starRating == null) {
                    starRatingView?.visibility = View.INVISIBLE
                } else {
                    (starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
                    starRatingView?.visibility = View.VISIBLE
                }

                if (nativeAd.advertiser == null) {
                    advertiserView?.visibility = View.GONE
                } else {
                    (advertiserView as TextView).text = nativeAd.advertiser
                    advertiserView?.visibility = View.VISIBLE
                }

                // This method tells the Google Mobile Ads SDK that you have finished populating your
                // native ad view with this native ad.
                setNativeAd(nativeAd)
            }
        }
    }

    fun destroyAd(bannerType:Int){
        when(bannerType){
            BANNER_AD_KOAN_DETAIL -> {
                pairBannerAdKoanDetail?.destroyAd()
                pairBannerAdKoanDetail=null
            }
            BANNER_AD_KOAN_IMAGE -> {
                pairBannerAdKoanImage?.destroyAd()
                pairBannerAdKoanImage=null
            }
            BANNER_AD_KOAN_LISTING -> {
                pairBannerAdKoanListing?.destroyAd()
                pairBannerAdKoanListing=null
            }
            else-> {
                pairBannerAdKoanSearch?.destroyAd()
                pairBannerAdKoanSearch=null
            }
        }
    }

    private fun Pair<AdView,ViewGroup>.destroyAd(){
        val (adView,container) = this
        adView.adListener = null
        container.removeAllViews()
        adView.destroy()
    }


}

fun Any.isAd() = this is NativeAd

fun List<Any>.destroyNativeAds(){
    forEach {
        if (it is NativeAd)
            it.destroy()
    }
}


