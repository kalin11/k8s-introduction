import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    java
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.openapi.generator") version "7.7.0"
    id("com.avast.gradle.docker-compose") version "0.17.7"
}

group = "ru.kalin"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.0")
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.liquibase:liquibase-core")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

springBoot {
    mainClass.set("ru.kalin.k8sdemoproject.K8sDemoProjectApplication")
}

sourceSets {
    main {
        java {
            srcDir("${layout.buildDirectory.get()}/generated/src/main/java")
        }
    }
}

val generateAllApis by lazy {
    tasks.register("generateAllApis") {
        group = "code generation"
        description = "Generates all APIs and model classes"
    }
}

registerOpenApiGenerateTask(
    "generateSelfApi",
    "Generates this service API and model classes",
    "openapi/k8s-introduction-public.yaml",
    "self",
    "ru.kalin.k8sdemoproject",
    "library" to "spring-boot"
)

tasks.withType<JavaCompile> {
    dependsOn(generateAllApis)

    inputs.files(tasks.processResources)
}

fun registerOpenApiGenerateTask(
    taskName: String,
    taskDescription: String,
    inputSpecPath: String,
    outputDirSuffix: String,
    targetPackageName: String,
    vararg options: Pair<String, String>
) {
    val task = tasks.register<GenerateTask>(taskName) {
        val outputDirPath = "${layout.buildDirectory.get()}/generated/$outputDirSuffix"
        group = "code generation"
        description = taskDescription

        inputSpec.set("$rootDir/src/main/resources/$inputSpecPath")
        outputDir.set(outputDirPath)

        generatorName.set("spring")
        packageName.set(targetPackageName)

        apiPackage.set("$targetPackageName.controller")
        modelPackage.set("$targetPackageName.model")

        configOptions.set(
            mapOf(
                "annotationLibrary" to "none",
                "documentationProvider" to "none",
                "useSpringBoot3" to "true",
                "useSwaggerUI" to "false",
                "openApiNullable" to "false",
                "useTags" to "true",
                "delegatePattern" to "true",
                "interfaceOnly" to "false",
                "performBeanValidation" to "true",
                *options
            )
        )
        sourceSets {
            main {
                java {
                    srcDir("$outputDirPath/src/main/java")
                }
            }
        }
    }

    generateAllApis.configure {
        dependsOn(task)
    }
}
