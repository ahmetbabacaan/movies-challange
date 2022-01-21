package com.ahmetbabacan.movies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.ahmetbabacan.movies.R
import com.ahmetbabacan.movies.databinding.FragmentHomeBinding
import com.ahmetbabacan.movies.ui.adapters.MovieAdapter
import com.ahmetbabacan.movies.util.Constants.argKeyBannerScrollPosition
import com.skydoves.bindables.BindingFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home),
    ViewPagerAdapter.ItemClickedListener {

    private val vm: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@HomeFragment
            viewModel = vm
            adapter = MovieAdapter(this@HomeFragment)
            fragmentHomeRvMovies.setHasFixedSize(true)
            fragmentHomeSwipeRefreshLayout.setOnRefreshListener {
                (fragmentHomeRvMovies.adapter as MovieAdapter).clearBannerItems()
                vm.refreshData();
                arguments = null
            }
            fragmentHomeRvMovies.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )

        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.upcomingResponse.observe(viewLifecycleOwner, { response ->
            response.results?.let { list ->
                with(binding) {
                    arguments?.let {
                        (fragmentHomeRvMovies.adapter as MovieAdapter).setLastPositionOfViewPager(
                            it[argKeyBannerScrollPosition] as Int
                        )
                    }
                    (fragmentHomeRvMovies.adapter as MovieAdapter).setBannerList(list)
                }
            }
            with(binding) {
                if (fragmentHomeSwipeRefreshLayout.isRefreshing) {
                    fragmentHomeSwipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }

    override fun onItemClicked(position: Int) {
        arguments = Bundle().apply {
            putInt(argKeyBannerScrollPosition, position)
        }
    }

}