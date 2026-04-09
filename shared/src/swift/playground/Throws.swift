import Foundation

/**
 * Enums interaction between Swift and Kotlin through @objc
 * This example shows how to declare an error enum and use it in throwing methods.
 */

/// Error enum for throwing tests.
/// Conforming to `Error` and being `@objc` allows it to be bridged to Kotlin.
@objc public enum ThrowError: Int, Error {
    case noError, error1, error2
}

@objcMembers public class Throw: NSObject {

    // workaround to export ThrowError type to Kotlin
    public let bindThrowErrorTypeToKotlin: ThrowError = .noError

    /// A method that simply throws an error using untyped `throws`.
    /// This will be bridged to Kotlin as a method that can throw a generic `NSError`.
    /// - Throws: `ThrowError.error1` always.
    @objc(justThrowAnError:)
    public func justThrowAnError() throws {
        throw ThrowError.error1
    }

    /// A method with a parameter that can throw an error and return a value using typed `throws`.
    /// This allows the compiler to know the exact error type.
    /// - Parameter input: The string to process.
    /// - Returns: A greeting string if successful.
    /// - Throws: `ThrowError.error2` if the input is empty.
    @objc(throwAnErrorWithParamAndReturn::)
    public func throwAnErrorWithParamAndReturn(input: String) throws -> String {
        if input.isEmpty {
            throw ThrowError.error2
        } else {
            return "Hello \(input)"
        }
    }
}
