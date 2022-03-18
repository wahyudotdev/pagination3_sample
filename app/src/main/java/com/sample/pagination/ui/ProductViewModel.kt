package com.sample.pagination.ui

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.crocodic.core.base.adapter.CorePagingSource
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.sample.pagination.data.repository.ProductRepository
import com.sample.pagination.data.repository.ProductRepositoryImpl
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first

class ProductViewModel: CoreViewModel() {
    override fun apiLogout(): Job {
        TODO("Not yet implemented")
    }

    override fun apiRenewToken(): Job {
        TODO("Not yet implemented")
    }

    private val repository: ProductRepository = ProductRepositoryImpl()

    fun productList() = Pager(CorePagingSource.config(), pagingSourceFactory = {
        CorePagingSource(1){ page, limit ->
            repository.getAllProduct(page, limit).first()
        }
    }).flow.cachedIn(viewModelScope)
}