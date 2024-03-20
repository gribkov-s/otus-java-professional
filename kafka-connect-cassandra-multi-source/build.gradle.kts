plugins {
    id("idea")
}

group = "ru.otus"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")

    implementation("com.datastax.cassandra:cassandra-driver-core")
    implementation("com.datastax.cassandra:cassandra-driver-mapping")
    implementation("org.apache.kafka:connect-api")

    implementation ("ch.qos.logback:logback-classic")
    implementation ("ch.qos.logback:logback-classic")
    implementation("com.codahale.metrics:metrics-core")
}
