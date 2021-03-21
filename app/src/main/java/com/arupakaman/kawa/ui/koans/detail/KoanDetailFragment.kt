package com.arupakaman.kawa.ui.koans.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arupakaman.kawa.databinding.FragmentKoanDetailBinding

class KoanDetailFragment : Fragment() {

    private val binding by lazy { FragmentKoanDetailBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val koan = KoanDetailFragmentArgs.fromBundle(requireArguments()).koan
        binding.koan=koan


        /*context?.let {ctx->
            val koanDao = KoansDatabase.getInstance(ctx).koanDao
            val list = koanDao.getAllKoans()
            list.observe(viewLifecycleOwner, { list->
                val item = list[Random.nextInt(100)]
                view.findViewById<TextView>(R.id.txtKoanTitle).text = item.title
                view.findViewById<TextView>(R.id.txtKoan).text = Html.fromHtml(item.koan)//item.koan
            })
        }

        KoansGenerator(requireContext()).prepareJsonArray()*/

        //prepareJsonArray()
    }

}