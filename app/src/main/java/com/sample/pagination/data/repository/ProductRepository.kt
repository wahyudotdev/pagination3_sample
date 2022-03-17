package com.sample.pagination.data.repository

import com.sample.pagination.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProduct(page: Int, limit: Int): Flow<List<Product>>
}