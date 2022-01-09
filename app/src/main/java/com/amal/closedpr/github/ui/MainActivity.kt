package com.amal.closedpr.github.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.amal.closedpr.databinding.ActivityMainBinding
import com.amal.closedpr.github.api.GithubAPI
import com.amal.closedpr.github.api.RetrofitHelper
import com.amal.closedpr.github.ui.list.GithubPRAdapter
import com.amal.closedpr.github.ui.list.GithubPRLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
        private set

    private val prViewModel: PrViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                val githubApi = RetrofitHelper.getInstance().create(GithubAPI::class.java)

                @Suppress("UNCHECKED_CAST")
                return PrViewModel(githubApi) as T
            }
        }
    }

    private lateinit var paginatedAdapter: GithubPRAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPRList()
        initSwipeToRefresh()
    }

    private fun initPRList() {
        paginatedAdapter = GithubPRAdapter()
        binding.rvPrs.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = paginatedAdapter.withRefreshHeaderAndAppendFooter(
                header = GithubPRLoadStateAdapter(paginatedAdapter),
                footer = GithubPRLoadStateAdapter(paginatedAdapter)
            )
        }
        lifecycleScope.launchWhenCreated {
            prViewModel.closedPRs.collectLatest {
                paginatedAdapter.submitData(it)
            }

        }

        lifecycleScope.launchWhenCreated {
            paginatedAdapter.loadStateFlow.collect { loadStates ->
                binding.swiperefresh.isRefreshing = loadStates.mediator?.refresh is LoadState.Loading
            }
        }
    }

    private fun initSwipeToRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            paginatedAdapter.refresh()
        }
    }
}
