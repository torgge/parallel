package com.bonespirito.parallel.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class ParallelListComparator<T> {
    /**
     * Highly optimized version for very large datasets where list1 >= list2
     * Uses optimal chunking and memory management
     */
    suspend fun findDifferencesHighPerformance(
        list1: List<T>, // Always larger or equal
        list2: List<T>, // Always smaller or equal
        keySelector: (T) -> Any,
        maxConcurrency: Int = Runtime.getRuntime().availableProcessors()
    ): List<T> = withContext(Dispatchers.Default) {

        // Pre-compute optimal chunk size based on list size and CPU cores
        val optimalChunkSize = maxOf(1000, list1.size / (maxConcurrency * 4))

        // Convert smaller list2 to Set - this is done only once
        val list2Keys = withContext(Dispatchers.Default) {
            list2.map(keySelector).toSet()
        }

        // Use semaphore to control memory usage and prevent thread explosion
        val semaphore = Semaphore(maxConcurrency)

        // Process in parallel with controlled concurrency
        list1.chunked(optimalChunkSize).map { chunk ->
            async {
                semaphore.withPermit {
                    // Use asSequence for memory efficiency on large chunks
                    chunk.asSequence()
                        .filter { item -> keySelector(item) !in list2Keys }
                        .toList()
                }
            }
        }.awaitAll().flatten()
    }
}
