package com.arupakaman.kawa.ui.koans.search

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.arupakaman.kawa.data.database.dao.KoanDao
import com.arupakaman.kawa.data.database.entities.Koan
import com.arupakaman.kawa.model.HighlightedKoans
import com.arupakaman.kawa.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class SearchKoansViewModel @Inject constructor(@ApplicationContext val application: Context, val koanDao:KoanDao) : ViewModel() {

    //private val koanDao = KoansDatabase.getKoanDao(application)

    // first: search query
    // second: adapter type
    private val liveSearchQuery = MutableLiveData<String>()

    /*val liveKoans:LiveData<List<Koan>> = Transformations.switchMap(liveSearchQuery){ (searchQuery,adapterType)->
        //koanDao.searchKoans(searchQuery)
            koanDao.searchKoansByFts(searchQuery.sanitizeSearchQuery())

    }*/

    val liveKoansCard = MediatorLiveData<List<Koan>>()

    val liveKoansHighlightedList = MediatorLiveData<List<HighlightedKoans>>()

    private val _liveKoanNoResult = MutableLiveData<Boolean>()
    val liveKoanNoResult : LiveData<Boolean>
        get() = _liveKoanNoResult

    private val _liveAdapterType = MutableLiveData<Event<Int>>()
    val liveAdapterType : LiveData<Event<Int>>
        get() = _liveAdapterType

    fun getAdapterType() = _liveAdapterType.value?.peekContent()?: ADAPTER_TYPE_LIST

    fun seAdapterTypeCardIfItWasAlreadySet(){
        if (getAdapterType()== ADAPTER_TYPE_CARD){
            _liveAdapterType.value = Event(ADAPTER_TYPE_CARD)
        }
    }

    init {
        _liveAdapterType.value = Event(ADAPTER_TYPE_LIST)

        liveKoansCard.addSource(liveSearchQuery) { searchQuery ->
            if (getAdapterType() == ADAPTER_TYPE_CARD) {

                val koans = koanDao.searchKoansByTitle(searchQuery)

                liveKoansCard.addSource(koans){
                    _liveKoanNoResult.value= searchQuery.isNotEmpty() && it.isEmpty()
                    liveKoansCard.value=it
                }

            }
        }


        liveKoansHighlightedList.addSource(liveSearchQuery) { searchQuery ->

            if (getAdapterType() == ADAPTER_TYPE_LIST) {

                val koans = koanDao.searchKoansByFts(searchQuery.sanitizeSearchQuery())

                liveKoansHighlightedList.addSource(koans) { koansList ->

                    viewModelScope.launch(Dispatchers.Default) {

                        val highlightColor = if (application.isNightMode()) "#FFFFFF" else "#000000"
                        val listOfHighlightedKoans = koansList.map { koan ->
                            val highlightedKoan = koan.getHighlightedString(searchQuery,highlightColor)
                          //  Log.d("highlighted query",highlightedKoan)
                            HighlightedKoans(koan, highlightedKoan)
                        }
                        _liveKoanNoResult.postValue(searchQuery.isNotEmpty() && listOfHighlightedKoans.isEmpty())

                        liveKoansHighlightedList.postValue(listOfHighlightedKoans)
                    }

                }
            }
        }

    }

    fun toggleAdapterType(){
        viewModelScope.launch (Dispatchers.Default){
            val currentAdapterType = _liveAdapterType.value?.peekContent()
            if (currentAdapterType== ADAPTER_TYPE_CARD)
                _liveAdapterType.postValue(Event(ADAPTER_TYPE_LIST))
            else
                _liveAdapterType.postValue(Event(ADAPTER_TYPE_CARD))
        }
    }

    private fun Koan.getHighlightedString(searchQuery: String, highlightColor:String): String {
        val plainKoan = koan.toPlainText()
        val index = plainKoan.indexOf(searchQuery,ignoreCase = true)

        Log.d("index",index.toString())

        var indexOfPrevWord = if (index==-1) 0 else index
        if (indexOfPrevWord!=0)
            indexOfPrevWord--
        if (indexOfPrevWord>0)
        {
            while (indexOfPrevWord>0)
            {
                indexOfPrevWord--
                if (plainKoan[indexOfPrevWord]==' ')
                {
                    if (index-indexOfPrevWord<10)
                        continue
                    else
                        break
                }
                else if (indexOfPrevWord==0)
                    break

            }
        }

        /*Log.d("indexOfPrevWord",indexOfPrevWord.toString())
        Log.d("plainKoan",plainKoan)*/

        val trimmedKoan= if (indexOfPrevWord==0)
            if (plainKoan.length>=indexOfPrevWord+50) plainKoan.substring(0,indexOfPrevWord+50) else plainKoan
        else
            if(plainKoan.length>=indexOfPrevWord+50) "...${plainKoan.substring(indexOfPrevWord, indexOfPrevWord+50)}" else "...${plainKoan.substring(indexOfPrevWord)}"//if (index - 10 >= 0) koan.substring(index - 10) else koan
        return trimmedKoan.toHighlightedText(searchQuery, highlightColor)

    }


    fun searchKoans(searchQuery: String/*, adapterType: Int*/) {
        liveSearchQuery.postValue(searchQuery.trim())
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("SearchKoansViewModel","onCleared called")
    }

    /* Like Query
    cup : 24 ms - 32 ms
    daibai: 24 ms
    joshu: 24 ms
    buddha: 23ms



     021-03-21 10:49:24.912 3846-3846/com.arupakaman.kawa D/likeQuery: cup: 32ms
     2021-03-21 10:49:24.912 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 4
     2021-03-21 10:49:30.522 3846-3846/com.arupakaman.kawa D/likeQuery:: 21ms
     2021-03-21 10:49:30.522 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 152
     2021-03-21 10:49:32.159 3846-3846/com.arupakaman.kawa D/likeQuery: cup: 26ms
     2021-03-21 10:49:32.159 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 4
     2021-03-21 10:49:35.833 3846-3846/com.arupakaman.kawa D/likeQuery:: 18ms
     2021-03-21 10:49:35.833 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 152
     2021-03-21 10:49:37.453 3846-3846/com.arupakaman.kawa D/likeQuery: cup: 25ms
     2021-03-21 10:49:37.453 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 4
     2021-03-21 10:49:42.625 3846-3846/com.arupakaman.kawa D/likeQuery:: 14ms
     2021-03-21 10:49:42.625 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 152
     2021-03-21 10:49:44.271 3846-3846/com.arupakaman.kawa D/likeQuery: cup: 24ms
     2021-03-21 10:49:44.271 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 4
     2021-03-21 10:49:46.291 3846-3846/com.arupakaman.kawa D/likeQuery:: 14ms
     2021-03-21 10:49:46.292 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 152
     2021-03-21 10:49:47.830 3846-3846/com.arupakaman.kawa D/likeQuery: cup: 24ms
     2021-03-21 10:49:47.830 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 4
     2021-03-21 10:49:50.401 3846-3846/com.arupakaman.kawa D/likeQuery:: 14ms
     2021-03-21 10:49:50.401 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 152
     2021-03-21 10:49:51.898 3846-3846/com.arupakaman.kawa D/likeQuery: cup: 24ms
     2021-03-21 10:49:51.898 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 4
     2021-03-21 10:50:39.466 3846-3846/com.arupakaman.kawa D/likeQuery:: 14ms
     2021-03-21 10:50:39.466 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 152





     2021-03-21 10:50:54.119 3846-3846/com.arupakaman.kawa D/likeQuery: daibai: 24ms
     2021-03-21 10:50:54.119 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 1
     2021-03-21 10:51:09.777 3846-3846/com.arupakaman.kawa D/likeQuery:: 14ms
     2021-03-21 10:51:09.777 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 152
     2021-03-21 10:51:11.434 3846-3846/com.arupakaman.kawa D/likeQuery: baso: 23ms
     2021-03-21 10:51:11.434 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 2



     2021-03-21 10:51:37.226 3846-3846/com.arupakaman.kawa D/likeQuery:: 14ms
     2021-03-21 10:51:37.226 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 152
     2021-03-21 10:51:39.420 3846-3846/com.arupakaman.kawa D/likeQuery: joshu: 24ms
     2021-03-21 10:51:39.420 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 10
     2021-03-21 10:51:48.688 3846-3846/com.arupakaman.kawa D/likeQuery:: 16ms
     2021-03-21 10:51:48.688 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 152
     2021-03-21 10:51:50.418 3846-3846/com.arupakaman.kawa D/likeQuery: wind: 24ms
     2021-03-21 10:51:50.418 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 6
     2021-03-21 10:51:56.664 3846-3846/com.arupakaman.kawa D/likeQuery:: 16ms
     2021-03-21 10:51:56.664 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 152
     2021-03-21 10:51:58.050 3846-3846/com.arupakaman.kawa D/likeQuery: the: 17ms
     2021-03-21 10:51:58.051 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 151
     2021-03-21 10:52:11.736 3846-3846/com.arupakaman.kawa D/likeQuery:: 14ms
     2021-03-21 10:52:11.736 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 152
     2021-03-21 10:52:13.780 3846-3846/com.arupakaman.kawa D/likeQuery: budha: 24ms
     2021-03-21 10:52:13.781 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 0
     2021-03-21 10:52:19.712 3846-3846/com.arupakaman.kawa D/likeQuery: udha: 26ms
     2021-03-21 10:52:19.712 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 0
     2021-03-21 10:52:20.433 3846-3846/com.arupakaman.kawa D/likeQuery: Budha: 24ms
     2021-03-21 10:52:20.433 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 0
     2021-03-21 10:52:47.786 3846-3846/com.arupakaman.kawa D/likeQuery:: 44ms
     2021-03-21 10:52:47.786 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 152


     2021-03-21 10:52:53.094 3846-3846/com.arupakaman.kawa D/likeQuery: bu: 50ms
     2021-03-21 10:52:53.094 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 107
     2021-03-21 10:52:54.233 3846-3846/com.arupakaman.kawa D/likeQuery: budd: 22ms
     2021-03-21 10:52:54.233 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 42
     2021-03-21 10:52:55.137 3846-3846/com.arupakaman.kawa D/likeQuery: buddha: 23ms
     2021-03-21 10:52:55.137 3846-3846/com.arupakaman.kawa D/likeQuery: resultSize: 33
 */

    /*
   withFts:
    cup : 4ms-5ms
    daibai : 4ms-5ms
    joshu: 5ms-9ms
    buddha: 7ms-11ms


    2021-03-21 12:02:07.091 12431-12431/com.arupakaman.kawa D/likeQuery: cup: 4ms
2021-03-21 12:02:07.091 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 4
2021-03-21 12:02:10.080 12431-12431/com.arupakaman.kawa D/likeQuery:: 3ms
2021-03-21 12:02:10.080 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 0
2021-03-21 12:02:12.300 12431-12431/com.arupakaman.kawa D/likeQuery: cup: 5ms
2021-03-21 12:02:12.300 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 4
2021-03-21 12:02:14.779 12431-12431/com.arupakaman.kawa D/likeQuery:: 2ms
2021-03-21 12:02:14.779 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 0
2021-03-21 12:02:16.392 12431-12431/com.arupakaman.kawa D/likeQuery: cup: 5ms
2021-03-21 12:02:16.392 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 4
2021-03-21 12:02:19.089 12431-12431/com.arupakaman.kawa D/likeQuery:: 3ms
2021-03-21 12:02:19.089 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 0
2021-03-21 12:02:20.324 12431-12431/com.arupakaman.kawa D/likeQuery: cup: 4ms
2021-03-21 12:02:20.324 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 4

2021-03-21 12:03:15.281 12431-12431/com.arupakaman.kawa D/likeQuery:: 2ms
2021-03-21 12:03:15.282 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 0
2021-03-21 12:03:17.345 12431-12431/com.arupakaman.kawa D/likeQuery: daibai: 4ms
2021-03-21 12:03:17.346 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 1
2021-03-21 12:03:21.462 12431-12431/com.arupakaman.kawa D/likeQuery:: 3ms
2021-03-21 12:03:21.462 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 0
2021-03-21 12:03:23.743 12431-12431/com.arupakaman.kawa D/likeQuery: daibai: 5ms
2021-03-21 12:03:23.743 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 1
2021-03-21 12:03:27.341 12431-12431/com.arupakaman.kawa D/likeQuery:: 3ms
2021-03-21 12:03:27.341 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 0
2021-03-21 12:03:29.332 12431-12431/com.arupakaman.kawa D/likeQuery: daibai: 4ms
2021-03-21 12:03:29.332 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 1


2021-03-21 12:04:14.249 12431-12431/com.arupakaman.kawa D/likeQuery: joshu: 6ms
2021-03-21 12:04:14.249 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 10
2021-03-21 12:04:18.204 12431-12431/com.arupakaman.kawa D/likeQuery:: 3ms
2021-03-21 12:04:18.204 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 0
2021-03-21 12:04:20.163 12431-12431/com.arupakaman.kawa D/likeQuery: joshu: 5ms
2021-03-21 12:04:20.163 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 10
2021-03-21 12:04:22.353 12431-12431/com.arupakaman.kawa D/likeQuery:: 2ms
2021-03-21 12:04:22.353 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 0
2021-03-21 12:04:24.300 12431-12431/com.arupakaman.kawa D/likeQuery: joshu: 9ms
2021-03-21 12:04:24.300 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 10
2021-03-21 12:04:26.921 12431-12431/com.arupakaman.kawa D/likeQuery:: 2ms
2021-03-21 12:04:26.921 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 0
2021-03-21 12:04:28.927 12431-12431/com.arupakaman.kawa D/likeQuery: joshu: 6ms
2021-03-21 12:04:28.927 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 10
2021-03-21 12:04:32.994 12431-12431/com.arupakaman.kawa D/likeQuery:: 2ms
2021-03-21 12:04:32.994 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 0
2021-03-21 12:04:35.515 12431-12431/com.arupakaman.kawa D/likeQuery: joshu: 5ms
2021-03-21 12:04:35.515 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 10


2021-03-21 12:05:37.222 12431-12431/com.arupakaman.kawa D/likeQuery: buddha: 7ms
2021-03-21 12:05:37.222 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 33
2021-03-21 12:05:42.890 12431-12431/com.arupakaman.kawa D/likeQuery:: 4ms
2021-03-21 12:05:42.891 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 0
2021-03-21 12:05:45.296 12431-12431/com.arupakaman.kawa D/likeQuery: buddha: 11ms
2021-03-21 12:05:45.296 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 33
2021-03-21 12:05:48.319 12431-12431/com.arupakaman.kawa D/likeQuery:: 4ms
2021-03-21 12:05:48.319 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 0
2021-03-21 12:05:50.368 12431-12431/com.arupakaman.kawa D/likeQuery: buddha: 8ms
2021-03-21 12:05:50.368 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 33
2021-03-21 12:05:52.172 12431-12431/com.arupakaman.kawa D/likeQuery:: 2ms
2021-03-21 12:05:52.172 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 0
2021-03-21 12:05:54.278 12431-12431/com.arupakaman.kawa D/likeQuery: buddha: 10ms
2021-03-21 12:05:54.278 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 33
2021-03-21 12:05:56.308 12431-12431/com.arupakaman.kawa D/likeQuery:: 3ms
2021-03-21 12:05:56.308 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 0
2021-03-21 12:05:58.317 12431-12431/com.arupakaman.kawa D/likeQuery: buddha: 9ms
2021-03-21 12:05:58.317 12431-12431/com.arupakaman.kawa D/likeQuery: resultSize: 33

     */
}