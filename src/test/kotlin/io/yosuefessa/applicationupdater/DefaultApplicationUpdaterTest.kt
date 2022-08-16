package io.yosuefessa.applicationupdater

import io.yosuefessa.applicationupdater.sample.ScheduleTaskWrapper
import io.yousefessa.applicationupdater.ApplicationUpdater
import io.yousefessa.applicationupdater.DefaultApplicationUpdater
import io.yousefessa.applicationupdater.destination.GitHubDestination
import io.yousefessa.applicationupdater.listener.ApplicationAdapter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.time.Duration
import java.util.concurrent.TimeUnit

private const val TEST_VERSION = "0.1.0"

class DefaultApplicationUpdaterTest {
    private var fileDownloaded = false
    private var taskRan = false

    private var updater: ApplicationUpdater

    init {
        val destination =
            GitHubDestination("yousef-essa", "ApplicationUpdaterSample", "version")

        val adapter = ApplicationAdapter.defaultAdapter(File("sample.jar")) {
            fileDownloaded = true
            println("downloaded")
        }

        val task = ScheduleTaskWrapper(
            { _, context ->
                if (taskRan) {
                    context.cancel = true
                    println("context.cancel = true; return")
                    return@ScheduleTaskWrapper
                }
                println("Scheduler task ran!")
                taskRan = true
            },
            1,
            100,
            TimeUnit.SECONDS
        )

        updater = DefaultApplicationUpdater(
            destination,
            adapter,
            task,
            TEST_VERSION
        )
    }

    @Test
    fun updaterInit() {
        try {
            updater.init()
        } catch (exception: Exception) {
            throw IllegalStateException(exception)
        }

        Assertions.assertTimeout(Duration.ofSeconds(10)) {
            while (fileDownloaded.not()) {
                Thread.sleep(500)
            }
            return@assertTimeout
        }
    }
}