import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java")
    id("jacoco")
    id("org.springframework.boot") version "2.3.9.RELEASE"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.3.40"

}

group = "br.com.exchangemoney"
version = "0.0.1-SNAPSHOT"
JavaVersion.toVersion(JavaVersion.VERSION_11)

repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone")
    maven("https://repo.spring.io/snapshot")
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

val logstashEncoderVersion = "5.3"
val javaMoneyVersion = "1.1"
val monetaVersion = "1.4.2"
val jacksonJavaMoneyVersion = "1.2.1"
val jadiraVersion = "7.0.0.CR1"
val hibernateTypesVersion = "2.4.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashEncoderVersion")

    //Money
    implementation("javax.money:money-api:$javaMoneyVersion")
    implementation("org.javamoney:moneta:$monetaVersion")
    implementation("org.zalando:jackson-datatype-money:$jacksonJavaMoneyVersion")
    implementation("org.jadira.usertype:usertype.core:$jadiraVersion")


    implementation("com.vladmihalcea:hibernate-types-52:$hibernateTypesVersion")

    runtimeOnly("com.h2database:h2")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("io.projectreactor:reactor-test")

    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

}

tasks {
    test {
        useJUnitPlatform()

        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            events = mutableSetOf(TestLogEvent.FAILED,TestLogEvent.PASSED, TestLogEvent.SKIPPED)
            showStandardStreams = true
        }

    }



    bootJar {
        archiveFileName.set("app.jar")
    }

    jacocoTestReport {
        reports {
            xml.isEnabled = true
            html.isEnabled = true
        }
    }

    jacocoTestCoverageVerification {
        dependsOn(jacocoTestReport)

        violationRules {
            rule { limit { minimum = 0.0.toBigDecimal() } }
        }
    }

    check {
        dependsOn(jacocoTestCoverageVerification)
    }

}

