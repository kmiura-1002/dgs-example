plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    id("org.springframework.boot") version "3.1.2"
    id("com.netflix.dgs.codegen") version "5.12.4"
    kotlin("plugin.spring") version "1.7.22"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:7.3.6"))
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.netflix.graphql.dgs:graphql-dgs-pagination")
    implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}


tasks.named<Test>("test") {
    useJUnitPlatform()
}
