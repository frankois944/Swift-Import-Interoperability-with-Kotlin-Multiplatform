# Swift-Import Interoperability with Kotlin Multiplatform

This project is a playground demonstrating how to import Swift code from Swift Package Manager (SPM) into a Kotlin Multiplatform (KMP) project using the [spmforkmp](https://spmforkmp.eu/) plugin.

It provides practical examples of Swift-Kotlin interoperability, specifically for iOS testing and development.

## Features and Examples

The project includes several test files, each focusing on a specific aspect of Swift-Kotlin integration:

### 1. Basic Interoperability ([`Basic.swift`](shared/src/swift/playground/Basic.swift) & [`BasicTest.kt`](shared/src/iosTest/kotlin/io/github/frankois944/swiftimportwithspmforkmpplayground/BasicTest.kt))
- **Class Instantiation**: Creating Swift class instances from Kotlin.
- **Methods**: Calling instance, static, and class methods.
- **Properties**: Accessing and modifying read-only and mutable properties.
- **Objective-C Name Overrides**: Using Swift methods with custom `@objc` names.
- **Data Types**: Passing standard types like `NSNumber` and handling optional values (`nil`).

### 2. Swift Concurrency ([`Concurrency.swift`](shared/src/swift/playground/Concurrency.swift) & [`ConcurrencyTest.kt`](shared/src/iosTest/kotlin/io/github/frankois944/swiftimportwithspmforkmpplayground/ConcurrencyTest.kt))
- **Async/Await**: Calling Swift `async` methods from Kotlin.
- **Coroutines Integration**: Wrapping callback-based Swift APIs into Kotlin `suspend` functions using `suspendCoroutine`.
- **Error Handling**: Managing errors returned via Swift callbacks.

### 3. Enums and Type Safety ([`Enums.swift`](shared/src/swift/playground/Enums.swift) & [`EnumTest.kt`](shared/src/iosTest/kotlin/io/github/frankois944/swiftimportwithspmforkmpplayground/EnumTest.kt))
- **Swift Enums**: Importing and using Swift enums in Kotlin.
- **Type-Safe Enums**: Demonstrating the use of `strictEnums` configuration from the `spmforkmp` plugin to make imported enums type-safe in Kotlin.

### 4. Error Handling and Exceptions ([`Throws.swift`](shared/src/swift/playground/Throws.swift), [`ThrowTest.kt`](shared/src/iosTest/kotlin/io/github/frankois944/swiftimportwithspmforkmpplayground/ThrowTest.kt)
- **Handling Throws**: Intercepting Swift errors (`throws`) in Kotlin.

## Error Management Helper

The project includes a utility function, [`executeWithErrorHandling`](shared/src/iosTest/kotlin/io/github/frankois944/swiftimportwithspmforkmpplayground/util/ExecuteWithErrorHandling.kt), designed to simplify error management when calling Swift methods that follow the Objective-C error pointer pattern.

### Why it helps:

- **Idiomatic Kotlin**: It transforms the lower-level Objective-C `NSError**` pointer pattern into idiomatic Kotlin exceptions (`SwiftException`).
- **Reduces Boilerplate**: You don't need to manually manage `memScoped`, allocate an error pointer, and check if it's `null` every time you call a throwing Swift function.
- **Type-Safe Exceptions**: It provides a consistent `SwiftException` type that wraps the underlying `NSError`, giving you easy access to its `localizedDescription` and other properties.
- **Better Debugging**: Instead of having to inspect an error pointer after each call, you can use standard Kotlin `try-catch` blocks, making the code cleaner and easier to maintain.

Example usage:
```kotlin
val result = executeWithErrorHandling { errorPtr ->
    swiftInstance.throwingMethod(errorPtr)
}
```

## Configuration

The project uses the `spmforkmp` plugin, configured in `shared/build.gradle.kts`. Key configuration highlights include:

- **Swift Package Configuration**: Defining the Swift version and name for the generated cinterop.
  - `toolsVersion = "6.0"`: Specifies the Swift tools version to use. This enables advanced features like typed throws and strict concurrency.
- **Compiler Arguments**: A requirement for iOS simulator testing (see [Concurrency Support in KMP iOS Test](https://spmforkmp.eu/section-help/tips/#support-concurrency-in-kmp-ios-test)):
  ```kotlin
  freeCompilerArgs += listOf("-Xoverride-konan-properties=osVersionMin.ios_simulator_arm64=16.0")
  ```
- **Strict Enums**: Specifying which Swift enums should be treated as type-safe enums in Kotlin (see [Strict Enums Documentation](https://spmforkmp.eu/references/swiftPackageConfig/#strictenums)):
  ```kotlin
  strictEnums = listOf("EnumData", "EnumError", "ConcurrencyError", "ThrowError")
  ```

## Getting Started

To run the tests, you will need a Mac with Xcode and Kotlin Multiplatform setup.

1.  Clone the repository.
2.  Open the project in IntelliJ IDEA or Android Studio.
3.  Run the iOS tests from the `shared` module.

## Feedback and Requests

If you have any feedback, encounter issues, or want to request new examples, please feel free to open an issue or a discussion on the [GitHub repository](https://github.com/frankois944/SwiftImportWithSpmForKmpPlayground).

## Resources

- [spmforkmp Documentation](https://spmforkmp.eu/)
- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
