@file:OptIn(ExperimentalForeignApi::class)

package io.github.frankois944.swiftimportwithspmforkmpplayground.util

import kotlinx.cinterop.CEnum
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import playground.ConcurrencyError
import playground.ThrowError

fun NSError.toThrowError() : ThrowError {
    return ThrowError.entries.first {
        it.value == this.code
    }
}

fun NSError.toConcurrencyError() : ConcurrencyError {
    return ConcurrencyError.entries.first {
        it.value == this.code
    }
}
