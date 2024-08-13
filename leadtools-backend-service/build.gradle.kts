plugins {
  alias(libs.plugins.springBoot)
  id("de.europace.docker-publish")
}

springBoot {
  buildInfo()
}

normalization {
  runtimeClasspath {
    ignore("**/build-info.properties")
  }
}

tasks {
  bootJar {
    archiveClassifier.set("boot")
    enabled = true
  }

  jar {
    enabled = true
  }

  withType(Test::class) {
    useJUnitPlatform()
  }
}

dependencies {
  implementation(libs.kotlin)
  implementation(libs.bundles.spring)
  implementation(libs.bundles.springBoot)
  implementation(libs.bundles.springSecurity)
  implementation(libs.jackson)
  implementation(libs.jacksonKotlin)
  implementation(libs.caffeine)


  testRuntimeOnly(libs.byteBuddy)
  testImplementation(libs.bundles.springTest)
  testImplementation(libs.bundles.mockk)
  testImplementation(libs.bundles.kotest)
  testImplementation(libs.jsonpath)

  implementation(libs.slf4jApi)
  runtimeOnly(libs.bundles.logging)
}
