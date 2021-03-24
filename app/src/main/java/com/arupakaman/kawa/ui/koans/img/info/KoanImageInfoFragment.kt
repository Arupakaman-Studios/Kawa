package com.arupakaman.kawa.ui.koans.img.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arupakaman.kawa.databinding.FragmentKoanImageInfoBinding
import com.arupakaman.kawa.model.KoanImage

class KoanImageInfoFragment : Fragment() {

    companion object{
        var currentKoanImage : KoanImage?=null
    }

    val binding by lazy { FragmentKoanImageInfoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.koanImage= currentKoanImage
    }
}