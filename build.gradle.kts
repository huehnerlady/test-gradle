import org.jetbrains.kotlin.gradle.dsl.JvmTarget

buildscript {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}

val javaVersion = 21

val dependencyVersions = listOf(
    libs.annotations,
    libs.checkerEqual,
    libs.classgraph,
    libs.commonsIo,
    libs.commonsLang,
    libs.commonsCodec,
    libs.errorProneAnnotations,
    libs.gson,
    libs.guava,
    libs.httpClient5,
    libs.httpClientFluent,
    libs.httpcore,
    libs.javaDataloader,
    libs.kotestSpring,
    libs.kotlinLogging,
    libs.kotlinLoggingJvm,
    libs.kotlinLoggingOshai,
    libs.kotlinResult,
    libs.kotlinxCorountineBom,
    libs.kotlinxCorountineCore,
    libs.kotlinxCorountineCoreJvm,
    libs.kotlinxCorountineDebug,
    libs.kotlinxCorountineJdk8,
    libs.kotlinxCorountineReactive,
    libs.kotlinxCorountineSlf4j,
    libs.kotlinxCorountineTest,
    libs.kotlinxCorountineTestJvm,
    libs.kotlinxSerialization,
    libs.logbackClassic,
    libs.logbackCore,
    libs.minidev,
    libs.opentest4j,
    libs.pactDriverCore,
    libs.protobufJava,
    libs.tikaCore,
    libs.reactivestreams
)

val dependencyGroupVersions = mapOf(
    "com.fasterxml.jackson.core" to libs.versions.jackson.get(),
    "com.fasterxml.jackson.dataformat" to libs.versions.jackson.get(),
    "com.fasterxml.jackson.datatype" to libs.versions.jackson.get(),
    "com.fasterxml.jackson.module" to libs.versions.jackson.get(),
    "io.kotest" to libs.versions.kotest.get(),
    "io.ktor" to libs.versions.ktor.get(),
    "io.micrometer" to libs.micrometer.get().version,
    "io.mockk" to libs.versions.mockk.get(),
    "io.netty" to libs.netty.get().version,
    "net.bytebuddy" to libs.versions.byteBuddy.get(),
    "org.apache.groovy" to libs.groovy.get().version,
    "org.jetbrains.kotlin" to libs.versions.kotlin.get(),
    "org.junit" to libs.junitBom.get().version,
    "org.junit.jupiter" to libs.junitBom.get().version,
    "org.junit.platform" to libs.junitPlatform.get().version,
    "org.slf4j" to libs.versions.slf4j.get(),
    "org.springframework" to libs.versions.spring.get(),
    "org.springframework.boot" to libs.versions.springBoot.get(),
    "org.springframework.security" to libs.versions.springSecurity.get(),
)

plugins {
  id("groovy")
  id("java-library")
  id("maven-publish")
  alias(libs.plugins.privatkreditArtifactVersion)
  alias(libs.plugins.kotlin)
  alias(libs.plugins.kotlinSpring)
  alias(libs.plugins.kotlinAllopen)
  alias(libs.plugins.springBoot) apply false
  alias(libs.plugins.dockerPublish) apply false
}

allprojects {
  group = "de.organisation"
}



subprojects {
  project.apply(plugin = "java")
  project.apply(plugin = "maven-publish")
  project.apply(plugin = "org.jetbrains.kotlin.jvm")
  project.apply(plugin = "org.jetbrains.kotlin.plugin.spring")
  project.apply(plugin = "org.jetbrains.kotlin.plugin.allopen")

  repositories {
    mavenCentral()
  }

  configurations {
    all {
      exclude(module = "log4j")
      exclude(module = "servlet-api")
      resolutionStrategy {
        failOnVersionConflict()
        force(dependencyVersions)
        eachDependency {
          val forcedVersion = dependencyGroupVersions[requested.group]
          if (forcedVersion != null) {
            useVersion(forcedVersion)
          }
        }
        cacheDynamicVersionsFor(Integer.parseInt(System.getenv("GRADLE_CACHE_DYNAMIC_SECONDS") ?: "0"), "seconds")
      }
    }
  }

  configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.toVersion(javaVersion)
    targetCompatibility = JavaVersion.toVersion(javaVersion)
  }

  tasks {
    withType<Test> {
      useJUnitPlatform()
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
      compilerOptions {
        jvmTarget.set(JvmTarget.valueOf("JVM_$javaVersion"))
        freeCompilerArgs.set(listOf("-Xjsr305=strict"))
      }
    }
  }
}
