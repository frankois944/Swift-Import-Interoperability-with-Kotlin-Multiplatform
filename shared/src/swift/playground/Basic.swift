import Foundation

/**
 Basic interaction between Swift and Kotlin through @objc
 */

@objcMembers public class Basic: NSObject {

    /// A read-only property exposed to Kotlin.
    public let readOnlyProperty: Int
    /// A mutable property exposed to Kotlin with a default value.
    public var mutableProperty: Int = 42

    /// Initializes the class with a given value.
    /// - Parameter someValue: The value to set for both properties.
    public init(someValue: Int) {
        mutableProperty = someValue
        readOnlyProperty = someValue
        super.init()
        print("init with someValue \(someValue)")
    }

    /// Default initializer setting properties to default values.
    public override init() {
        readOnlyProperty = 42
        super.init()
        print("init")
    }

    deinit {
        print("deinit")
    }

    /// A simple instance method.
    public func method() {
        print("method")
    }

    /// A static method (cannot be overridden).
    public static func staticMethod() {
        print("staticMethod")
    }

    /// A class method (can be overridden in subclasses).
    public class func classMethod() {
        print("classMethod")
    }

    /// An instance method with an explicit `@objc` name override.
    /// - Returns: A greeting string.
    @objc(methodWithNameOverride)
    public func method() -> String {
        return "HelloWorld!"
    }

    /// An instance method with a parameter, exposed to Kotlin.
    /// - Parameter param: A string parameter.
    /// - Returns: A personalized greeting string.
    // @objc(methodWithNameOverride:)
    public func method(param: String) -> String {
        return "Hello \(param)!"
    }

    @objc(methodNumber:)
    public func methodNumber(param: NSNumber) -> String {
        return "Hello \(param)!"
    }

    @objc(methodOptional:)
    public func methodOptional(param: String?) -> String? {
        return "Hello \(String(describing: param))!"
    }

}
