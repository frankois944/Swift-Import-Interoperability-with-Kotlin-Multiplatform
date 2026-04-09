@file:OptIn(ExperimentalForeignApi::class)

package io.github.frankois944.swiftimportwithspmforkmpplayground

import io.github.frankois944.swiftimportwithspmforkmpplayground.util.toConcurrencyError
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.test.runTest
import playground.Concurrency
import playground.ConcurrencyError
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * This test class demonstrates the use case of importing Swift code from a Swift Package Manager (SPM)
 * dependency into a Kotlin Multiplatform (KMP) project for iOS testing, specifically focusing on
 * asynchronous operations and Swift Concurrency.
 */
class ConcurrencyTest {
    lateinit var concurrencyClass: Concurrency

    @BeforeTest
    fun setup() {
        concurrencyClass = Concurrency()
    }

    /**
     * This test demonstrates calling a basic Swift async method using Kotlin Coroutines by
     * wrapping the callback-based Swift API in a suspend function.
     */
    @Test
    fun doAsyncStuff() =
        runTest {
            suspendCoroutine { continuation ->
                concurrencyClass.doAsyncStuff {
                    continuation.resume(Unit)
                }
            }
        }

    /**
     * This test demonstrates calling a static Swift async method from Kotlin by wrapping
     * the callback-based Swift API in a suspend function.
     */
    @Test
    fun doAsyncStuffWithStaticMethod() =
        runTest {
            suspendCoroutine { continuation ->
                Concurrency.doAsyncWithStuffWithStaticMethod {
                    continuation.resume(Unit)
                }
            }
        }

    /**
     * This test demonstrates calling a Swift async method that returns a value to Kotlin
     * through a callback.
     */
    @Test
    fun doAsyncStuffWithValue() =
        runTest {
            val result: String? =
                suspendCoroutine { continuation ->
                    concurrencyClass.doAsyncStuffWithValue("France") {
                        continuation.resume(it)
                    }
                }
            assertEquals("Hello France!", result)
        }

    /**
     * This test demonstrates calling a Swift async method that can return either a value
     * or an error via a callback.
     */
    @Test
    fun doAsyncStuffWithValueAndNoError() =
        runTest {
            val result: String? =
                suspendCoroutine { continuation ->
                    concurrencyClass.doAsyncStuffWithValueAndError("France") { result, error ->
                        if (error != null) {
                            continuation.resumeWithException(SwiftException(error))
                        } else {
                            continuation.resume(result)
                        }
                    }
                }
            assertEquals("Hello France!", result)
        }

    /**
     * This test demonstrates error handling when calling a Swift async method that
     * provides an error via a callback.
     */
    @Test
    fun doAsyncStuffWithoutAsyncWithError() =
        runTest {
            var error: SwiftException? = null
            var result: String? = null
            try {
                result =
                    suspendCoroutine { continuation ->
                        concurrencyClass.doAsyncStuffWithoutAsync("") { result, error ->
                            if (error != null) {
                                continuation.resumeWithException(SwiftException(error))
                            } else {
                                continuation.resume(result)
                            }
                        }
                    }
            } catch (e: SwiftException) {
                error = e
            }
            assertNotNull(error)
            assertEquals(
                error.nsError.toConcurrencyError(),
                ConcurrencyError.ConcurrencyErrorInvalidData
            )
            assertEquals(null, result)
        }

    /**
     * This test demonstrates successful execution of a Swift async method that
     * provides a result via a callback without any error.
     */
    @Test
    fun doAsyncStuffWithoutAsyncWithoutError() =
        runTest {
            var error: SwiftException? = null
            var result: String? = null
            try {
                result =
                    suspendCoroutine { continuation ->
                        concurrencyClass.doAsyncStuffWithoutAsync("France") { value, error ->
                            if (error != null) {
                                continuation.resumeWithException(SwiftException(error))
                            } else {
                                continuation.resume(value)
                            }
                        }
                    }
            } catch (e: SwiftException) {
                error = e
            }
            assertEquals(null, error)
            assertEquals("Hello France!", result)
        }
}
