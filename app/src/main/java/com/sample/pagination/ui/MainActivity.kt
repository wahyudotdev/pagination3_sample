package com.sample.pagination.ui

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.base.adapter.PaginationAdapter
import com.crocodic.core.base.adapter.PaginationLoadState
import com.sample.pagination.R
import com.sample.pagination.data.model.Product
import com.sample.pagination.databinding.ActivityMainBinding
import com.sample.pagination.databinding.ItemProductBinding
import com.sample.pagination.databinding.StateLoadingCustomBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : CoreActivity<ActivityMainBinding, ProductViewModel>(R.layout.activity_main) {

    private val adapter = PaginationAdapter<ItemProductBinding, Product>(R.layout.item_product)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        with(adapter) {
            // Create footer loading
            val loadStateFooter = PaginationLoadState<StateLoadingCustomBinding>(R.layout.state_loading_custom)
            binding.rvProduct.adapter = withLoadStateFooter(loadStateFooter)

            // Or just use default loading view
            // binding.rvProduct.adapter = withLoadStateFooter(PaginationLoadState.default)
        }
        lifecycleScope.launch {
            launch {
                viewModel.productList().collect {
                    adapter.submitData(it)
                }
            }
            launch {
                adapter.loadStateFlow.collectLatest {
                    // Only show parent layout loading on first loading
                    val loading = it.append == LoadState.Loading || it.refresh == LoadState.Loading
                    binding.progressCircular.isVisible = loading && adapter.snapshot().isEmpty()
                }
            }
        }
    }
}