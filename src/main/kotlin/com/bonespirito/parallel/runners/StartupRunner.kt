package com.bonespirito.parallel.runners

import com.bonespirito.parallel.utils.Utils
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class StartupRunner(
    private val parallelUtils : Utils,
    @Value("\${app.config.list-one-size}")
    private val listOneSize: Int,
    @Value("\${app.config.list-two-size}")
    private val listTwoSize: Int
) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(StartupRunner::class.java)

    override fun run(vararg args: String?) {
        val listOne = parallelUtils.produceProductList(listOneSize)
        val listTwo = parallelUtils.produceProductList(listTwoSize)


        logger.info("Start no performed test with list sizes: List One = $listOneSize, List Two = $listTwoSize")
        val resultList = parallelUtils.getProductDifference(
            listOne
            , listTwo
        )
        logger.info("End no performance test")

        logger.info("Start performed test with list sizes: List One = $listOneSize, List Two = $listTwoSize")
        val resultList2 = runBlocking {
            parallelUtils.getProductDifferencePerformed(
                listOne,
                listTwo
            )
        }
        logger.info("End no performance test with result size: ${resultList2.size}")
    }
}
