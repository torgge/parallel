package com.bonespirito.parallel.utils

import com.bonespirito.parallel.domain.ProductDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Utils {
    private val logger = LoggerFactory.getLogger(Utils::class.java)

    fun getProductDifference(
        productList1: List<ProductDto>,
        productList2: List<ProductDto>
    ): List<ProductDto> {
        val differences: List<ProductDto>
        val time = measureTimeMillis {
            logger.info("List One size: ${productList1.size}, List Two size: ${productList2.size}")
            differences = productList1.parallelStream().filter { it !in productList2 }.toList()
        }
        logger.info("getProductDifference executed in $time ms")
        return differences
    }

    suspend fun getProductDifferencePerformed(
        productList1: List<ProductDto>,
        productList2: List<ProductDto>
    ): List<ProductDto> {
        val differences: List<ProductDto>
        logger.info("List One size: ${productList1.size}, List Two size: ${productList2.size}")
        val time = measureTimeMillis {
            if (productList1.size < productList2.size) {
                throw IllegalArgumentException("productList1 must be larger or equal to productList2")
            }
            differences = productList1.findDifferencesFrom(productList2) { it.skuCode }

            println("Items only in larger list: ${differences.size}")
            println("Expected: ${productList1.size - productList2.size}")
        }
        logger.info("getProductDifference executed $time ms")
        return differences
    }


    fun produceProductList(
        size: Int
    ): List<ProductDto> {
        return (1..size).map { ProductDto("SKU$it", "BOT") }
    }

    // Extension function for easier usage - optimized for list1 >= list2
    suspend fun <T> List<T>.findDifferencesFrom(
        smallerList: List<T>,
        keySelector: (T) -> Any
    ): List<T> {
        val comparator = ParallelListComparator<T>()
        return comparator.findDifferencesHighPerformance(this, smallerList, keySelector)
    }

}
