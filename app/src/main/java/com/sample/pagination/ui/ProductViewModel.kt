package com.sample.pagination.ui

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.crocodic.core.base.adapter.CorePagingSource
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.sample.pagination.data.repository.ProductRepository
import com.sample.pagination.data.repository.ProductRepositoryImpl
import kotlinx.coroutines.flow.first

class ProductViewModel : CoreViewModel() {

    private val repository: ProductRepository = ProductRepositoryImpl()
    private val firstPage = 1
    private val itemPerPage = 10

    fun productList() = Pager(CorePagingSource.config(itemPerPage), pagingSourceFactory = {
        CorePagingSource(firstPage) { page, limit ->
            repository.getAllProduct(page, limit).first() // List<Product>
        }
    }).flow.cachedIn(viewModelScope)

    override fun apiLogout() {}

    override fun apiRenewToken() {}
}