pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io") // 🔥 phải có cái này!
    }
}

rootProject.name = "Contrast"
include(":app")
include(":swipe-reveal-layout")
include(":refreshLayout")
include(":singledateandtimepicker")
include(":compressor")
include(":domain")
include(":data")
