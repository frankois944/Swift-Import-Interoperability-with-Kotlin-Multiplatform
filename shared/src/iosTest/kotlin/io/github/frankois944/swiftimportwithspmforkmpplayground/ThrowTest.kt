@file:OptIn(ExperimentalForeignApi::class)

package io.github.frankois944.swiftimportwithspmforkmpplayground

import io.github.frankois944.swiftimportwithspmforkmpplayground.util.toThrowError
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import playground.Throw
import playground.ThrowError
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull



/**
 * This test class demonstrates the use case of importing Swift code that throws errors from a
 * Swift Package Manager (SPM) dependency into a Kotlin Multiplatform (KMP) project for iOS testing.
 */
class ThrowTest {
    lateinit var throwClass: Throw

    @BeforeTest
    fun setup() {
        throwClass = Throw()
    }

    /**
     * This test demonstrates how to handle a Swift method that throws an error from Kotlin
     * using a helper for Objective-C error handling.
     */
    @Test
    fun justThrowAnError() {
        var error: SwiftException? = null
        try {
            executeWithErrorHandling {
                throwClass.justThrowAnError(it)
            }
        } catch (ex: SwiftException) {
            error = ex
        }
        assertNotNull(error)
        assertEquals(
            error.nsError.code,
            ThrowError.ThrowErrorError1.value,
        )
    }

    /**
     * This test demonstrates handling a Swift method that takes a parameter and throws an
     * error, specifically verifying the error code returned to Kotlin.
     */
    @Test
    fun throwAnErrorWithParamAndReturnWithError() {
        var error: SwiftException? = null
        var value: String? = null
        try {
            value = executeWithErrorHandling {
                throwClass.throwAnErrorWithParamAndReturn("", it)
            }
        } catch (ex: SwiftException) {
            error = ex
        }
        assertNull(value)
        assertNotNull(error)
        assertEquals(
            error.nsError.toThrowError(),
            ThrowError.ThrowErrorError2,
        )
    }

    /**
     * This test demonstrates handling a Swift method that takes a parameter and returns a value,
     * specifically verifying the value returned to Kotlin.
     */
    @Test
    fun throwAnErrorWithParamAndReturnValue() {
        var error: SwiftException? = null
        var value: String? = null
        try {
            value = executeWithErrorHandling {
                throwClass.throwAnErrorWithParamAndReturn("France", it)
            }
        } catch (ex: SwiftException) {
            error = ex
        }
        assertNull(error)
        assertEquals(
            "Hello France",
            value,
        )
    }
}
