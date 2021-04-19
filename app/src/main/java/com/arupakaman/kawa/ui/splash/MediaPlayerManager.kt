package com.arupakaman.kawa.ui.splash

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.arupakaman.kawa.R


class MediaPlayerManager(context: Context, lifecycleOwner: LifecycleOwner):LifecycleObserver{

    companion object{
        fun init(context: Context,lifecycleOwner: LifecycleOwner): MediaPlayerManager {
            return MediaPlayerManager(context, lifecycleOwner)
        }
    }

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    val mp: MediaPlayer by lazy { MediaPlayer.create(context, R.raw.river) }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun playMusic(){
        mp.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopMusic(){
        mp.pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        mp.stop()
        mp.release()
    }
}