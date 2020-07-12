package com.kehao.myapplication.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kehao.myapplication.R
import com.kehao.myapplication.base.BaseFragment
import com.kehao.myapplication.databinding.FragmentListBinding
import com.kehao.myapplication.network.LoadState
import com.kehao.myapplication.utils.showShortToast
import com.kehao.myapplication.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : BaseFragment<MainActivityViewModel, FragmentListBinding>(true) {
    private val linearAdapter = MovieListLinearAdapter()
    override fun getContentViewId() = R.layout.fragment_list

    override fun observeData() {
        viewModel.loadState.observe(viewLifecycleOwner, Observer {
            refresh_layout.isRefreshing = it.equals(LoadState.Loading())
        })

        // for paging list
        viewModel.allMovies.observe(viewLifecycleOwner, Observer {
            showShortToast("read from local db", requireContext())
            linearAdapter.submitList(it)
        })
    }

    override fun handleEvent() {
        movie_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = linearAdapter
        }

        refresh_layout.setOnRefreshListener {
            viewModel.refreshAll()
        }
    }
}