package com.bhive.beehiveapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.adapters.NewsletterAdapter
import com.bhive.beehiveapp.databinding.FragmentNewsletterMainBinding
import com.bhive.beehiveapp.interfaces.NewsletterInterface
import com.bhive.beehiveapp.models.NewsletterModel

class NewsletterFragment : BaseFragment() , NewsletterInterface{

    private val adapter = NewsletterAdapter()
    var ColorPrimary : String ? = null

    private lateinit var newsletterMainBinding: FragmentNewsletterMainBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsletterMainBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_newsletter_main,
            container,
            false
        )
        return newsletterMainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }
        val arrayList = ArrayList<NewsletterModel>()

        adapter.newsletterInterface=this
        val recyclerView =view.findViewById<RecyclerView>(R.id.recyclerView)

        arrayList.add(NewsletterModel("title1"))
        arrayList.add(NewsletterModel("title2"))
        arrayList.add(NewsletterModel("title3"))
        arrayList.add(NewsletterModel("title4"))
        arrayList.add(NewsletterModel("title5"))
        arrayList.add(NewsletterModel("title5"))
        arrayList.add(NewsletterModel("title5"))
        newsletterMainBinding.recyclerView.adapter=adapter
        adapter.submitList(arrayList)
    }

    override fun displayItem(position: Int) {

    }
}