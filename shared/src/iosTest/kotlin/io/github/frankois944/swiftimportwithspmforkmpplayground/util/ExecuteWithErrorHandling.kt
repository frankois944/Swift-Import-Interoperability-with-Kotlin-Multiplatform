package io.github.frankois944.swiftimportwithspmforkmpplayground

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSError

/**
 * Custom exception that wraps a Swift NSError for use in Kotlin.
 */
class SwiftException(
    val nsError: NSError,
) : Exception(nsError.localizedDescription)

/**
 * Helper function to execute a Swift operation that uses the Objective-C error pointer pattern.
 * If the Swift operation results in an error, it throws a [SwiftException] wrapping the [NSError].
 */
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Throws(SwiftException::class)
fun <T> executeWithErrorHandling(operation: (errorPtr: CPointer<ObjCObjectVar<NSError?>>) -> T): T {
    memScoped {
        val errorPtr: CPointer<ObjCObjectVar<NSError?>> = alloc<ObjCObjectVar<NSError?>>().ptr
        val result: T = operation(errorPtr)
        val error: NSError? = errorPtr.pointed.value
        if (error != null) {
            throw SwiftException(error)
        }
        return result
    }
}
