package com.example.springbatchpractice

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableBatchProcessing
@SpringBootApplication
class SpringBatchPracticeApplication

fun main(args: Array<String>) {
    runApplication<SpringBatchPracticeApplication>(*args)
}
