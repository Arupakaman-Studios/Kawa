package com.arupakaman.kawa.ui.koans.list

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.arupakaman.kawa.R
import com.arupakaman.kawa.databinding.FragmentKoansListingBinding
import com.arupakaman.kawa.ui.koans.detail.KoanDetailFragmentArgs
import com.arupakaman.kawa.utils.motions.navigateToContainerTransform
import com.arupakaman.kawa.utils.motions.postponeEnterTrans
import com.google.android.material.transition.MaterialElevationScale

class KoansListingFragment : Fragment() {

    private val mBinding by lazy { FragmentKoansListingBinding.inflate(layoutInflater) }
    private val koansListingViewModel by lazy { ViewModelProvider(this).get(KoansListingViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        super.onViewCreated(root, savedInstanceState)

        mBinding.run {
            lifecycleOwner = viewLifecycleOwner
            viewModel = koansListingViewModel

            rvKoans.adapter=KoansAdapter(KoanClickListener {itemView,koan->

                val direction = KoansListingFragmentDirections.actionKoansListingFragmentToKoanDetailFragment(koan)
                navigateToContainerTransform(root,itemView,R.string.transition_koan_detail,direction)

            })
        }


        postponeEnterTrans()
    }

}