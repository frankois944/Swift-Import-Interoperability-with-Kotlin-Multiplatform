package io.github.frankois944.swiftimportwithspmforkmpplayground

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
