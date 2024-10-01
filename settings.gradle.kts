pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/gradle-plugin" )
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
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
        maven("https://maven.aliyun.com/repository/gradle-plugin" )
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        google()
        mavenCentral()
    }
}

rootProject.name = "ToolComponents"
include(":app")
include(":ToolComponents")
include(":ToolComponents:TitleBar")
include(":ToolComponents:Toaster")
include(":ToolComponents:EasyWindow")
include(":ToolComponents:GsonFactory")
include(":ToolComponents:Permissions")
