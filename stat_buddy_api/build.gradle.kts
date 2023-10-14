plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.detekt)
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.boot.dm)
    `maven-publish`
}

dependencies {
    detektPlugins(libs.detekt.ktlint)

    implementation(libs.spring.starter.webflux)
    implementation(libs.spring.starter.validation)
    implementation(libs.swagger.annotations)

    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
}

sourceSets {
    val main by getting { }
    main.java.srcDirs("build/generated/source/proto/main/kotlin")
}

detekt {
    config.setFrom(files("$rootDir/detekt-config.yml"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "ru.shedlab.schedule_construction"
            artifactId = "stat_buddy_api"
            from(components["kotlin"])
        }
    }
    repositories {
        maven {
            url = if (project.version.toString().endsWith("-SNAPSHOT")) {
                uri("https://${System.getenv("CI_ARTIFACT_REPO_HOST")}/repository/maven-snapshots")
            } else {
                uri("https://${System.getenv("CI_ARTIFACT_REPO_HOST")}/repository/maven-releases")
            }
            credentials {
                username = System.getenv("CI_ARTIFACT_REPO_NAME")
                password = System.getenv("CI_ARTIFACT_REPO_TOKEN")
            }
        }
    }
}
