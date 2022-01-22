package com.ahmetbabacan.movies.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ahmetbabacan.movies.R
import com.ahmetbabacan.movies.databinding.FragmentDetailBinding
import com.ahmetbabacan.movies.util.Constants.imdbUrl
import com.ahmetbabacan.movies.util.toMovieDetailModel
import com.skydoves.bindables.BindingFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : BindingFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val vm: DetailViewModel by viewModels()

    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@DetailFragment
            viewModel = vm
            movie = args.movie.toMovieDetailModel()
        }.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.detailResponse.observe(viewLifecycleOwner, { response ->
            binding.fragmentDetailIvImdb.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(imdbUrl + response.imdb_id)
                    )
                )
            }
        })
    }


}