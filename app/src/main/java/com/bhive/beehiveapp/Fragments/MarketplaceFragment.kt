package com.bhive.beehiveapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.adapters.MarketPlaceAdapter
import com.bhive.beehiveapp.databinding.FragmentMarketplaceBinding
import com.bhive.beehiveapp.models.MarketPlaceModel

class MarketplaceFragment : BaseFragment() {
    private val adapter = MarketPlaceAdapter()
    var ColorPrimary : String ? = null

    private lateinit var marketplaceBinding: FragmentMarketplaceBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        marketplaceBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_marketplace,
            container,
            false
        )
        return marketplaceBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arrayList = ArrayList<MarketPlaceModel>()
        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }
        val recyclerView =view.findViewById<RecyclerView>(R.id.recyclerView)
        marketplaceBinding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, 1)

        arrayList.add(MarketPlaceModel(resources.getDrawable(R.drawable.img_1),"title1","description1"))
        arrayList.add(MarketPlaceModel(resources.getDrawable(R.drawable.img_1),"title2","description2"))
        arrayList.add(MarketPlaceModel(resources.getDrawable(R.drawable.img_1),"title3","description3"))
        arrayList.add(MarketPlaceModel(resources.getDrawable(R.drawable.img_1),"title4","description4"))
        arrayList.add(MarketPlaceModel(resources.getDrawable(R.drawable.img_1),"title5","description5"))
        arrayList.add(MarketPlaceModel(resources.getDrawable(R.drawable.img_1),"title5","description5"))
        arrayList.add(MarketPlaceModel(resources.getDrawable(R.drawable.img_1),"title5","description5"))
        marketplaceBinding.recyclerView.adapter=adapter
        adapter.submitList(arrayList)

    }
}


