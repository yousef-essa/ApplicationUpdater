package io.yosuefessa.applicationupdater.sample

import io.yousefessa.applicationupdater.ApplicationUpdater
import io.yousefessa.applicationupdater.schedule.ScheduleTask
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import java.util.function.Function

class ScheduleTaskSample(
    private val task: Function<ApplicationUpdater, Unit>,
    private val initialDelay: Long,
    private val delay: Long,
    private val timeUnit: TimeUnit,
): ScheduleTask {

    override fun handle(updater: ApplicationUpdater): Unit {
        this.task.apply(updater)
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