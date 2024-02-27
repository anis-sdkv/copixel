pluginManagement {
    repositories {
        google()
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

rootProject.name = "Copixel"
include(":app")

include(":core")
include(":core:designsystem")
include(":feature")
include(":feature:auth")
include(":feature:home")
include(":feature:canvas")
include(":feature:profile")
include(":feature:auth:api")
include(":feature:auth:impl")
include(":core:network")
include(":core:navigation")
include(":feature:canvas:api")
include(":feature:canvas:impl")
include(":feature:home:api")
include(":feature:home:impl")
include(":feature:profile:api")
include(":feature:profile:impl")
