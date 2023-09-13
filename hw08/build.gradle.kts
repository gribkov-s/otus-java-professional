plugins {
    java
}

group = "ru.otus"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("javax.json:javax.json-api:1.1.4")
    implementation ("com.google.guava:guava")
    implementation ("com.fasterxml.jackson.core:jackson-databind")
    implementation ("javax.json:javax.json-api")
    implementation ("com.google.code.gson:gson:2.10.1")

    testImplementation ("org.junit.jupiter:junit-jupiter-api")
    testImplementation ("org.junit.jupiter:junit-jupiter-engine")
    testImplementation ("org.junit.jupiter:junit-jupiter-params")
    testImplementation ("org.assertj:assertj-core")
    testImplementation ("org.mockito:mockito-core")
    testImplementation ("org.mockito:mockito-junit-jupiter")
}
