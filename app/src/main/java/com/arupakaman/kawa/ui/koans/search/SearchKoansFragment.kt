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
import com.arupakaman.kawa.ui.koans.list.KoanClickListener
import com.arupakaman.kawa.ui.koans.list.KoansAdapter
import com.arupakaman.kawa.utils.addOnScrollListenerToHideKeyboard
import com.arupakaman.kawa.utils.afterTextChangedDebounce
import com.arupakaman.kawa.utils.hideKeyboard
import com.arupakaman.kawa.utils.motions.navigateToContainerTransform
import com.arupakaman.kawa.utils.motions.postponeEnterTrans
import com.arupakaman.kawa.utils.onClick

const val ADAPTER_TYPE_CARD=1
const val ADAPTER_TYPE_LIST=2

class SearchKoansFragment : Fragment() {

    private var adapterType = ADAPTER_TYPE_LIST

    var start= 0L

    private val mBinding by lazy {FragmentSearchKoansBinding.inflate(layoutInflater)}
    private val searchKoansViewModel by lazy { ViewModelProvider(this).get(SearchKoansViewModel::class.java) }

    private var fragmentCreated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SearchKoansFragment","onCreate")
        fragmentCreated=true
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

        if (fragmentCreated)
            mBinding.setupListenersAndAdapter(view)
        else
            mBinding.rvKoans.tag = "1"

        /*searchKoansViewModel.liveKoansHighlightedList.observe(viewLifecycleOwner){
            Log.d("SearchKoansFragment: %s",it.size.toString())

            val end=System.currentTimeMillis()
            Log.d("likeQuery: ${mBinding.etSearch.text}",(end-start).toString()+"ms")
            Log.d("likeQuery: resultSize",it.size.toString())
        }*/

        postponeEnterTrans()
    }

    private fun FragmentSearchKoansBinding.setupListenersAndAdapter(view: View){

        setListeners()

        rvKoans.adapter = KoansListAdapter(koanClickListener)
        rvKoans.addOnScrollListenerToHideKeyboard(requireActivity(),view,etSearch)
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
            start=System.currentTimeMillis()
            searchKoansViewModel.searchKoans(searchQuery,adapterType)
            rvKoans.tag = ""
        }

        imgClearSearch.onClick {
            Log.d("imgClearSearch","clicked")
            etSearch.text.clear()
        }

        imgListType.onClick {
            // toggle the icon and type
            adapterType = if (adapterType==ADAPTER_TYPE_LIST) {
                imgListType.setImageResource(R.drawable.ic_list)
                rvKoans.adapter = KoansAdapter(koanClickListener)
                ADAPTER_TYPE_CARD
            } else{
                imgListType.setImageResource(R.drawable.ic_square)
                rvKoans.adapter = KoansListAdapter(koanClickListener)
                ADAPTER_TYPE_LIST
            }

            searchKoansViewModel.searchKoans(mBinding.etSearch.text.toString(),adapterType)
        }
    }

    private val koanClickListener = KoanClickListener{itemView,koan->
        Log.d("koanClickListener",koan.title)

        activity?.hideKeyboard(mBinding.root)

        val direction = SearchKoansFragmentDirections.actionSearchKoansFragmentToKoanDetailFragment(koan)
        //view.findNavController().navigate(direction)
        navigateToContainerTransform(mBinding.root,itemView,R.string.transition_koan_detail,direction)


    }
}