plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.20"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.github.ajalt.clikt:clikt:3.1.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClass.set("hands.on.clikt.AppKt")
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.8"

val copyRuntimeLibs = task<Copy>("copyRuntimeLibs") {
    into("$buildDir/libs")
    from(configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") })
}

tasks.getByName("assemble").finalizedBy(copyRuntimeLibs)

fun getClasspath() = "libs/" + file("$buildDir/libs").list()?.joinToString(":libs/")

task<Exec>("buildNativeImage") {
    description = "Build native image using GraalVM"
    dependsOn(tasks.getByName("build"))
    workingDir(buildDir)
    commandLine(
        "native-image",
        "-cp", getClasspath(),
        "-H:+ReportExceptionStackTraces",
        "-H:+AddAllCharsets",
        // "--report-unsupported-elements-at-runtime",
        // "--allow-incomplete-classpath",
        "--no-server",
        "--no-fallback",
        "--enable-http",
        "--enable-https",
        "hands.on.clikt.AppKt",
        "hands-on-clikt"
    )
}