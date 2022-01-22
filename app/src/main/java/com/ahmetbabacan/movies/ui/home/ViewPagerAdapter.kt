package com.ahmetbabacan.movies.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ahmetbabacan.movies.data.models.Movie
import com.ahmetbabacan.movies.databinding.ItemViewpagerBinding


class ViewPagerAdapter(private val listener: ItemClickedListener) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    private val items: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = ItemViewpagerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position =
                    bindingAdapterPosition.takeIf { pos -> pos != RecyclerView.NO_POSITION }
                        ?: return@setOnClickListener
                listener.onItemClicked(position)
                it.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(items[position])
                )
            }
        }
    }

    fun setItems(items: List<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        with(holder.binding) {
            movie = items[position]
        }
    }

    override fun getItemCount() = items.size

    class ViewPagerViewHolder(val binding: ItemViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root)


    interface ItemClickedListener {
        fun onItemClicked(position: Int)
    }
}