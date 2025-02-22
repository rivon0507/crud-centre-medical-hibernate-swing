plugins {
    id("java")
    id("io.freefair.lombok") version "8.12.1"
}

group = "com.github.rivon0507"
version = "1.0-SNAPSHOT"

val mockito = "org.mockito:mockito-core:5.15.2"
val mockitoAgent = configurations.create("mockitoAgent")

repositories {
    mavenCentral()
}

dependencies {
    implementation("jakarta.data:jakarta.data-api:1.0.1")
    implementation("org.glassfish:jakarta.el:4.0.2")
    implementation("org.hibernate.orm:hibernate-core:6.6.8.Final")
    implementation("org.hibernate:hibernate-jpamodelgen:6.6.8.Final")
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.5.2")
    implementation("org.jetbrains:annotations:24.0.0")
    implementation("ch.qos.logback:logback-classic:1.5.16")
    implementation("org.h2database

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(mockito)
    testImplementation("com.h2database:h2:2.3.232")
    mockitoAgent(mockito) { isTransitive = false }

    annotationProcessor("org.hibernate:hibernate-jpamodelgen:6.6.8.Final")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("-javaagent:${mockitoAgent.asPath}")
}
