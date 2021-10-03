package com.arupakaman.kawa.ui.koans.list

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.arupakaman.kawa.data.database.KoansDatabase
import com.arupakaman.kawa.data.database.dao.KoanDao
import com.flavours.AdManager
import com.flavours.destroyNativeAds
import com.flavours.isAd
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KoansListingViewModel @Inject constructor(val koanDao : KoanDao) : ViewModel() {

    /*@Inject
    lateinit var koanDao : KoanDao*/ //= KoansDatabase.getKoanDao(application)

    // first: koan list, second: withAd?, if second is true means ad is added to the list
    private val _liveKoansWithAd = MediatorLiveData<List<Any>>()
    val liveKoansWithAd : LiveData<List<Any>>
        get() = _liveKoansWithAd

    private val liveNewAd = MutableLiveData<List<Any>>()

    init {
        _liveKoansWithAd.addSource(koanDao.getAllKoans()){
            _liveKoansWithAd.value= it
        }

        _liveKoansWithAd.addSource(liveNewAd){adList->
            if (adList.isEmpty())
                return@addSource
            viewModelScope.launch (Dispatchers.Default){
                val originalList = _liveKoansWithAd.value?.let { ArrayList(it) }?: ArrayList()

                val listToRemove = originalList.filter{it.isAd()}
                if (listToRemove.isNotEmpty())
                    originalList.removeAll(listToRemove)

                val bundleSize = originalList.size/adList.size
                var slot = bundleSize
                adList.forEach {adObj->
                    //val randomIndex = slot + Random.nextInt(10)
                    originalList.add(slot,adObj)
                    Log.d("showNativeAd","index: $slot added")
                    slot+=bundleSize

                }

                _liveKoansWithAd.postValue(originalList)
            }
        }
    }

    fun addAdInTheList(newAd:List<Any>){
        destroyAds()
        liveNewAd.value=newAd
    }

    private fun destroyAds(){
        liveNewAd.value?.destroyNativeAds()
    }

    override fun onCleared() {
        super.onCleared()
        destroyAds()
    }
}