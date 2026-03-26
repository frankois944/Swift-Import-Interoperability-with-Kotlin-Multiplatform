@file:OptIn(ExperimentalForeignApi::class)

package io.github.frankois944.swiftimportwithspmforkmpplayground

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSNumber
import playground.Basic
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * This test class demonstrates the use case of importing Swift code from a Swift Package Manager (SPM)
 * dependency into a Kotlin Multiplatform (KMP) project for iOS testing.
 */
class BasicTest {
    lateinit var basicClass: Basic

    @BeforeTest
    fun setup() {
        basicClass = Basic()
    }

    /**
     * This test demonstrates that the Swift class 'Basic' can be instantiated with a custom
     * initializer from Kotlin code.
     */
    @Test
    fun customInit() {
        val customBasic = Basic(someValue = 41244)
        assertEquals(41244, customBasic.mutableProperty())
        assertEquals(41244, customBasic.readOnlyProperty())
    }

    /**
     * This test demonstrates calling a basic method on a Swift class instance from Kotlin.
     */
    @Test
    fun method() {
        basicClass.method()
    }

    /**
     * This test demonstrates calling a static method on a Swift class from Kotlin.
     */
    @Test
    fun staticMethod() {
        Basic.staticMethod()
    }

    /**
     * This test demonstrates calling a class method on a Swift class from Kotlin.
     */
    @Test
    fun classMethod() {
        Basic.classMethod()
    }

    /**
     * This test demonstrates that Swift methods with custom Objective-C names can be
     * called from Kotlin using the overridden name.
     */
    @Test
    fun overrideMethodName() {
        assertEquals("HelloWorld!", basicClass.methodWithNameOverride())
    }

    /**
     * This test demonstrates calling a Swift method with a parameter from Kotlin.
     */
    @Test
    fun methodWithParam() {
        assertEquals("Hello France!", basicClass.methodWithParam("France"))
    }

    /**
     * This test demonstrates accessing a read-only property of a Swift class from Kotlin.
     */
    @Test
    fun readOnlyProperty() {
        assertEquals(42, basicClass.readOnlyProperty())
    }

    /**
     * This test demonstrates accessing and modifying a mutable property of a Swift class from Kotlin.
     */
    @Test
    fun mutableProperty() {
        assertEquals(42, basicClass.mutableProperty())
        basicClass.setMutableProperty(200)
        assertEquals(200, basicClass.mutableProperty())
    }

    /**
     * This test demonstrates passing a NSNumber from Kotlin to a Swift method that expects a Number.
     */
    @Test
    fun numberMethod() {
        assertEquals("Hello 42!", basicClass.methodNumber(NSNumber(42)))
    }

    /**
     * This test demonstrates calling a Swift method with an optional parameter, passing null from Kotlin.
     */
    @Test
    fun optionalMethod() {
        assertEquals("Hello nil!", basicClass.methodOptional(null))
    }
}
