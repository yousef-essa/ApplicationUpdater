package io.yosuefessa.applicationupdater.sample

import io.yousefessa.applicationupdater.ApplicationUpdater
import io.yousefessa.applicationupdater.schedule.ScheduleContext
import io.yousefessa.applicationupdater.schedule.ScheduleTask
import java.util.concurrent.TimeUnit
import java.util.function.BiConsumer

class ScheduleTaskWrapper(
    private val task: BiConsumer<ApplicationUpdater, ScheduleContext>,
    private val initialDelay: Long,
    private val delay: Long,
    private val timeUnit: TimeUnit,
): ScheduleTask {
    override fun handle(updater: ApplicationUpdater, context: ScheduleContext) {
        this.task.accept(updater, context)
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