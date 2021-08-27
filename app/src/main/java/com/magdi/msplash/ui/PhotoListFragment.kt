package com.magdi.msplash.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.magdi.msplash.R
import com.magdi.msplash.ui.utils.EndlessScrollListener
import com.magdi.msplash.utils.Results
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PhotoListFragment : Fragment() {

    companion object {
        fun newInstance() = PhotoListFragment()
        private const val TAG = "PhotoListFragment"
    }

    private lateinit var photoListView: RecyclerView
    private val photoAdapter: PhotoListAdapter = PhotoListAdapter()

    private val viewModel: PhotoListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val parent = inflater.inflate(R.layout.photo_list_fragment, container, false)
        photoListView = parent.findViewById(R.id.image_recyclerView)
        setupList(photoListView)
        return parent
    }

    private fun setupList(photoListView: RecyclerView?) {
        photoListView?.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            adapter = photoAdapter
            addOnScrollListener(EndlessScrollListener(linearLayoutManager) { currentPage ->
                loadMore(currentPage)
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadPhotos().collect { results ->
                if (results is Results.Success && results.data != null) {
                    photoAdapter.setList(results.data)
                }
            }
        }
        loadMore()
    }

    private fun loadMore(currentPage: Int = 1) {
        viewLifecycleOwner.lifecycleScope.launch {
            Log.e(TAG, "Loading...")
            viewModel.refreshPhotos(currentPage).collect { results ->
                //TODO show loading status
            }
        }
    }
}