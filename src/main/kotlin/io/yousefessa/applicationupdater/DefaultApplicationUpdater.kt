package io.yousefessa.applicationupdater

import io.yousefessa.applicationupdater.adapter.ApplicationAdapter
import io.yousefessa.applicationupdater.destination.Destination
import io.yousefessa.applicationupdater.schedule.ScheduleTask
import java.util.concurrent.TimeUnit

class DefaultApplicationUpdater(
    private val destination: Destination,
    private val adapter: ApplicationAdapter,
    private val task: ScheduleTask,
    private val version:
    String,
) : ApplicationUpdater() {
    override fun scheduledTask(): ScheduleTask {
        return this.task
    }

    override fun destination(): Destination {
        return this.destination
    }

    override fun localVersion(): String {
        return this.version
    }

    override fun adapter(): ApplicationAdapter {
        return this.adapter
    }

    override fun initialDelay(): Long {
        return 1
    }

    override fun delay(): Long {
        return 100
    }

    override fun timeUnit(): TimeUnit {
        return TimeUnit.SECONDS
    }
}