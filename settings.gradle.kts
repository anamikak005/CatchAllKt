pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    
}
rootProject.name = "CatchAllKt"
include("src:main:MultipleExceptionHandling")
findProject(":src:main:MultipleExceptionHandling")?.name = "MultipleExceptionHandling"
