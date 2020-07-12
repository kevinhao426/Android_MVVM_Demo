package com.kehao.myapplication.view

import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.kehao.myapplication.R
import com.kehao.myapplication.base.BaseFragment
import com.kehao.myapplication.databinding.FragmentDetailBinding
import com.kehao.myapplication.viewModel.MainActivityViewModel

class DetailFragment : BaseFragment<MainActivityViewModel, FragmentDetailBinding>(true) {
    val args: DetailFragmentArgs by navArgs()
    override fun getContentViewId(): Int = R.layout.fragment_detail

    override fun observeData() {
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            binding.movie = it
        })
    }

    override fun handleEvent() {
        val movidId = args.movieId
        viewModel.getMovieItem(movidId)
    }
}