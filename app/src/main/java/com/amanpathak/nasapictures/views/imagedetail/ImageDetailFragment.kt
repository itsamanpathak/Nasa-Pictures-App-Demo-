package com.amanpathak.nasapictures.views.imagedetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import androidx.recyclerview.widget.SimpleItemAnimator
import com.amanpathak.nasapictures.MainViewModel
import com.amanpathak.nasapictures.R
import com.amanpathak.nasapictures.adapters.GalleryAdapter
import com.amanpathak.nasapictures.databinding.FragmentImagedetailBinding
import com.amanpathak.nasapictures.databinding.ImagedetailfragmentToolbarBinding
import com.amanpathak.nasapictures.models.PhotoModel


class ImageDetailFragment : Fragment(), GalleryAdapter.Interaction {
    private lateinit var binding: FragmentImagedetailBinding
    private lateinit var toolbarBinding : ImagedetailfragmentToolbarBinding
    private val mainViewModel : MainViewModel by activityViewModels()
    private var adapter : GalleryAdapter? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagedetailBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        listeners()
    }

    private fun listeners() {

        mainViewModel.currentModelLiveData.observe(viewLifecycleOwner, Observer {
            toolbarBinding.title.text = it?.title
        })

        mainViewModel.photoListLiveData.observe(viewLifecycleOwner, Observer {
            with(binding.photoDetailRecyclerView) {
                PagerSnapHelper().attachToRecyclerView(binding.photoDetailRecyclerView)
                layoutManager = LinearLayoutManager(context,HORIZONTAL, false)
                adapter = GalleryAdapter(requireActivity(), this@ImageDetailFragment,it,GalleryAdapter.TYPE.PHOTO_DETAIL)
                scrollToPosition(mainViewModel.currentPositionLiveData.value!!)
                adapter = adapter
                val animator: ItemAnimator = binding.photoDetailRecyclerView.itemAnimator!!
                if (animator is SimpleItemAnimator) {
                    animator.supportsChangeAnimations = false
                }
            }

        })

        binding.photoDetailRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val position: Int = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    mainViewModel.setScrollPosition(position)
                }
            }
        })
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun initViews() {

        toolbarBinding = ImagedetailfragmentToolbarBinding.inflate(layoutInflater)
        toolbarBinding.back.setOnClickListener {
            NavHostFragment.findNavController(this@ImageDetailFragment).popBackStack()
        }
        binding.toolbar.addView(toolbarBinding.root)

        toolbarBinding.menu.setOnClickListener {

            it?.let {
                val optionMenu = PopupMenu(requireContext(), it)
                optionMenu.inflate(R.menu.imagedetail_menu)
                optionMenu.show()


                optionMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.details -> {
                            val action = ImageDetailFragmentDirections.actionImageDetailFragmentToImageMetaFragment()
                            NavHostFragment.findNavController(this@ImageDetailFragment).navigate(action)
                        }
                    }
                    true
                }
            }
        }


    }



    override fun onImageSelect(photoItem: PhotoModel, position: Int, view: ImageView) {}


}