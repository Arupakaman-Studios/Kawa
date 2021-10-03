package com.arupakaman.kawa.ui.koans.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.arupakaman.kawa.R
import com.arupakaman.kawa.databinding.FragmentKoansListingBinding
import com.arupakaman.kawa.ui.koans.KoansActivitySharedViewModel
import com.arupakaman.kawa.ui.koans.list.ad.KoansAdapterWithAd
import com.arupakaman.kawa.utils.motions.navigateToContainerTransform
import com.arupakaman.kawa.utils.motions.postponeEnterTrans
import com.flavours.AdManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KoansListingFragment : Fragment() {

    private var fragmentCreated = false

    private val mBinding by lazy { FragmentKoansListingBinding.inflate(layoutInflater) } // :FragmentKoansListingBinding?=null //

    private val koansListingViewModel by viewModels<KoansListingViewModel>()//lazy { ViewModelProvider(this).get(KoansListingViewModel::class.java) }
    private val koansActivitySharedViewModel by activityViewModels<KoansActivitySharedViewModel>()//lazy { ViewModelProvider(requireActivity()).get(KoansActivitySharedViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentCreated=true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        /*val fragmentKoansListingBinding=FragmentKoansListingBinding.inflate(layoutInflater)
        mBinding=fragmentKoansListingBinding*/
        return mBinding.root
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        super.onViewCreated(root, savedInstanceState)

        mBinding.run {
            lifecycleOwner = viewLifecycleOwner
            viewModel = koansListingViewModel

            if (fragmentCreated){
                //rvKoans.setHasFixedSize(true)
                rvKoans.adapter=KoansAdapterWithAd(KoanClickListener { itemView, position, koan->

                    val direction = KoansListingFragmentDirections.actionKoansListingFragmentToKoanDetailFragment(position)
                    navigateToContainerTransform(itemView,R.string.transition_koan_detail,direction)

                })
                fragmentCreated=false

                loadNativeAds()
            }
        }

        setObserver()

        postponeEnterTrans()
    }

    private fun loadNativeAds(){
        mBinding.partialAdContainer.let { container->
            AdManager.showAd(container,AdManager.BANNER_AD_KOAN_LISTING)
        }

        AdManager.showNativeAd(requireActivity()){
            if (it==null)
                return@showNativeAd
            koansListingViewModel.addAdInTheList(it)
        }
    }

    private fun setObserver(){
        koansListingViewModel.liveKoansWithAd.observe(viewLifecycleOwner,{
            koansActivitySharedViewModel.setKoanListForDetail(it)
            Log.d("koanList","withAd: "+it.size)
        })
    }

    override fun onDestroyView() {
        AdManager.destroyAd(AdManager.BANNER_AD_KOAN_LISTING)
        //mBinding=null
        super.onDestroyView()

        Log.d("koanListing","onDestroyView called")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("koanListing","onDestroy called")
    }
}