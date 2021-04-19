package com.arupakaman.kawa.ui.koans.img.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arupakaman.kawa.databinding.FragmentKoanImageInfoBinding
import com.arupakaman.kawa.ui.koans.KoansActivitySharedViewModel

class KoanImageInfoFragment : Fragment() {

    private val koansActivitySharedViewModel by lazy { ViewModelProvider(requireActivity()).get(
        KoansActivitySharedViewModel::class.java) }


    val binding by lazy { FragmentKoanImageInfoBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.koansActivitySharedViewModel=koansActivitySharedViewModel

    }
}