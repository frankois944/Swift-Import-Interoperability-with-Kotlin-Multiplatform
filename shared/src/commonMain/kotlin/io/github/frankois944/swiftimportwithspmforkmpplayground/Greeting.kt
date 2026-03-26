package io.github.frankois944.swiftimportwithspmforkmpplayground

class Greeting {
    private val platform = getPlatform()

    fun greet(): String = "Hello, ${platform.name}!"
}
