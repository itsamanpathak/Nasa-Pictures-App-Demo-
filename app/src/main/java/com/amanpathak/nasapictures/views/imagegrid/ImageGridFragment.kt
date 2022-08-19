package com.amanpathak.nasapictures.views.imagegrid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.amanpathak.nasapictures.MainViewModel
import com.amanpathak.nasapictures.adapters.GalleryAdapter
import com.amanpathak.nasapictures.databinding.FragmentImagegridBinding
import com.amanpathak.nasapictures.databinding.ImagegridToolbarBinding
import com.amanpathak.nasapictures.models.PhotoModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageGridFragment : Fragment(), GalleryAdapter.Interaction {
    private lateinit var binding: FragmentImagegridBinding
    private val mainViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagegridBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    private fun initViews() {

        ImagegridToolbarBinding.inflate(layoutInflater, binding.root, true)

        mainViewModel.photoListLiveData.observe(viewLifecycleOwner, Observer {
            with(binding.photoRecyclerView) {
                PagerSnapHelper().attachToRecyclerView(binding.photoRecyclerView)
                layoutManager = GridLayoutManager(context, 3)
                adapter = GalleryAdapter(
                    requireActivity(),
                    this@ImageGridFragment,
                    it,
                    GalleryAdapter.TYPE.GALLERY
                )
            }
            binding.totalPhotos.text = "Total ${it.size} Photos"

        })


    }

    override fun onImageSelect(photoItem: PhotoModel, position: Int, view: ImageView) {
        mainViewModel.setScrollPosition(position)
        val action = ImageGridFragmentDirections.actionImageGridFragmentToImageDetailFragment()
        NavHostFragment.findNavController(this@ImageGridFragment).navigate(action)
    }

}