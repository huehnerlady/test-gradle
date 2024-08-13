package de.organisation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = [
  "de.organisation"
])
class LeadtoolsBackendApplication

fun main(args: Array<String>) {
  runApplication<LeadtoolsBackendApplication>(*args)
}
