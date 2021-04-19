package com.arupakaman.kawa.ui.koans.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arupakaman.kawa.R
import com.arupakaman.kawa.databinding.FragmentKoansListingBinding
import com.arupakaman.kawa.utils.motions.navigateToContainerTransform
import com.arupakaman.kawa.utils.motions.postponeEnterTrans

class KoansListingFragment : Fragment() {

    private var fragmentCreated = false

    private val mBinding by lazy { FragmentKoansListingBinding.inflate(layoutInflater) }
    private val koansListingViewModel by lazy { ViewModelProvider(this).get(KoansListingViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentCreated=true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        super.onViewCreated(root, savedInstanceState)

        mBinding.run {
            lifecycleOwner = viewLifecycleOwner
            viewModel = koansListingViewModel

            if (fragmentCreated){
                rvKoans.adapter=KoansAdapter(KoanClickListener {itemView,koan->

                    val direction = KoansListingFragmentDirections.actionKoansListingFragmentToKoanDetailFragment(koan)
                    navigateToContainerTransform(root,itemView,R.string.transition_koan_detail,direction)

                })
                fragmentCreated=false
            }
        }


        postponeEnterTrans()
    }

}