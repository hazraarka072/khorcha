plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.4"
    id("io.micronaut.aot") version "4.4.4"
}

version = "0.1"
group = "com"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.aws:micronaut-aws-lambda-events-serde")
    implementation("io.micronaut.crac:micronaut-crac")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut:micronaut-http-server-netty")
    compileOnly("io.micronaut:micronaut-http-client-jdk")
    runtimeOnly("ch.qos.logback:logback-classic")
    testImplementation("io.micronaut:micronaut-http-client-jdk")
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.aws:micronaut-aws-sdk-v2")
    implementation("software.amazon.awssdk:dynamodb-enhanced")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.mindrot:jbcrypt:0.4")
}

application {
    mainClass = "com.khorcha.Application"
}

java {
    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
}

graalvmNative.toolchainDetection = false

micronaut {
    runtime("lambda_java")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}

tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "17"
    args(
            "-XX:MaximumHeapSizePercent=80",
            "-Dio.netty.allocator.numDirectArenas=0",
            "-Dio.netty.noPreferDirect=true"
    )
}

//tasks.register<Zip>("zipJar") {
//    group = "build"
//    description = "Zips the shadow JAR file for deployment"
//
//    val shadowJarTask = tasks.named<Jar>("shadowJar")
//    dependsOn(shadowJarTask)
//
//    // Use 'from' to specify the directory of the JAR
//    from(shadowJarTask.map { it.archiveFile.get().asFile.parent })
//
//    // Use 'filesMatching' to include the specific JAR file
//    val jarFileName = shadowJarTask.get().archiveFileName.get()
//    include(jarFileName)
//
//    archiveFileName.set("deployment.zip")
//    destinationDirectory.set(layout.buildDirectory.dir("distributions"))
//}

