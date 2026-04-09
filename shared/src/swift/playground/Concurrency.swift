import Foundation

/// Error enum for concurrency-related operations.
@objc public enum ConcurrencyError: Int, Error {
    case noError = -1
    case invalidData = 4503
    case taskCancel
}

@available(iOS 16.0.0, *)
@objcMembers public class Concurrency: NSObject {

    // workaround to export ConcurrencyError type to Kotlin
    public let bindConcurrencyErrorTypeToKotlin: ConcurrencyError = .noError

    /// A basic async function.
    @objc(doAsyncStuff:)
    public func doAsyncStuff() async {
        try? await Task.sleep(for: .seconds(0.5))
        print("doAyncStuff")
    }

    /// A static async function.
    @objc(doAsyncWithStuffWithStaticMethod:)
    public static func doAsyncWithStuffWithStaticMethod() async {
        try? await Task.sleep(for: .seconds(0.5))
        print("doAsyncWithStuffWithStaticMethod")
    }

    /// An async function that returns a value.
    /// - Parameter input: Input string.
    /// - Returns: A processed greeting string.
    @objc(doAsyncStuffWithValue::)
    public func doAsyncStuffWithValue(input: String) async -> String {
        try? await Task.sleep(for: .seconds(0.5))
        return "Hello \(input)!"
    }

    /// An async function that can throw an error and returns a value.
    /// - Parameter input: Input string.
    /// - Returns: A processed greeting string.
    /// - Throws: `ConcurrencyError.invalidData` if input is empty.
    @objc(doAsyncStuffWithValueAndError::)
    public func doAsyncStuffWithValueAndError(input: String) async throws -> String {
        try? await Task.sleep(for: .seconds(0.5))
        if input.isEmpty {
            throw ConcurrencyError.invalidData
        }
        return "Hello \(input)!"
    }

    /// An example of handling concurrency without the `async` keyword on the signature, using a completion handler.
    /// - Parameters:
    ///   - input: Input string.
    ///   - onResult: A closure called when the operation finishes, providing the result or an error.
    @MainActor
    @objc(doAsyncStuffWithoutAsync::)
    public func doAsyncStuffWithoutAsync(input: String, onResult: @escaping @Sendable (String?, NSError?) -> Void) {
        DispatchQueue.global().async {
            if input.isEmpty {
                onResult(
                    nil,
                    .init(
                        domain: "\(ConcurrencyError.invalidData)",
                        code: ConcurrencyError.invalidData.rawValue))
            } else {
                onResult("Hello \(input)!", nil)
            }
        }
    }
}
