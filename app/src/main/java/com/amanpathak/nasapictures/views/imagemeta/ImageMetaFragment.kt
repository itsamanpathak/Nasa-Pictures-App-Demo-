package com.amanpathak.nasapictures.views.imagemeta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.amanpathak.nasapictures.MainViewModel
import com.amanpathak.nasapictures.databinding.FragmentImagemetaBinding
import com.amanpathak.nasapictures.databinding.ImagemetafragmentToolbarBinding

class ImageMetaFragment : Fragment() {
    private lateinit var binding: FragmentImagemetaBinding
    private val mainViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagemetaBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    private fun initViews() {

        val toolbarBinding = ImagemetafragmentToolbarBinding.inflate(layoutInflater)
        toolbarBinding.back.setOnClickListener {
            NavHostFragment.findNavController(this@ImageMetaFragment).popBackStack()
        }
        binding.toolbar.addView(toolbarBinding.root)


        mainViewModel.currentModelLiveData.observe(viewLifecycleOwner, Observer {

            with(binding) {
                metaTitle.text = "Name : ${it?.title}"
                meditype.text = "MediaType : ${it?.mediaType}"
                date.text = "Time : ${it?.date}"
                explanation.text = "Description : \n\n${it?.explanation}"
                serviceVersion.text = "Service Version : ${it?.serviceVersion}"
            }
        })
    }

}