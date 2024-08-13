include(
    "leadtools-backend-service"
)
rootProject.name = "leadtools-backend"

fun Settings.property(key: String): String =
  javaClass.getMethod("getProperty", String::class.java).invoke(this, key) as String

fun Settings.hasProperty(key: String): Boolean =
  javaClass.getMethod("hasProperty", String::class.java).invoke(this, key) as Boolean

operator fun Settings.get(key: String) = if (hasProperty(key)) property(key) else null

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
