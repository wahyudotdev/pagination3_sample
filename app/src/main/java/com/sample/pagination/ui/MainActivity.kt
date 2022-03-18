package com.sample.pagination.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.base.adapter.PaginationAdapter
import com.sample.pagination.data.model.Product
import com.sample.pagination.R
import com.sample.pagination.databinding.ActivityMainBinding
import com.sample.pagination.databinding.ItemProductBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : CoreActivity<ActivityMainBinding, ProductViewModel>(R.layout.activity_main) {

    private val adapter = PaginationAdapter<ItemProductBinding, Product>(R.layout.item_product)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.rvProduct.adapter = adapter
        adapter.initItem { _, data ->
            Toast.makeText(this, data.name, Toast.LENGTH_SHORT).show()
        }
        lifecycleScope.launch {
            launch {
                viewModel.productList().collect {
                    adapter.submitData(it)
                }
            }
            launch {
                adapter.loadStateFlow.collectLatest {
                    binding.progressCircular.isVisible =
                        it.refresh == LoadState.Loading || it.append == LoadState.Loading
                }
            }
        }
    }
}