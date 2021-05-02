import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val functions_framework_api_version: String by project
val java_function_invoker_version: String by project
val javafaker_version: String by project
val invoker by configurations.creating

plugins {
    kotlin("jvm") version "1.4.32"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "me.leo_lai"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit"))

    //cloud function
    implementation("com.google.cloud.functions:functions-framework-api:$functions_framework_api_version")
    invoker("com.google.cloud.functions.invoker:java-function-invoker:$java_function_invoker_version")

    //Gson
    implementation ("com.google.code.gson:gson:2.8.6")

    //java faker
    implementation("com.github.javafaker:javafaker:$javafaker_version")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.named("build") {
    dependsOn(":shadowJar")
}

task<Copy>("buildFunction") {
    group = "gcp"
    dependsOn("build")
    from("build/libs/${rootProject.name}-${version}-all.jar") {
        rename { "${rootProject.name}.jar" }
    }
    into("build/deploy")
}

task<JavaExec>("runFunction") {
    group = "gcp"
    main = "com.google.cloud.functions.invoker.runner.Invoker"
    classpath(invoker, sourceSets.main.get().runtimeClasspath)
    args(
        "--target", project.findProperty("runFunction.target") ?: "",
        "--port", project.findProperty("runFunction.port") ?: 8080
    )
}