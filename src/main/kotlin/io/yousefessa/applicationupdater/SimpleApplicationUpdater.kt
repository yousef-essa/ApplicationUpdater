package io.yousefessa.applicationupdater

import io.yousefessa.applicationupdater.adapter.ApplicationAdapter
import io.yousefessa.applicationupdater.destination.Destination
import io.yousefessa.applicationupdater.schedule.ScheduleTask
import java.util.concurrent.TimeUnit

class SimpleApplicationUpdater(
    private val destination: Destination,
    private val adapter: ApplicationAdapter,
    private val task: ScheduleTask,
    private val version: String,
    private val initialDelay: Long,
    private val delay: Long,
    private val timeUnit: TimeUnit,
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
        return this.initialDelay
    }

    override fun delay(): Long {
        return this.delay
    }

    override fun timeUnit(): TimeUnit {
        return this.timeUnit
    }
}