package com.ahmetbabacan.movies.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.viewpager2.widget.ViewPager2
import com.ahmetbabacan.movies.R
import com.ahmetbabacan.movies.data.models.Movie
import com.ahmetbabacan.movies.databinding.ItemMovieBinding
import com.ahmetbabacan.movies.databinding.LayoutViewpagerBinding
import com.skydoves.bindables.binding

class MovieAdapter(private val listener: ViewPagerAdapter.ItemClickedListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ViewPagerAdapter.ItemClickedListener {

    private val VIEW_TYPE_BANNER = 0
    private val VIEW_TYPE_MOVIE = 1

    private var viewPagerLastPosition = -1;

    private val items: MutableList<Movie> = mutableListOf()
    private val bannerItems: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_MOVIE -> {
                val binding = parent.binding<ItemMovieBinding>(R.layout.item_movie)
                return MovieViewHolder(binding).apply {
                    binding.root.setOnClickListener {
                        val position = bindingAdapterPosition.takeIf { pos -> pos != NO_POSITION }
                            ?: return@setOnClickListener
                        listener.onItemClicked(viewPagerLastPosition)
                    }
                }
            }
            else -> {
                val binding = parent.binding<LayoutViewpagerBinding>(R.layout.layout_viewpager)
                return ViewPagerViewHolder(binding)
            }
        }
    }

    fun setMovieList(movieList: List<Movie>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(movieList)
        val firstDummyItem = movieList[0]
        items.add(0, firstDummyItem)
        notifyItemRangeInserted(previousItemSize, items.size)
    }

    fun setBannerList(movieList: List<Movie>) {
        bannerItems.addAll(movieList)
        notifyItemChanged(0)
    }

    fun clearBannerItems() {
        viewPagerLastPosition = -1
        bannerItems.clear()
    }

    fun clearItems() {
        val itemSize = items.size
        items.clear()
        notifyItemRangeRemoved(0, itemSize)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewPagerViewHolder -> {
                holder.binding.apply {
                    layoutViewPagerViewPager.adapter = ViewPagerAdapter(this@MovieAdapter).apply {
                        this.setItems(bannerItems)
                        stateRestorationPolicy = StateRestorationPolicy.ALLOW
                    }
                    layoutViewPagerViewPager.registerOnPageChangeCallback(object :
                        ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            viewPagerLastPosition = position
                        }
                    })
                    layoutViewPagerIndicator.setupWithViewPager(layoutViewPagerViewPager)
                    if (viewPagerLastPosition != -1) {
                        layoutViewPagerViewPager.setCurrentItem(viewPagerLastPosition, false)
                    }
                }
            }

            is MovieViewHolder -> {
                holder.binding.apply {
                    movie = items[position]
                    executePendingBindings()
                }
            }


        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_BANNER
        } else {
            VIEW_TYPE_MOVIE
        }
    }

    fun setLastPositionOfViewPager(lastPositionOfViewpager: Int) {
        viewPagerLastPosition = lastPositionOfViewpager
    }

    class MovieViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ViewPagerViewHolder(val binding: LayoutViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onItemClicked(position: Int) {
        listener.onItemClicked(position)
    }

}
