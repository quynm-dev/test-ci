package com.ktor.skeleton.helper

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> wrapperTransaction(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }