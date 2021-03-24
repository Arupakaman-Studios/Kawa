package com.arupakaman.kawa.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.arupakaman.kawa.R
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class KoanImage(@DrawableRes val imgResId:Int, val artName:String, val artistName:String):Parcelable
{
    companion object{
        private val listOfKoanImage = ArrayList<KoanImage>().apply {
            add(KoanImage(R.drawable.img_koan1,"A Calm Sea","Simon de Vlieger"))
            add(KoanImage(R.drawable.img_koan2,"Clouds above a sea calm","Ivan Aivazovsky"))
            add(KoanImage(R.drawable.img_koan3,"Cauld Blaws the Wind frae East to West","Joseph Farquharson"))
            add(KoanImage(R.drawable.img_koan4,"View of the Bosporus","Ivan Aivazovsky"))
            add(KoanImage(R.drawable.img_koan5,"Calm Early Evening Sea","Ivan Aivazovsky"))
            add(KoanImage(R.drawable.img_koan6,"Ruhige See","Ivan Aivazovsky"))
            add(KoanImage(R.drawable.img_koan7,"Lake Maggiore in the Evening","Ivan Aivazovsky"))
            add(KoanImage(R.drawable.img_koan8,"Cloud Study","Knud Baade"))
            add(KoanImage(R.drawable.img_koan9,"Irises","Vincent van Gogh"))
            add(KoanImage(R.drawable.img_koan10,"Girl with Chrysanthemums","Olga Boznańska"))
        }

        fun getRandomKoanImage() = listOfKoanImage[Random.nextInt(listOfKoanImage.size)]
    }
}

