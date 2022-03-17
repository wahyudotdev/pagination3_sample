package com.sample.pagination.data.api

import com.sample.pagination.data.model.Product
import kotlinx.coroutines.delay

class ProductApi {

    // First product is in page 1
    suspend fun getProduct(page: Int, limit: Int): List<Product> {
        delay(1000)
        val products = ArrayList<Product>()
        val offset = (page * limit) - limit
        for (i in offset..(offset.plus(limit))){
            products.add(Product(name = "Product $i", price = (i * 1000.0).toString()))
        }
        return products
    }

}