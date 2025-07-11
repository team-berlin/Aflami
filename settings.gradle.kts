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
    }
}

rootProject.name = "Aflami"
include(":app")
include(":presentation:ui")
include(":presentation:viewModel")
include(":presentation:designSystem")
include(":domain:usecase")
include(":domain:entity")
include(":data:local")
include(":data:remote")
include(":presentation:safeImageViewer")
include(":data:repository")
