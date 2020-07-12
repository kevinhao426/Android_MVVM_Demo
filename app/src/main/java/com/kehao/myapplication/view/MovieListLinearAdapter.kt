package com.kehao.myapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kehao.myapplication.R
import com.kehao.myapplication.databinding.ItemCardLinearBinding
import com.kehao.myapplication.model.Movie

class MovieListLinearAdapter :
    PagedListAdapter<Movie, MovieListLinearAdapter.LinearViewHolder>(diffCallback) {

    class LinearViewHolder(var binding: ItemCardLinearBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinearViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCardLinearBinding>(
            inflater,
            R.layout.item_card_linear,
            parent,
            false
        )
        return LinearViewHolder(view)
    }

    override fun onBindViewHolder(holder: LinearViewHolder, position: Int) {
        holder.binding.movie = getItem(position)
        holder.binding.root.setOnClickListener {
            val action =
                ListFragmentDirections.actionListFragmentToDetailFragment(getItem(position)!!.uuid)
            it.findNavController().navigate(action)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.uuid == newItem.uuid

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}

