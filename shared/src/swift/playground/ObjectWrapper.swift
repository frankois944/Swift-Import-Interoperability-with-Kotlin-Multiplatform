import Foundation

/// This file demonstrates how to wrap pure Swift types into Objective-C compatible classes
/// for interoperability with Kotlin/Native in Kotlin Multiplatform projects.
/// Kotlin/Native can import and use Objective-C classes directly, enabling seamless integration.

/// A pure Swift struct representing native content that cannot be directly accessed from Kotlin.
/// This could contain Swift-only APIs, third-party libraries, or platform-specific code.
struct NativeContent {
    var message: String
    // and other swift code that are not wrappable, could be CoreData or CoreNFC classes, or any third party library that are not ObjC compatible.
}

/// An Objective-C compatible wrapper class that bridges the native Swift struct to Kotlin.
/// The @objcMembers attribute exposes all members to Objective-C, and 'public' makes it accessible
/// to other modules. Inheriting from NSObject provides the necessary Objective-C runtime support.
@objcMembers public class BridgedNativeContent: NSObject {
    // nativeObject could be a native instance of a third party library.
    var nativeObject: NativeContent

    init(nativeObject: NativeContent) {
        self.nativeObject = nativeObject
        super.init()
    }

    /// Computed property that exposes the native message through an Objective-C compatible interface.
    /// The @objc attribute ensures this property is visible to the Objective-C runtime and thus to Kotlin.
    @objc public var stringValue: String {
        get {
            nativeObject.message
        }
        set {
            nativeObject.message = newValue
        }
    }
}

/// A utility class that provides static methods to create and manipulate bridged instances.
/// This serves as the entry point for Kotlin code to interact with the wrapped Swift functionality.
@objcMembers public class ObjectWrapper: NSObject {

    /// Creates and returns a new instance of the bridged native content.
    /// The @objc attribute with a custom name ensures the method is properly exposed to Objective-C.
    @objc(getBridgedNativeClass)
    public static func getBridgedNativeClass() -> BridgedNativeContent {
        BridgedNativeContent(nativeObject: .init(message: "Hello world!"))
    }

    /// Performs a native Swift operation on the bridged instance.
    /// This demonstrates how Swift code can directly manipulate the wrapped native object.
    @objc(callNativeAPI:)
    public static func callNativeAPI(instance: BridgedNativeContent) {
        instance.nativeObject.message = String(instance.nativeObject.message.prefix(5))
    }
}