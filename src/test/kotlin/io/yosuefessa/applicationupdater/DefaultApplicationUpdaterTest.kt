package io.yosuefessa.applicationupdater

import io.yosuefessa.applicationupdater.sample.ScheduleTaskSample
import io.yosuefessa.applicationupdater.sample.UpdateListenerSample
import io.yousefessa.applicationupdater.DefaultApplicationUpdater
import io.yousefessa.applicationupdater.destination.GitHubDestination
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.concurrent.TimeUnit

class DefaultApplicationUpdaterTest {
    private var taskRan = false

    private val updater = DefaultApplicationUpdater(
        GitHubDestination("yousef-essa", "ApplicationUpdater", "version"),
        UpdateListenerSample(
            {
                println("Download success")
            },
            {
                println("Download failure")
            },
            {
                println("Update success")
            },
            {
                println("Update failure")
            }
        ),
        ScheduleTaskSample(
            {
                println("Scheduler task ran!")
                taskRan = true
            },
            1,
            1,
            TimeUnit.SECONDS
        )
    )

    // put tasks here
}