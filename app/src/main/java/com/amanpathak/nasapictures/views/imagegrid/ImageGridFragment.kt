package com.amanpathak.nasapictures.views.imagegrid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.amanpathak.nasapictures.MainViewModel
import com.amanpathak.nasapictures.databinding.FragmentImagegridBinding
import com.amanpathak.nasapictures.databinding.ImagegridToolbarBinding

class ImageGridFragment : Fragment() {
    private lateinit var binding: FragmentImagegridBinding
    private val mainViewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagegridBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    private fun initViews() {

        ImagegridToolbarBinding.inflate(layoutInflater,binding.root,true)

    }

}