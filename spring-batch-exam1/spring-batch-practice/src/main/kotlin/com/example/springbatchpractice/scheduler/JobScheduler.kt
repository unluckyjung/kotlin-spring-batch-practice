package com.example.springbatchpractice.scheduler

import com.example.springbatchpractice.batch.SampleTaskletJobConfig
import com.example.springbatchpractice.batch.SampleTaskletJobConfig.Companion.DATE
import com.example.springbatchpractice.batch.SampleTaskletJobConfig.Companion.MEMBER_PREFIX
import org.slf4j.LoggerFactory
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class JobScheduler(
    private val jobLauncher: JobLauncher,
    private val simpleJobTaskletJobConfig: SampleTaskletJobConfig,
) {

    @Scheduled(initialDelay = 1000, fixedDelay = 10000)
    fun runSimpleJob() {
        val arguments = mutableMapOf<String, JobParameter>()
        arguments[DATE] = JobParameter(ZonedDateTime.now().toString())
        arguments[MEMBER_PREFIX] = JobParameter((1L..100L).random())

        try {
            jobLauncher.run(
                simpleJobTaskletJobConfig.sampleTaskletJob(),
                JobParameters(
                    arguments
                )
            )
        } catch (e: Exception) {
            loggingAndThrowException(e)
        }
    }

    private fun loggingAndThrowException(e: Exception) {
        LOG.error(e.message)
        throw e
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(this::class.java)
    }
}
