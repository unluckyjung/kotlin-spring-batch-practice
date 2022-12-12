package com.example.springbatchpractice.batch

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SampleTaskletJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun sampleTaskletJob(): Job {
        return jobBuilderFactory.get("sampleTaskletJob")
            .start(sampleTaskletJobStep1()).build()
    }

    @Bean
    fun sampleTaskletJobStep1(): Step {
        return stepBuilderFactory.get("sampleTaskletJobStep1")
            .tasklet { _, _ ->
                log.info("-> job -> [step1]")
                RepeatStatus.FINISHED
            }.build()
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}