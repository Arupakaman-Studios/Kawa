package com.arupakaman.kawa.ui.koans.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arupakaman.kawa.BuildConfig
import com.arupakaman.kawa.databinding.FragmentAboutUsBinding
import com.arupakaman.kawa.utils.onClick
import com.arupakaman.kawa.utils.openAppInPlayStore
import com.arupakaman.kawa.utils.openDonationVersion
import com.arupakaman.kawa.utils.shareApp

class AboutUsFragment : Fragment() {

    private val binding by lazy { FragmentAboutUsBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root//inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtDonate.onClick {
            requireActivity().openDonationVersion()
        }

        binding.txtShare.onClick {
            requireActivity().shareApp()
        }

        binding.txtRate.onClick {
            requireActivity().openAppInPlayStore(BuildConfig.APPLICATION_ID)
        }
    }
}