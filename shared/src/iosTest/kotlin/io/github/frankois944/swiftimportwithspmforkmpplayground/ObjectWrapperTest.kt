package io.github.frankois944.swiftimportwithspmforkmpplayground

import kotlinx.cinterop.ExperimentalForeignApi
import playground.ObjectWrapper
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalForeignApi::class)
/**
 * Test class demonstrating how Swift native classes are imported and wrapped for use in Kotlin/Native.
 *
 * In this example:
 * - NativeContent is a pure Swift struct that cannot be directly accessed from Kotlin.
 * - BridgedNativeContent wraps NativeContent in an Objective-C compatible class (@objcMembers),
 *   allowing Kotlin/Native to import and interact with it seamlessly.
 * - ObjectWrapper provides static methods to create and manipulate bridged instances.
 *
 * This showcases the interoperability between Swift and Kotlin in multiplatform projects,
 * where Swift Package Manager (SPM) integration enables direct import of Swift code.
 */
class ObjectWrapperTest {

    @Test
    fun wrapObjectTest() {
        // Import the Swift classes via SPM integration; ObjectWrapper and BridgedNativeContent are Swift classes
        // Get a wrapped instance: BridgedNativeContent containing a NativeContent (pure Swift struct)
        val wrappedObject = ObjectWrapper.getBridgedNativeClass()
        // Access the wrapped Swift object's properties through the bridged Objective-C compatible interface
        assertEquals("Hello world!", wrappedObject.stringValue())
        // Modify the underlying NativeContent via the bridged setter
        wrappedObject.setStringValue("Hello me!")
        // Confirm the change was applied to the wrapped object
        assertEquals("Hello me!", wrappedObject.stringValue())
        // Call a native Swift API that modifies the wrapped NativeContent directly
        ObjectWrapper.callNativeAPI(wrappedObject)
        // Verify the native API call affected the object's state
        assertEquals("Hello", wrappedObject.stringValue())
    }
}