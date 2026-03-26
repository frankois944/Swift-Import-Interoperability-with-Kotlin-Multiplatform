@file:OptIn(ExperimentalForeignApi::class)

package io.github.frankois944.swiftimportwithspmforkmpplayground

import kotlinx.cinterop.ExperimentalForeignApi
import playground.DefaultEnumDefaultValue1
import playground.EnumData
import playground.EnumError
import playground.Enums
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * This test class demonstrates the use case of importing Swift Enums from a Swift Package Manager (SPM)
 * dependency into a Kotlin Multiplatform (KMP) project for iOS testing.
 * To make imported enums typesafe, use [strictenums](https://spmforkmp.eu/references/swiftPackageConfig/#strictenums)
 */
class EnumTest {
    lateinit var enumsClass: Enums

    var myEnum: EnumData = EnumData.EnumDataValue2

    @BeforeTest
    fun setup() {
        enumsClass = Enums()
    }

    /**
     * This test demonstrates accessing and modifying a Swift enum property from Kotlin.
     */
    @Test
    fun testEnumMutable() {
        assertEquals(EnumData.EnumDataValue2, myEnum)
        assertEquals(EnumData.EnumDataValue1, enumsClass.myEnum())
        enumsClass.setMyEnum(EnumData.EnumDataValue3)
        assertEquals(EnumData.EnumDataValue3, enumsClass.myEnum())
    }

    /**
     * This test demonstrates the use of a typesafe Swift enum representing an error in Kotlin.
     */
    @Test
    fun testEnumError() {
        // typesafe enum
        assertEquals(EnumError.EnumErrorNoError, enumsClass.doSomething())
    }

    /**
     * This test demonstrates the use of a non-typesafe Swift enum from Kotlin.
     */
    @Test
    fun testDefaultEnum() {
        // non-typesafe enum
        assertEquals(0, DefaultEnumDefaultValue1)
    }
}
