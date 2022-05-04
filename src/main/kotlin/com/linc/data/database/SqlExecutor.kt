package com.linc.data.database

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction

object SqlExecutor {
    suspend fun <T> executeQuery(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: () -> T
    ): Result<T?> = withContext(dispatcher) {
        try {
            Result.success(transaction { block() })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

}