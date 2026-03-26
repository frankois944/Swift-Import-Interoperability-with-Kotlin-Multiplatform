import Foundation

/**
 * Enums interaction between Swift and Kotlin through @objc
 * To make an enum accessible from Kotlin, they must be declared inside a public class like the following example.
 */


/// Only enum of type `Int` is supported
/// Example of an enum with specific raw values.
@objc public enum EnumData: Int {
    case value1, value2
    case value3 = 42
}

/// Example of an enum conforming to `Error` for usage with `throws`.
@objc public enum EnumError: Int, Error {
    case noError, error1
    case error2 = 42
}

/// Example of a default enum with implicit values.
@objc public enum DefaultEnum: Int {
    case defaultValue1, defaultValue2
}

@objcMembers public class Enums: NSObject {

    /// A property using the custom `EnumData` enum.
    public var myEnum: EnumData = .value1

    /// A constant using the `DefaultEnum` enum.
    public let defaultEnum: DefaultEnum = .defaultValue1

    /// A method returning an `EnumError`.
    /// - Returns: The default success error case.
    public func doSomething() -> EnumError {
        return .noError
    }
}
