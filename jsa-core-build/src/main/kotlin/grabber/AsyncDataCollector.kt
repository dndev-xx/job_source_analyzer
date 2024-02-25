package org.example.grabber

import jsa.mapper.model.HHModelApi
import jsa.mapper.model.HabrModelApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AsyncDataCollector {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    suspend fun collectAndSendData(sourceHH: String, sourceHABR: String, topic: String) {
        val loadingJob = coroutineScope.launch {
            var counter = 0
            while (coroutineContext.isActive) {
                repeat(3) {
                    print("\rLoading${".".repeat(counter % 4)}  ")
                    delay(500)
                    counter++
                }
            }
        }
        val hhData = withContext(Dispatchers.IO) {
            fetchDataFromSourceHH(sourceHH)
        }
        val habrData = withContext(Dispatchers.IO) {
            fetchDataFromSourceHABR(sourceHABR)
        }
        loadingJob.cancelAndJoin()
        println("\nData loaded:")
        mergeData(hhData, habrData)
    }

    private fun mergeData(hhData: HHModelApi?, habrData: HabrModelApi) {
        println(hhData?.items?.size)
        println(habrData.items.size)
    }
}