package com.arupakaman.kawa.ui.koans.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arupakaman.kawa.R
import com.arupakaman.kawa.databinding.FragmentSearchKoansBinding
import com.arupakaman.kawa.ui.koans.KoansActivitySharedViewModel
import com.arupakaman.kawa.ui.koans.list.KoanClickListener
import com.arupakaman.kawa.ui.koans.list.KoansAdapter
import com.arupakaman.kawa.utils.*
import com.arupakaman.kawa.utils.motions.navigateToContainerTransform
import com.arupakaman.kawa.utils.motions.postponeEnterTrans
import com.flavours.AdManager

const val ADAPTER_TYPE_CARD=1
const val ADAPTER_TYPE_LIST=2

class SearchKoansFragment : Fragment() {

    //private var adapterType = ADAPTER_TYPE_LIST

    private val mBinding by lazy {FragmentSearchKoansBinding.inflate(layoutInflater)}
    private val searchKoansViewModel by lazy { ViewModelProvider(this).get(SearchKoansViewModel::class.java) }
    private val koansActivitySharedViewModel by lazy { ViewModelProvider(requireActivity()).get(
        KoansActivitySharedViewModel::class.java) }

    private var fragmentCreated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SearchKoansFragment","onCreate")
        fragmentCreated=true

        mBinding.rvKoans.adapter=null
        searchKoansViewModel.seAdapterTypeCardIfItWasAlreadySet()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        Log.d("SearchKoansFragment","onCreateView")
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("SearchKoansFragment","onViewCreated")

        mBinding.observeViewModel()

        mBinding.rvKoans.setHasFixedSize(true)
        if (fragmentCreated) {
            mBinding.setupListenersAndAdapter(view)
        }
        else
            mBinding.rvKoans.tag = "1"

        setObserver()

        /*searchKoansViewModel.liveKoansHighlightedList.observe(viewLifecycleOwner){
            Log.d("SearchKoansFragment: %s",it.size.toString())

            val end=System.currentTimeMillis()
            Log.d("likeQuery: ${mBinding.etSearch.text}",(end-start).toString()+"ms")
            Log.d("likeQuery: resultSize",it.size.toString())
        }*/

        postponeEnterTrans()



        AdManager.showAd(mBinding.partialAdContainer,AdManager.BANNER_AD_KOAN_SEARCH)
    }

    private fun FragmentSearchKoansBinding.setupListenersAndAdapter(view: View){

        setListeners()

        rvKoans.adapter = KoansListAdapter(koanClickListener)
        rvKoans.addOnScrollListenerToKeyboardHandling(requireActivity(),view,etSearch,false)
        //HighlightText.highlightColor("dummy text",txtKoanTest)

        fragmentCreated=false
    }

    private fun FragmentSearchKoansBinding.observeViewModel(){
        lifecycleOwner = viewLifecycleOwner
        viewModel = searchKoansViewModel
    }


    private fun FragmentSearchKoansBinding.setListeners(){
        etSearch.afterTextChangedDebounce {searchQuery->
            Log.d("searchQuery: %s",searchQuery)

            searchKoansViewModel.searchKoans(searchQuery)
            rvKoans.tag = ""
        }

        imgClearSearch.onClick {
            Log.d("imgClearSearch","clicked")
            etSearch.text.clear()
        }

        imgListType.onClick {
            // toggle the icon and type
            searchKoansViewModel.toggleAdapterType()
        }
    }

    private fun setObserver(){
        searchKoansViewModel.liveKoansCard.observe(viewLifecycleOwner, {
            if (searchKoansViewModel.getAdapterType() == ADAPTER_TYPE_CARD)
                koansActivitySharedViewModel.setKoanListForDetail(it)
        })

        searchKoansViewModel.liveKoansHighlightedList.observe(viewLifecycleOwner,{
            if (searchKoansViewModel.getAdapterType() == ADAPTER_TYPE_LIST)
                koansActivitySharedViewModel.setKoanListForDetailByHighlightedKoan(it)
        })

        searchKoansViewModel.liveAdapterType.observeAsEvent(viewLifecycleOwner,{adapterType->
            Log.d("liveAdapterType",adapterType.toString())
            if (adapterType==ADAPTER_TYPE_CARD) {
                mBinding.imgListType.setImageResource(R.drawable.ic_list)
                mBinding.rvKoans.adapter = KoansAdapter(koanClickListener)

                /*searchKoansViewModel.liveKoansHighlightedList.value?.let {
                    koansActivitySharedViewModel.setKoanListForDetailByHighlightedKoan(it)
                }*/
            } else{
                mBinding.imgListType.setImageResource(R.drawable.ic_square)
                mBinding.rvKoans.adapter = KoansListAdapter(koanClickListener)

                /*searchKoansViewModel.liveKoansCard.value?.let {
                    koansActivitySharedViewModel.setKoanListForDetail(it)
                }*/
            }

            searchKoansViewModel.searchKoans(mBinding.etSearch.text.toString())
        })
    }

    private val koanClickListener = KoanClickListener{itemView,position,koan->
        Log.d("koanClickListener",koan.title)

        activity?.hideKeyboard(mBinding.root)

        val direction = SearchKoansFragmentDirections.actionSearchKoansFragmentToKoanDetailFragment(position)
        //view.findNavController().navigate(direction)
        navigateToContainerTransform(itemView,R.string.transition_koan_detail,direction)


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SearchKoansFragment","onDestroy")
    }

    override fun onDestroyView() {
        AdManager.destroyAd(AdManager.BANNER_AD_KOAN_SEARCH)
        super.onDestroyView()

        Log.d("SearchKoansFragment","onDestroyView")
    }
}