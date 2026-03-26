import io.github.frankois944.spmForKmp.swiftPackageConfig
import kotlin.apply
import kotlin.collections.plus
import kotlin.collections.plusAssign

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.spmforkmp)
}

kotlin {
    listOf(
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.getTest("debug").apply {
            freeCompilerArgs +=
                listOf(
                    "-Xoverride-konan-properties=osVersionMin.ios_simulator_arm64=16.0",
                )
        }
        iosTarget.swiftPackageConfig(cinteropName = "playground") {
            // use swift 6.x for the Swift file
            toolsVersion = "6.0"
            // https://spmforkmp.eu/references/swiftPackageConfig/#strictenums
            strictEnums = listOf("EnumData", "EnumError", "ConcurrencyError", "ThrowError")
        }
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
        }
    }
}
