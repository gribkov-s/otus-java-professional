plugins {
    java
}

group = "ru.otus"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")
    implementation("org.mapstruct:mapstruct")
    annotationProcessor("org.mapstruct:mapstruct-processor")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-test")
    //implementation ("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation ("ch.qos.logback:logback-classic")
    implementation ("org.flywaydb:flyway-core")
    implementation ("org.postgresql:postgresql")
    implementation ("com.google.code.findbugs:jsr305")

    implementation("com.google.code.gson:gson")
}
