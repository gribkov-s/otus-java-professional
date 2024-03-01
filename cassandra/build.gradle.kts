import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("idea")
    id("com.google.protobuf")
}

group = "ru.otus"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.stargate.grpc:grpc-proto")
    implementation("io.grpc:grpc-netty")
    implementation ("ch.qos.logback:logback-classic")
    implementation ("com.google.code.gson:gson:2.10.1")
}
