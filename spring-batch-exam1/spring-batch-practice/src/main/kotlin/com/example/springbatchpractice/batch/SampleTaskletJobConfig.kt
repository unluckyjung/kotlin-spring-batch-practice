package com.example.springbatchpractice.batch

import com.example.springbatchpractice.service.MemberCreate
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.TaskletStep
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SampleTaskletJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val memberCreate: MemberCreate,
) {

    @Bean
    fun sampleTaskletJob(): Job {
        return jobBuilderFactory.get("sampleTaskletJob")
            .start(sampleTaskletJobStep1())
            .next(sampleTaskletJobStep2("defaultDate"))
            .next(memberCreateStep(-1))
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

    @Bean
    @JobScope
    fun memberCreateStep(@Value("#{jobParameters[memberPrefix]}") memberPrefix: Long): TaskletStep {
        return stepBuilderFactory.get("memberCreateStep")
            .tasklet { _, _ ->
                log.info(">>>>>> this is CreateStep")
                log.info("-> job -> step1 -> step2 -> [create]")
                memberCreate.create(memberPrefix)
                RepeatStatus.FINISHED
            }.build()
    }

    companion object {
        const val MEMBER_PREFIX = "memberPrefix"
        const val DATE = "date"
        private const val JOB_NAME = "sampleTaskletJob"
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
