package com.arupakaman.kawa.ui.koans.list

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.arupakaman.kawa.databinding.FragmentKoansListingBinding
import com.arupakaman.kawa.ui.koans.detail.KoanDetailFragmentArgs

class KoansListingFragment : Fragment() {

    private val mBinding by lazy { FragmentKoansListingBinding.inflate(layoutInflater) }
    private val koansListingViewModel by lazy { ViewModelProvider(this).get(KoansListingViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.run {
            lifecycleOwner = viewLifecycleOwner
            viewModel = koansListingViewModel

            rvKoans.adapter=KoansAdapter(KoanClickListener {
                view.findNavController().navigate(KoansListingFragmentDirections.actionKoansListingFragmentToKoanDetailFragment(it))
            })
        }
    }

}