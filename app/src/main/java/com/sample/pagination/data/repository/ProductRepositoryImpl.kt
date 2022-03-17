package com.sample.pagination.data.repository

import com.sample.pagination.data.api.ProductApi
import com.sample.pagination.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl: ProductRepository {

    val api = ProductApi()

    override fun getAllProduct(page: Int, limit: Int): Flow<List<Product>> = flow {
        val product = api.getProduct(page, limit)
        emit(product)
    }
}