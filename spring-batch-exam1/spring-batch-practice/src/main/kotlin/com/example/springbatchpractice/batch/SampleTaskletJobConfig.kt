package com.example.springbatchpractice.batch

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
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
            .start(sampleTaskletJobStep1())
            .next(sampleTaskletJobStep2("defaultDate"))
            .build()
    }

    @Bean
    fun sampleTaskletJobStep1(): Step {
        return stepBuilderFactory.get(JOB_NAME)
            .tasklet { _, _ ->
                log.info(">>>>>> this is Step1")
                log.info("-> job -> [step1]")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    @JobScope
    fun sampleTaskletJobStep2(@Value("#{jobParameters[date]}") date: String): Step {
        return stepBuilderFactory.get("sampleTaskletJobStep2")
            .tasklet { _, _ ->
                log.info(">>>>>> this is Step2")
                log.info("-> job -> step1 -> [step2]")
                log.info("date: $date")
                RepeatStatus.FINISHED
            }.build()
    }

    companion object {
        private const val JOB_NAME = "sampleTaskletJob"
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}