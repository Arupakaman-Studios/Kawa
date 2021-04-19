package com.arupakaman.kawa.ui.koans.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arupakaman.kawa.databinding.FragmentKoanDetailBinding
import com.arupakaman.kawa.ui.koans.KoansActivitySharedViewModel
import com.arupakaman.kawa.utils.getDimenFromSize
import com.arupakaman.kawa.utils.motions.setupSharedElementTransitionToContainerTransform

class KoanDetailFragment : Fragment() {

    private val binding by lazy { FragmentKoanDetailBinding.inflate(layoutInflater) }

    private val koansActivitySharedViewModel by lazy { ViewModelProvider(requireActivity()).get(KoansActivitySharedViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedElementTransitionToContainerTransform()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.lifecycleOwner = viewLifecycleOwner
        binding.koansActivitySharedViewModel = koansActivitySharedViewModel

        setObserver()

        try {
            val koan = KoanDetailFragmentArgs.fromBundle(requireArguments()).koan
            koansActivitySharedViewModel.setCurrentKoan(koan)
        }catch (e:Exception){
            koansActivitySharedViewModel.findCurrentKoan()
        }


       // KoanImageInfoFragment.currentKoanImage=koan.koanImage


       /* context?.let {ctx->
            val koanDao = KoansDatabase.getInstance(ctx).koanDao
            val list = koanDao.getAllKoans()
            list.observe(viewLifecycleOwner, { list->
                val item = list[Random.nextInt(100)]
                view.findViewById<TextView>(R.id.txtKoanTitle).text = item.title
                view.findViewById<TextView>(R.id.txtKoan).text = Html.fromHtml(item.koan)//item.koan
            })
        }*/

        //KoansGenerator(requireContext()).prepareJsonArray()

        //prepareJsonArray()
    }

    private fun setObserver(){
        koansActivitySharedViewModel.liveKoanTextSize.observe(viewLifecycleOwner, {
            context?.getDimenFromSize(it)?.let {textSizeInDimen->
                binding.txtKoan.textSize=textSizeInDimen
            }
        })

        koansActivitySharedViewModel.liveKoanTypeFace.observe(viewLifecycleOwner, {
            binding.txtKoan.typeface = it
        })
    }

}