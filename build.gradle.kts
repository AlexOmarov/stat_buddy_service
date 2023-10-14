plugins {
    alias(libs.plugins.kotlin) // Sonarqube may break if this plugin will be in subprojects with version
    alias(libs.plugins.sonarqube)
}

var exclusions = project.properties["test_exclusions"].toString()

sonar {
    properties {
        property("sonar.qualitygate.wait", "true")
        property("sonar.core.codeCoveragePlugin", "jacoco")
        property(
            "sonar.kotlin.detekt.reportPaths",
            "${project(":stat_buddy_app").layout.buildDirectory.get().asFile}/reports/detekt/detekt.xml, " +
                    "${project(":stat_buddy_api").layout.buildDirectory.get().asFile}/reports/detekt/detekt.xml"
        )
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${project(":stat_buddy_app").layout.buildDirectory.get().asFile}/reports/jacoco/test/jacocoTestReport.xml"
        )
        property("sonar.scm.provider", "git")
        property("sonar.cpd.exclusions", exclusions)
        property("sonar.gradle.skipCompile", true)
        property("sonar.jacoco.excludes", exclusions)
        property("sonar.coverage.exclusions", exclusions)
    }
}